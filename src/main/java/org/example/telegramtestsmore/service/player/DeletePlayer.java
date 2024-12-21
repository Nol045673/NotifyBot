package org.example.telegramtestsmore.service.player;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.telegramtestsmore.entity.PlayerTag;
import org.example.telegramtestsmore.repository.PlayerRepository;
import org.example.telegramtestsmore.service.message.SendMessageBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

@Service
@RequiredArgsConstructor
public class DeletePlayer {
    private final CreateTag createTag;
    private final PlayerRepository playerRepository;
    private final SendMessageBot sendMessageBot;

    @Transactional
    public List deletePlayer(long chatId, Integer messageThreadId, String tag, List<MessageEntity> entity) {
        String tagname = createTag.createTag(tag, entity);
        playerRepository.deleteByTag(tagname);
        return sendMessageBot.sendMessage(chatId, messageThreadId, "Вояку удалили из списка! Его тег: " + tagname);
    }
}
