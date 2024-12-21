package org.example.telegramtestsmore.service.player;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.telegramtestsmore.entity.PlayerTag;
import org.example.telegramtestsmore.repository.PlayerRepository;
import org.example.telegramtestsmore.service.message.SendMessageBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

@Service
@RequiredArgsConstructor
public class UpdatePlayer {
    private final CreateTag createTag;
    private final PlayerRepository playerRepository;
    private final SendMessageBot sendMessageBot;

    public List addPlayer(long chatId, Integer messageThreadId, String tag, List<MessageEntity> entity) {
        String tagname = createTag.createTag(tag, entity);
        playerRepository.save(new PlayerTag(tagname, tag.split(" ")[2], true));
        return sendMessageBot.sendMessage(chatId, messageThreadId, "Добавили нового вояку! Его тег: " + tagname + " его ник: " + tag.split(" ")[2]);
    }
}
