package org.example.telegramtestsmore.service.player;

import java.util.List;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

@Service
public class CreateTag {
    public String createTag(String tag, List<MessageEntity> entity) {
        String[] parts = tag.split(" ");
        String tagname;
        if (parts[1].contains("@")) {
            tagname = parts[1];
        } else {
            tagname = linkMaker(entity);
        }
        return tagname;
    }

    private String linkMaker(List<MessageEntity> entity) {
        String link = "";
        for (MessageEntity ent : entity) {
            if (ent.getType().equals("text_mention")) { // Например, @username
                Long mention = ent.getUser().getId();
                String name = ent.getUser().getFirstName();
                link = "<a href=\"tg://user?id=" + mention + "\">" + name + "</a>";
//                <a href=\"tg://user?id=\">Example</a>
            }
        }
        return link;
    }
}
