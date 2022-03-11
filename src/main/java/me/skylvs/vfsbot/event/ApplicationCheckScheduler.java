package me.skylvs.vfsbot.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.skylvs.vfsbot.telegram.BotInteractor;
import me.skylvs.vfsbot.user.UserSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static me.skylvs.vfsbot.user.UserSessionState.READY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationCheckScheduler {

    private final UserSessionRepository sessionRepository;
    private final BotInteractor interactor;

    @Scheduled(cron = "* * 0 * * ?")
    public void checkApplications() {
        sessionRepository.findByStage(READY).forEach(session -> {
            try {
                interactor.sendUserApplicationStatus(session);
            } catch (TelegramApiException e) {
                log.error("Failed to check application: ", e);
            }
        });
    }

}
