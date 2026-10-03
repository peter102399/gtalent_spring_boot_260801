package student.gtalent_spring_boot_260801.service;


import org.springframework.stereotype.Service;

import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.MessageContent;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;

@Service
public class LineWebhookService {

    private final LineReplyService lineReplyService;

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
            String text = textMessage.text();
            System.out.println("LINE webhook text message from " + lineUserId + ": " + text);
          // 模糊比對：只要訊息包含「愛你」就觸發
            if (text.contains("愛你")) {
                // 呼叫新方法：回覆 LOVE U TOO 加上一個大愛心貼圖
                // "11537" 和 "52002734" 是 LINE 官方提供的內建免費貼圖（熊大兔兔系列）
                lineReplyService.replyTextAndSticker(event.replyToken(), "LOVE U TOO", "11537", "52002734");
            } else {
                lineReplyService.replyText(event.replyToken(), "收到：" + text);
            }
        }
    }
}