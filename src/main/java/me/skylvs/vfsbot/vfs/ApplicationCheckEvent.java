package me.skylvs.vfsbot.vfs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationCheckEvent {

    private String response;
    private boolean error;

}
