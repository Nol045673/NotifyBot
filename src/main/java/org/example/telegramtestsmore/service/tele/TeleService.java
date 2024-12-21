package org.example.telegramtestsmore.service.tele;

import java.lang.reflect.Method;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.telegramtestsmore.config.BotConfig;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@RequiredArgsConstructor
public class TeleService extends TelegramLongPollingBot {

    private final UpdateReceivedService updateReceivedService;
    private final BotConfig config; // Например, @my_channel

//    public TeleService(@Lazy MessageService messageService, @Lazy PlayerService playerService,
//                       @Lazy WarService warService, PlayerRepository playerRepository, BotConfig config) {
//        this.messageService = messageService;
//        this.playerService = playerService;
//        this.warService = warService;
//        this.playerRepository = playerRepository;
//        this.config = config;
//    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            List<Object> operations = updateReceivedService.updateHandle(update);
            for (Object operation : operations) {
                execute((BotApiMethod<?>)operation);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
