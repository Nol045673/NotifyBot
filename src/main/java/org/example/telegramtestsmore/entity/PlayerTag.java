package org.example.telegramtestsmore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerTag {
    @Id
    private String tag;
    private String nikename;
    private boolean active = true;

}
