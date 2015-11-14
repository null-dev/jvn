package xyz.nulldev.jvn.debug.cmd;

import xyz.nulldev.jvn.debug.ConsoleCommand;
import xyz.nulldev.jvn.graphics.Graphics;

/**
 * Project: jvn
 * Created: 13/11/15
 * Author: nulldev
 */
public class FullscreenCommand extends ConsoleCommand {
    public FullscreenCommand(String command) {
        super(command);
    }

    @Override
    public void invoke(String... args) {
        Graphics.fullscreen(!Graphics.fullscreen);
    }
}
