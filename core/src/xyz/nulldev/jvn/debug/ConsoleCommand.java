package xyz.nulldev.jvn.debug;

import java.util.ArrayList;

/**
 * Project: jvn
 * Created: 13/11/15
 * Author: nulldev
 */
public abstract class ConsoleCommand {
    String command;
    ArrayList<String> aliases = new ArrayList<>();

    public ConsoleCommand(String command) {
        this.command = command.toUpperCase();
    }

    public ConsoleCommand(String command, String... aliases) {
        this(command);
        if(aliases != null) {
            for(String alias : aliases) {
                this.aliases.add(alias.toUpperCase());
            }
        }
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }

    public abstract void invoke(String... args);

    public boolean doesMatch(String name) {
        String uName = name.toUpperCase();
        return uName.equals(command) || aliases.contains(uName);
    }

    public String getCommand() {
        return command;
    }

    public void print(String message) {
        JVNLogger.programOutput.add("INFO[COMMAND:" + command + "] " + message);
    }
}
