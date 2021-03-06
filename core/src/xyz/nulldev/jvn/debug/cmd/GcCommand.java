package xyz.nulldev.jvn.debug.cmd;

import xyz.nulldev.jvn.debug.ConsoleCommand;
import xyz.nulldev.jvn.locale.JVNLocale;

/**
 * Project: jvn
 * Created: 13/11/15
 * Author: nulldev
 */
public class GcCommand extends ConsoleCommand {
    public GcCommand(String command) {
        super(command);
    }

    @Override
    public void invoke(String... args) {
        print(JVNLocale.s("garbageCollectInfo"));
        System.gc();
    }
}
