package me.skylvs.vfsbot.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateListener implements ApplicationListener<UpdateEvent> {

    @Override
    public void onApplicationEvent(UpdateEvent event) {
        System.out.println(event.getBody().getMessage());
    }
}
