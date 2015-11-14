package xyz.nulldev.jvn.debug.cmd;

import xyz.nulldev.jvn.TickManager;
import xyz.nulldev.jvn.debug.ConsoleCommand;
import xyz.nulldev.jvn.locale.JVNLocale;

/**
 * Project: jvn
 * Created: 13/11/15
 * Author: nulldev
 */
public class GetTpsCommand extends ConsoleCommand {
    public GetTpsCommand(String command) {
        super(command);
    }

    public GetTpsCommand(String command, String... aliases) {
        super(command, aliases);
    }

    @Override
    public void invoke(String... args) {
        print(JVNLocale.s("getTpsResult") + TickManager.tps);
    }
}
