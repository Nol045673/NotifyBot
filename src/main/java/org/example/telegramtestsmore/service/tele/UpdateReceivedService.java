package org.example.telegramtestsmore.service.tele;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.telegramtestsmore.entity.PlayerTag;
import org.example.telegramtestsmore.repository.PlayerRepository;
import org.example.telegramtestsmore.service.message.SendMessageBot;
import org.example.telegramtestsmore.service.message.SendMessageWithKeyBoard;
import org.example.telegramtestsmore.service.player.DeletePlayer;
import org.example.telegramtestsmore.service.player.ResultAllPlayers;
import org.example.telegramtestsmore.service.player.SetActive;
import org.example.telegramtestsmore.service.player.UpdatePlayer;
import org.example.telegramtestsmore.service.war.StartWar;
import org.example.telegramtestsmore.service.war.WarKeyBoard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UpdateReceivedService {
    private final SendMessageBot sendMessageBot;
    private final SendMessageWithKeyBoard sendMessageWithKeyBoard;
    private final DeletePlayer deletePlayer;
    private final SetActive setActive;
    private final ResultAllPlayers resultAllPlayers;
    private final UpdatePlayer updatePlayer;
    private final StartWar startWar;
    private final WarKeyBoard warKeyBoard;

    private final PlayerRepository playerRepository;

    public List<Object> updateHandle(Update update) {
        Integer messageThreadId;
        List<PlayerTag> allnames = new ArrayList<>(playerRepository.findAll());
        if (update.hasCallbackQuery()) {
            messageThreadId = update.getCallbackQuery().getMessage().getMessageThreadId();
            return warKeyBoard.warKeyBoard(update, messageThreadId, allnames);
        } else if (update.hasMessage() && update.getMessage().isSuperGroupMessage() || update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            messageThreadId = update.getMessage().getMessageThreadId();
            long chatId = update.getMessage().getChatId();
            if (message.equals("/war")) {
                return sendMessageWithKeyBoard.sendMessageWithKeyBoard(chatId, messageThreadId, "Вы выбрали: ", allnames);
            } else if (message.contains("/add") && message.split(" ").length >= 3) {
                List<MessageEntity> entities = update.getMessage().getEntities();
                return updatePlayer.addPlayer(chatId, messageThreadId, message, entities);
            } else if (message.contains("/delete") && message.split(" ").length == 2) {
                List<MessageEntity> entities = update.getMessage().getEntities();
                return deletePlayer.deletePlayer(chatId, messageThreadId, message, entities);
            } else if (message.contains("/active") && message.split(" ").length == 2) {
                List<MessageEntity> entities = update.getMessage().getEntities();
                return setActive.setActive(chatId, messageThreadId, message, entities);
            } else if (message.equals("/all")) {
                return resultAllPlayers.allPlayers(chatId, messageThreadId);
            } else if (message.equals("/startwarbot")) {
                return startWar.startWar(chatId, messageThreadId);
            } else if (message.equals("/start")) {
                return sendMessageBot.sendMessage(chatId, messageThreadId, "Обратитесь к @chilik809");
            }
        }
        return null;
    }
}
