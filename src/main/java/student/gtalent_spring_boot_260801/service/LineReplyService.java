package student.gtalent_spring_boot_260801.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.StickerMessage;
import com.linecorp.bot.messaging.model.TextMessage;

import student.gtalent_spring_boot_260801.exception.ApiException;

@Service
public class LineReplyService {

    private final MessagingApiClient messagingApiClient;

    public LineReplyService(MessagingApiClient messagingApiClient) {
        this.messagingApiClient = messagingApiClient;
    }

    // 1. 原本的回覆純文字方法
    public void replyText(String replyToken, String message) {
        replyMessages(replyToken, List.of(new TextMessage(message)));
    }

    // 2. 新增：回覆單張貼圖的方法
    // packageId: 貼圖包 ID，stickerId: 貼圖 ID
    public void replySticker(String replyToken, String packageId, String stickerId) {
        replyMessages(replyToken, List.of(new StickerMessage(packageId, stickerId)));
    }

    // 3. 新增：同時回覆「文字 + 貼圖」（或多個訊息組合）的方法
    public void replyTextAndSticker(String replyToken, String message, String packageId, String stickerId) {
        replyMessages(replyToken, List.of(
                new TextMessage(message),
                new StickerMessage(packageId, stickerId)
        ));
    }

    // 4. 封裝統一發送請求的私有方法
    private void replyMessages(String replyToken, List<Message> messages) {
        ReplyMessageRequest request = new ReplyMessageRequest(
                replyToken,
                messages,
                false);

        try {
            messagingApiClient.replyMessage(request).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new ApiException("line", "LINE 回覆被中斷");
        } catch (ExecutionException exception) {
            throw new ApiException("line", "LINE 回覆失敗");
        }
    }
}