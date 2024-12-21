package org.example.telegramtestsmore.service.message;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class SendMessageBot<T> {

    public List<T> sendMessage(Long chatId, Integer messageThreadId, String message) {
        if (messageThreadId == null) {
            messageThreadId = 0;
        }
        List<SendMessage> operation = new ArrayList<>();
        try {
            org.telegram.telegrambots.meta.api.methods.send.SendMessage sendMessage = new org.telegram.telegrambots.meta.api.methods.send.SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setMessageThreadId(messageThreadId);
            sendMessage.setText(message);
            sendMessage.enableHtml(true);
            operation.add(sendMessage);
            return (List<T>) operation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
