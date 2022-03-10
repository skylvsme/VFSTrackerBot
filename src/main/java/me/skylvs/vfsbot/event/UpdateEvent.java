package me.skylvs.vfsbot.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
public class UpdateEvent extends ApplicationEvent {

    private final Update body;

    public UpdateEvent(Object source, Update body) {
        super(source);
        this.body = body;
    }
}
