package org.example.telegramtestsmore.service.message;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

@Service
public class DeleteMessageBot<T> {

    public List<T> deleteMessage(Long chatId, Integer messageId) {
        List<DeleteMessage> operation = new ArrayList<>();
        try {
            DeleteMessage deleteMessage = new org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage();
            deleteMessage.setChatId(chatId);
            deleteMessage.setMessageId(messageId);
            operation.add(deleteMessage);
            return (List<T>) operation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
