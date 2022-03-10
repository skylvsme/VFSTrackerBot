package me.skylvs.vfsbot.telegram;

import lombok.RequiredArgsConstructor;
import me.skylvs.vfsbot.event.UpdateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class VfsTrackerBot extends TelegramLongPollingBot {

    private final ApplicationEventPublisher eventPublisher;

    @Value("${vfsbot.bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return "VFS Bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        eventPublisher.publishEvent(new UpdateEvent(this, update));
    }
}
