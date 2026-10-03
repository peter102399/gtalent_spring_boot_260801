package student.gtalent_spring_boot_260801.service;

import org.springframework.stereotype.Service;

import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.MessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;

@Service
public class LineWebhookService {

    private final LineReplyService lineReplyService;

    // 💡 改變地方 1：在類別最上方新增這個靜態變數，用來記住目前的總金額
    private static int totalMoney = 0;

    public LineWebhookService(LineReplyService lineReplyService) {
        this.lineReplyService = lineReplyService;
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
            String text = textMessage.text().trim(); // 💡 加上 .trim() 可以順便防呆（去掉前後空白）
            System.out.println("LINE webhook text message from " + lineUserId + ": " + text);
            
            // 💡 改變地方 2-1：外加一個「清除」指令，讓你可以隨時把帳目歸零
            if ("清除".equals(text)) {
                totalMoney = 0;
                lineReplyService.replyText(event.replyToken(), "金額已清空！目前總額為：0 元");
                return; // 執行完就結束，不往下走
            }

            // 模糊比對：只要訊息包含「愛你」就觸發 (這段你原有的程式碼完全沒變動)
            if (text.contains("愛你") || text.contains("愛妳")) {
                // 呼叫新方法：回覆 LOVE U TOO 加上一個大愛心貼圖
                // "11537" 和 "52002734" 是 LINE 官方提供的內建免費貼圖（熊大兔兔系列）
                lineReplyService.replyTextAndSticker(event.replyToken(), "LOVE U TOO", "1", "2");
            } else {
                // 💡 改變地方 2-2：原先只有單純回覆「收到：」的地方，改成數字計算機邏輯
                try {
                    // 嘗試將使用者輸入的文字轉成數字
                    int inputMoney = Integer.parseInt(text);
                    
                    // 將輸入的金額累加到總金額變數中
                    totalMoney += inputMoney;
                    
                    // 回覆加總後的結果給使用者
                    lineReplyService.replyText(event.replyToken(), 
                        "已幫你記下：" + inputMoney + " 元\n💰 目前累計總金額：" + totalMoney + " 元");
                        
                } catch (NumberFormatException e) {
                    // 如果輸入的不是純數字（例如打：哈囉），轉數字會失敗，就會跳來這裡，維持你原來的預設回覆
                    lineReplyService.replyText(event.replyToken(), "收到：" + text);
                }
            }
        }
    }
}