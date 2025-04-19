package org.example.telegramtestsmore.service.war;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.telegramtestsmore.entity.PlayerTag;
import org.example.telegramtestsmore.repository.PlayerRepository;
import org.example.telegramtestsmore.service.message.DeleteMessageBot;
import org.example.telegramtestsmore.service.message.SendMessageBot;
import org.example.telegramtestsmore.service.message.SendMessageWithKeyBoard;
import org.example.telegramtestsmore.service.message.UpdateMessageWithKeyBoard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class WarKeyBoard {
    private final PlayerRepository playerRepository;
    private final SendMessageBot sendMessageBot;
    private final DeleteMessageBot deleteMessageBot;
    private final UpdateMessageWithKeyBoard updateMessageWithKeyBoard;
    private final SendMessageWithKeyBoard sendMessageWithKeyBoard;
    private final Set<String> userAnswers = new HashSet<>();
    private final List<String> orderfight = new Stack<>();

    public List<Object> warKeyBoard(Update update, Integer messageThreadId, List<PlayerTag> allnames) {
        boolean firstmassege = true;
        List<Object> operation = new ArrayList<>();
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        try {
            switch (callbackData) {
                case "готово" -> {
                    operation.add(deleteMessageBot.deleteMessage(chatId, messageId));
                    while (!userAnswers.isEmpty()) {
                        int countplayers = 0;
                        List<String> tags = new ArrayList<>();
                        Iterator<String> iterator = userAnswers.iterator();
                        while (iterator.hasNext()) {
                            String name = iterator.next();
                            countplayers++;
                            tags.add(playerRepository.findByNikename(name).getTag());
                            iterator.remove();
                            if (countplayers == 5) {
                                break;
                            }
                        }
                        if (firstmassege) {
                            firstmassege = false;
                            operation.add(sendMessageBot.sendMessage(chatId, messageThreadId,
                                "Игрок: " +
                                    playerRepository.findByTag("@" + update.getCallbackQuery().getFrom().getUserName()).getNikename() +
                                    " вызвал на войну! " +
                                    "Дата: " +
                                    LocalDate.now() +
                                    "\n" +
                                    String.join("\n", tags)));
                        } else {
                            operation.add(sendMessageBot.sendMessage(chatId, messageThreadId, String.join("\n", tags)));
                        }
                    }
                }
                case "отмена" -> {
                    userAnswers.clear();
                    operation.add(deleteMessageBot.deleteMessage(chatId, messageId));
                    operation.add(sendMessageBot.sendMessage(chatId, messageThreadId, "Призыв был отменен"));
                }
                case "убрать" -> {
                    if (!userAnswers.isEmpty()) {
                        String last = orderfight.get(orderfight.size() - 1);
                        userAnswers.remove(last);
                        orderfight.remove(last);
                        String text = "Вы выбрали: " + String.join(", ", userAnswers);
                        operation.add(updateMessageWithKeyBoard.updateMessageWithKeyBoard(chatId, messageId, text,
                                    sendMessageWithKeyBoard.createInlineKeyboard(allnames), orderfight));
                    } else {
                        operation.add(sendMessageBot.sendMessage(chatId, messageThreadId, "Некого убирать!"));
                    }

                }
                default -> {
                    userAnswers.add(callbackData);
                    orderfight.add(callbackData);
                    String text = "Вы выбрали: " + String.join(", ", userAnswers);
                    operation.add(updateMessageWithKeyBoard.updateMessageWithKeyBoard(chatId, messageId, text,
                                  sendMessageWithKeyBoard.createInlineKeyboard(allnames), orderfight));
                }
            }
        } catch (Exception e) {
            operation.add(sendMessageBot.sendMessage(chatId, messageThreadId, "Произошла ошибка, возможно в вашем профиле не настроен username или вас нет в базе"));
        }
        List<Object> collect = operation.stream()
            .flatMap(inner -> inner instanceof List<?> ? ((List<?>) inner).stream() : Stream.empty()) // Распаковываем вложенные списки в поток
            .collect(Collectors.toList());
        return collect;
    }
}
