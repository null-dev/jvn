package xyz.nulldev.jvn.debug;

import java.util.ArrayList;
import java.util.logging.Logger;

//Loggerz for the debugz
public class JVNLogger extends Logger {
	
	String taskName;
	public static ArrayList<String> programOutput = new ArrayList<>();
	
	//Create a new logger with a name for the task
	public JVNLogger(String task) {
		super("JVN", null);
		this.taskName = task;
		sysout("[JVNLogger][INFO]", "Created new logger: " + taskName);
		programOutput.add("INFO[JVNLogger][INFO] Created new logger: " + taskName);
	}
	
	@Override
	public void info(String message) {
		super.info("[" + taskName+"][INFO] " + message);
		sysout("[" + taskName+"][INFO]", message);
		programOutput.add("INFO[" + taskName+"][INFO] " + message);
	}
	@Override
	public void warning(String message) {
		super.info("[" + taskName+"][WARN] " + message);
		sysout("[" + taskName+"][WARN]", message);
		programOutput.add("WARN[" + taskName+"][WARN] " + message);
	}
	@Override
	public void severe(String message) {
		super.info("[" + taskName+"][SEVR] " + message);
		sysout("[" + taskName+"][SEVR]", message);
		programOutput.add("SEVR[" + taskName+"][SEVR] " + message);
	}
	
	//System.out.println, in a function in case I need to change it later
	public static void sysout(String prefix, String message) {
		System.out.println(prefix + " " + message);
		//programOutput.add(prefix + " " + message); Moved to individual statements
	}
}
