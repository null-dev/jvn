package xyz.nulldev.jvn.debug.cmd;

import xyz.nulldev.jvn.debug.ConsoleCommand;

/**
 * Project: jvn
 * Created: 13/11/15
 * Author: nulldev
 */
public class ExitCommand extends ConsoleCommand {
    public ExitCommand(String command, String... aliases) {
        super(command, aliases);
    }

    @Override
    public void invoke(String... args) {
        System.exit(0);
    }
}
