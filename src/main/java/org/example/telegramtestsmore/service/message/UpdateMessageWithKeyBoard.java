package org.example.telegramtestsmore.service.message;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Service
public class UpdateMessageWithKeyBoard<T> {
    public List<T> updateMessageWithKeyBoard(Long chatId, Integer messageId, String text,
                                             InlineKeyboardMarkup inlineKeyboardMarkup, List<String> namesToRemove) {
        List<EditMessageText> operation = new ArrayList<>();
        try {
            List<List<InlineKeyboardButton>> originalKeyboard = inlineKeyboardMarkup.getKeyboard();
            List<List<InlineKeyboardButton>> updatedKeyboard = new ArrayList<>();

            for (List<InlineKeyboardButton> row : originalKeyboard) {
                List<InlineKeyboardButton> filteredRow = row.stream()
                    .filter(button -> !namesToRemove.contains(button.getText()))
                    .toList();

                // Добавляем только если есть кнопки в строке
                if (!filteredRow.isEmpty()) {
                    updatedKeyboard.add(filteredRow);
                }
            }
            inlineKeyboardMarkup.setKeyboard(updatedKeyboard);

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
