package com.nulldev.jvn.debug;

import java.util.ArrayList;
import java.util.logging.Logger;

//Loggerz for the debugz
public class JVNLogger extends Logger {
	
	String taskName;
	public static ArrayList<String> programOutput = new ArrayList<String>();
	
	//Create a new logger with a name for the task
	public JVNLogger(String task) {
		super("JVN", null);
		this.taskName = task;
		sysout("[JVNEngine][JVNLogger][INFO]", "Created new logger: " + taskName);
		programOutput.add("INFO[JVNEngine][JVNLogger][INFO] Created new logger: " + taskName);
	}
	
	@Override
	public void info(String message) {
		super.info("[JVNEngine][" + taskName+"][INFO] " + message);
		sysout("[JVNEngine][" + taskName+"][INFO]", message);
		programOutput.add("INFO[JVNEngine][" + taskName+"][INFO] " + message);
	}
	@Override
	public void warning(String message) {
		super.info("[JVNEngine][" + taskName+"][WARN] " + message);
		sysout("[JVNEngine][" + taskName+"][WARN]", message);
		programOutput.add("WARN[JVNEngine][" + taskName+"][WARN] " + message);
	}
	@Override
	public void severe(String message) {
		super.info("[JVNEngine][" + taskName+"][SEVR] " + message);
		sysout("[JVNEngine][" + taskName+"][SEVR]", message);
		programOutput.add("SEVR[JVNEngine][" + taskName+"][SEVR] " + message);
	}
	
	//System.out.println, in a function in case I need to change it later
	public static void sysout(String prefix, String message) {
		System.out.println(prefix + " " + message);
		//programOutput.add(prefix + " " + message); Moved to individual statements
	}
}
