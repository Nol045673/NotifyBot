package org.example.telegramtestsmore.service.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.example.telegramtestsmore.entity.PlayerTag;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendMessageWithKeyBoard<T> {
    public List<T> sendMessageWithKeyBoard(Long chatId, Integer messageThreadId, String message, List<PlayerTag> allnames) {
        if (messageThreadId == null) {
            messageThreadId = 0;
        }
        List<Object> operation = new ArrayList<>();
        try {
            org.telegram.telegrambots.meta.api.methods.send.SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setMessageThreadId(messageThreadId);
            sendMessage.setText(message);
            sendMessage.setReplyMarkup(createInlineKeyboard(allnames));
            operation.add(sendMessage);
            return (List<T>) operation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public InlineKeyboardMarkup createInlineKeyboard(List<PlayerTag> allnames) {
        List<String> playersnames = new ArrayList<>();
        for (PlayerTag playerTag : allnames) {
            if (playerTag.isActive()) {
                playersnames.add(playerTag.getNikename());
            }
        }
        Collections.sort(playersnames);
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int rowscount = 0;
        Iterator<String> iterator = playersnames.iterator();
        while (rowscount < playersnames.size()) {
            int counter = 0;
            List<InlineKeyboardButton> row = new ArrayList<>();
            while (iterator.hasNext()) {
                String option = iterator.next();
                row.add(InlineKeyboardButton.builder()
                    .text(option)
                    .callbackData(option)
                    .build());
                counter++;
                if (counter == 3) {
                    break;
                }
            }
            rows.add(row);
            rowscount++;
        }
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder()
            .text("ОТМЕНА")
            .callbackData("отмена")
            .build());
        row.add(InlineKeyboardButton.builder()
            .text("Убрать последнего")
            .callbackData("убрать")
            .build());
        rows.add(row);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder()
            .text("ГОТОВО")
            .callbackData("готово")
            .build());
        rows.add(row2);
        return InlineKeyboardMarkup.builder().keyboard(rows).build();
    }
}
