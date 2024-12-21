package org.example.telegramtestsmore.repository;

import org.example.telegramtestsmore.entity.PlayerTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerTag, String> {
    PlayerTag findByNikename(String nikename);
    PlayerTag findByTag(String tag);
    void deleteByTag(String tag);
}
