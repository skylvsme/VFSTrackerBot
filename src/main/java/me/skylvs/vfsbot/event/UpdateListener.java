package me.skylvs.vfsbot.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import me.skylvs.vfsbot.telegram.BotInteractor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateListener implements ApplicationListener<UpdateEvent> {

    private final BotInteractor interactor;

    @Override
    public void onApplicationEvent(UpdateEvent event) {
        val update = event.getBody();
        if (update.getMessage() == null) {
            return;
        }
        interactor.onEvent(update);
    }
}
