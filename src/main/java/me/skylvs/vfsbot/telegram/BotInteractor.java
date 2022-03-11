package me.skylvs.vfsbot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import me.skylvs.vfsbot.message.Constants;
import me.skylvs.vfsbot.user.UserSession;
import me.skylvs.vfsbot.user.UserSessionRepository;
import me.skylvs.vfsbot.vfs.ApplicationChecker;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

import static me.skylvs.vfsbot.user.UserSessionState.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotInteractor {

    private final VfsTrackerBot bot;
    private final ApplicationChecker checker;
    private final UserSessionRepository sessionRepository;

    public void onEvent(Update update) {
        val message = update.getMessage();

        sessionRepository.findByChatId(message.getChatId()).ifPresentOrElse(
                it -> onUserSendMessage(it, message),
                () -> onUserFirstMessage(message)
        );
    }

    private void onUserFirstMessage(Message message) {
        val userSession = new UserSession();
        userSession.setChatId(message.getChatId());
        userSession.setStage(REF_NUMBER_WAITING);
        sessionRepository.save(userSession);

        val sendMessage = new SendMessage(message.getChatId().toString(), Constants.GREETINGS_MESSAGE);
        try {
            bot.execute(sendMessage);
            log.debug("Greetings message sent");
        } catch (TelegramApiException e) {
            log.error("Failed to send greetings message: ", e);
        }
    }

    @SneakyThrows
    private void onUserSendMessage(UserSession session, Message message) {
        val text = message.getText();
        log.info("Received message: {}", text);
        if (session.getStage() == REF_NUMBER_WAITING) {
            val parts = validateReferenceNumber(text);
            if (parts == null)
                bot.execute(new SendMessage(session.getChatId().toString(), Constants.INVALID_REFERENCE_NUMBER_FORMAT));
            else {
                session.setReferenceNumber(text);
                session.setStage(BIRTHDATE_WAITING);
                sessionRepository.save(session);

                bot.execute(new SendMessage(message.getChatId().toString(), Constants.BIRTHDATE_WAITING));
            }
            return;
        }

        if (session.getStage() == BIRTHDATE_WAITING) {
            if (validateBirthDate(text)) {
                session.setBirthDate(text);
                session.setStage(READY);
                sessionRepository.save(session);

                bot.execute(new SendMessage(message.getChatId().toString(), Constants.READY_TO_GO));

                sendUserApplicationStatus(session);
            } else {
                bot.execute(new SendMessage(message.getChatId().toString(), Constants.INVALID_BIRTHDATE_FORMAT));
            }
            return;
        }
    }

    public void sendUserApplicationStatus(UserSession session) throws TelegramApiException {
        if (session.getReferenceNumber() == null || session.getBirthDate() == null) return;

        val status = checker.getApplicationStatus(validateReferenceNumber(session.getReferenceNumber()), session.getBirthDate());

        if (!Objects.equals(session.getApplicationStatus(), status)) {
            session.setApplicationStatus(status);
            sessionRepository.save(session);

            bot.execute(new SendMessage(session.getChatId().toString(), status));
        }
    }

    private String[] validateReferenceNumber(String referenceNumber) {
        val arr = referenceNumber.split("/");
        if (arr.length == 4) return arr;
        else return null;
    }

    private boolean validateBirthDate(String birthDate) {
        return birthDate.matches("^\\d{2}\\/\\d{2}\\/\\d{4}$");
    }


}
