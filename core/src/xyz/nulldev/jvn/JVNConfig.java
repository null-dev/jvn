package xyz.nulldev.jvn;

import java.io.File;
import java.util.HashMap;

/*
 * Allows easy configuration of specific components of JVN
 */

public class JVNConfig {
	private final static HashMap<String, Object> config =
            new HashMap<>();
	
	//FILL THE CONFIGURATION HERE
	static {
		//DEBUG TOGGLE
		putObject("debug", true);
		
		//Where are the locale files stored?
		putObject("locale-dir", new File(JVN.ASSET_ROOT, "locale"));
		//What is the current locale?
		putObject("locale", "en-US");
		//Default screen resoulution
		putObject("defaultRes", "1280x720");
	}
	
	//Get the raw config map...
	public static HashMap<String, Object> getConfigHashMap() {
		return config;
	}
	
	public static void putObject(String key, Object obj) {
		config.put(key.toUpperCase(), obj);
	}
	
	public static Object readObject(String key) {
		return config.get(key.toUpperCase());
	}
	
	public static String readString(String key) {
		return (String) JVNConfig.readObject(key);
	}
	
	public static int readInt(String key) {
		return (Integer) JVNConfig.readObject(key);
	}
	
	public static float readFloat(String key) {
		return (Float) JVNConfig.readObject(key);
	}
	
	public static double readDouble(String key) {
		return (Double) JVNConfig.readObject(key);
	}
	
	public static long readLong(String key) {
		return (Long) JVNConfig.readObject(key);
	}
	
	public static boolean readBoolean(String key) {
		return (Boolean) JVNConfig.readObject(key);
	}

    public static File readFile(String key) {return (File) JVNConfig.readObject(key);}
}
