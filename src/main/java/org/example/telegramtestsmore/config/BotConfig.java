package org.example.telegramtestsmore.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
public class BotConfig {
    String token = "7067379600:AAGPmGYcSWpBcrQN66UhPwNJflz4Fq7cpLA";
    String botName = "ITMyTestsBot";
}
