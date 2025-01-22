package org.example.telegramtestsmore.service.player;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.telegramtestsmore.entity.PlayerTag;
import org.example.telegramtestsmore.repository.PlayerRepository;
import org.example.telegramtestsmore.service.message.SendMessageBot;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultAllPlayers {
    private final PlayerRepository playerRepository;
    private final SendMessageBot sendMessageBot;

    public List allPlayers(long chatId, Integer messageThreadId) {
        int countplayer = 1;
        StringBuilder answer = new StringBuilder();
        for (PlayerTag player : playerRepository.findAll()) {
            answer.append(countplayer)
                .append(" ")
                .append(player.getTag())
                .append("||")
                .append(player.getNikename())
                .append("||")
                .append(player.isActive() ? "Активен" : "В запасе")
                .append("\n");
            countplayer++;
        }
        return sendMessageBot.sendMessage(chatId, messageThreadId, answer.toString());

    }
}
