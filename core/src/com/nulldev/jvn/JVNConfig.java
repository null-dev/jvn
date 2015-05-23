package com.nulldev.jvn;

import java.util.HashMap;

/*
 * Allows easy configuration of specific components of JVN
 */

public class JVNConfig {
	private final static HashMap<String, Object> config =
			new HashMap<String, Object>();
	
	//FILL THE CONFIGURATION HERE
	static {
		//DEBUG TOGGLE
		putObject("debug", true);
		
		//Where are the locale files stored?
		putObject("locale-dir", "locale");
		//What is the current locale?
		putObject("locale", "en-US");
	}
	
	//Get the raw config map...
	public final static HashMap<String, Object> getConfigHashMap() {
		return config;
	}
	
	public final static void putObject(String key, Object obj) {
		config.put(key.toUpperCase(), obj);
	}
	
	public final static Object readObject(String key) {
		return config.get(key.toUpperCase());
	}
	
	public final static String readString(String key) {
		return (String) JVNConfig.readObject(key);
	}
	
	public final static int readInt(String key) {
		return (Integer) JVNConfig.readObject(key);
	}
	
	public final static float readFloat(String key) {
		return (Float) JVNConfig.readObject(key);
	}
	
	public final static double readDouble(String key) {
		return (Double) JVNConfig.readObject(key);
	}
	
	public final static long readLong(String key) {
		return (Long) JVNConfig.readObject(key);
	}
	
	public final static boolean readBoolean(String key) {
		return (Boolean) JVNConfig.readObject(key);
	}
}
