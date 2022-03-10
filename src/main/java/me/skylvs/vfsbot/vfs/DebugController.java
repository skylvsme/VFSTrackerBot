package me.skylvs.vfsbot.vfs;

import lombok.RequiredArgsConstructor;
import me.skylvs.vfsbot.event.ApplicationCheckScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DebugController {

    private final ApplicationCheckScheduler scheduler;

    @GetMapping(path = "debug")
    public void debug() {
        scheduler.checkApplications();
    }

}
