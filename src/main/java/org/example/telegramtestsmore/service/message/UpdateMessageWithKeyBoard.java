package org.example.telegramtestsmore.service.message;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class UpdateMessageWithKeyBoard<T> {
    public List<T> updateMessageWithKeyBoard(Long chatId, Integer messageId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        List<EditMessageText> operation = new ArrayList<>();
        try {
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(messageId);
            editMessageText.setText(text);
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
            operation.add(editMessageText);
            return (List<T>) operation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
