package me.skylvs.vfsbot.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class BotApiRegistrar {

    private final VfsTrackerBot vfsTrackerBot;
    private final TelegramBotsApi telegramBotsApi;

    @PostConstruct
    public void registerBot() {
        try {
            telegramBotsApi.registerBot(vfsTrackerBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
