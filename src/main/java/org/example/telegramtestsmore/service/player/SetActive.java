package org.example.telegramtestsmore.service.player;

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
public class SetActive {
    private final CreateTag createTag;
    private final SendMessageBot sendMessageBot;
    private final PlayerRepository playerRepository;

    public List<Object> setActive(long chatId, Integer messageThreadId, String tag, List<MessageEntity> entity) {
        return activeChen(chatId, messageThreadId, createTag.createTag(tag, entity));
    }

    private List<Object> activeChen(Long chatId, Integer messageThreadId, String tag) {
        List<Object> operation = new ArrayList<>();
        PlayerTag playerTag = playerRepository.findByTag(tag);
        if (playerTag.isActive()) {
            playerRepository.save(new PlayerTag(playerTag.getTag(), playerTag.getNikename(), false));
            operation.add(sendMessageBot.sendMessage(chatId, messageThreadId, "Игрока отправили в запас"));
        }
        else {
            playerRepository.save(new PlayerTag(playerTag.getTag(), playerTag.getNikename(), true));
            operation.add(sendMessageBot.sendMessage(chatId, messageThreadId, "Игрок вышел из запаса"));
        }
        return operation;
    }
}
