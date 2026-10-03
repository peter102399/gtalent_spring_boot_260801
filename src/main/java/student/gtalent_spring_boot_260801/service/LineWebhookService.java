package student.gtalent_spring_boot_260801.service;

import org.springframework.stereotype.Service;

import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.MessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;

import student.gtalent_spring_boot_260801.entity.MoneyRecord;
import student.gtalent_spring_boot_260801.repository.MoneyRecordRepository;

@Service
public class LineWebhookService {

    private final LineReplyService lineReplyService;
    private final MoneyRecordRepository moneyRecordRepository;

    public LineWebhookService(LineReplyService lineReplyService, MoneyRecordRepository moneyRecordRepository) {
        this.lineReplyService = lineReplyService;
        this.moneyRecordRepository = moneyRecordRepository;
    }

    public void handleEvent(Event event) {
        if (event instanceof MessageEvent messageEvent) {
            handleMessageEvent(messageEvent);
        }
    }

    private void handleMessageEvent(MessageEvent event) {
        MessageContent message = event.message();

        if (message instanceof TextMessageContent textMessage) {
            String lineUserId = event.source().userId();
            String text = textMessage.text().trim();
            System.out.println("LINE webhook text message from " + lineUserId + ": " + text);
            
            // 1. 清除指令：把資料庫 id=1 的金額改回 0
            if ("清除".equals(text)) {
                MoneyRecord record = moneyRecordRepository.findById(1L).orElse(new MoneyRecord(1L, 0));
                record.setTotalMoney(0);
                moneyRecordRepository.save(record);
                lineReplyService.replyText(event.replyToken(), "金額已清空！目前總額為：0 元");
                return;
            }

            // 2. 模糊比對：保留你原本的愛心貼圖功能
            if (text.contains("愛你") || text.contains("愛妳")) {
                lineReplyService.replyTextAndSticker(event.replyToken(), "LOVE U TOO", "1", "2");
            } else {
                // 3. 嘗試金錢加總
                try {
                    int inputMoney = Integer.parseInt(text);
                    
                    // 從資料庫撈出 id=1 的紀錄，若沒有則建立一筆預設 0 元的
                    MoneyRecord record = moneyRecordRepository.findById(1L).orElse(new MoneyRecord(1L, 0));
                    
                    // 加總並儲存回資料庫
                    int newTotal = record.getTotalMoney() + inputMoney;
                    record.setTotalMoney(newTotal);
                    moneyRecordRepository.save(record);
                    
                    lineReplyService.replyText(event.replyToken(), 
                        "已幫你記下：" + inputMoney + " 元\n💰 目前累計總金額：" + newTotal + " 元（已永久存入資料庫）");
                        
                } catch (NumberFormatException e) {
                    lineReplyService.replyText(event.replyToken(), "收到：" + text);
                }
            }
        }
    }
}