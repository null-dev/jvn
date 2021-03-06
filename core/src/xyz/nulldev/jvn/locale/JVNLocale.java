package xyz.nulldev.jvn.locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import xyz.nulldev.jvn.JVNConfig;
import xyz.nulldev.jvn.debug.JVNLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/*
 * JVNLocale allows JVN and JVN novels to be easily translated into other languages with little or no modification of the original code!
 * An example translation is specified in the locale folder.
 */

public class JVNLocale {
	public static HashMap<String, String> messageList = new HashMap<>();
	private static JVNLogger logger = new JVNLogger("JLocale");
    public static boolean localeInitialized = false;

	//Only call this once, on the creation of JVN or when the locale is changed
	//Returns false on failure, otherwise, returns true!
	public static boolean loadLocale(String locale) {
		logger.info("Refreshing locale: " + locale);
		
		messageList.clear();
		//Construct locale file
		String localeFile = new File(JVNConfig.readFile("locale-dir"), locale + ".jvnl").getAbsolutePath();
		BufferedReader br;
		try {
			br = new BufferedReader(Gdx.files.internal(localeFile).reader());
		} catch (GdxRuntimeException e) {
			logger.warning("Failed to find locale file for: "
					+ locale
					+", falling back to: "
					+ JVNConfig.readString("locale"));
			e.printStackTrace();
			
			//Try the default locale now...
			locale = JVNConfig.readString("locale");
			localeFile = new File(JVNConfig.readFile("locale-dir"), locale + ".jvnl").getAbsolutePath();
			try {
				br = new BufferedReader(Gdx.files.internal(localeFile).reader());
			} catch (GdxRuntimeException e1) {
				logger.severe("Failed to refresh locale, could not fall back to default locale!");
				e1.printStackTrace();
				return false;
			}
		}
		try {
			int curLine = 0;
			for(String line; (line = br.readLine()) != null; ) {
				curLine++;
				//Is comment?
				if(line.startsWith("#") || line.startsWith("//")) {
					logger.info("Comment found @ line " + curLine);
				} else {
					//Is valid line?
					int colonLoc = line.indexOf(':');
					if(colonLoc == -1) {
						logger.warning("Invalid entry @ line " + curLine + ", skipping!");
					} else {
						//Is there a space trailing the colon?
						if(line.charAt(colonLoc+1) == ' ') {
							//Remove the space
							line = line.replaceFirst(": ", ":");
						}
						//Split the line into two
						String[] splitLine = line.split(":", 2);
						messageList.put(splitLine[0], splitLine[1]);
					}
				}
				
			}
			logger.info("Added " + messageList.size() + " messages!");
		} catch (IOException e) {
			logger.severe("Failed to refresh locale, Exception reading locale file!");
			e.printStackTrace();
			return false;
		} finally {
			//Close it at the end
            try {
                br.close();
            } catch (IOException e) {
                logger.warning("Failed to close BufferedReader, ignoring!");
                e.printStackTrace();
            }
        }
        localeInitialized = true;
		logger.info("Refreshed locale: " + locale + " successfully!");
		return true;
	}
	
	//Used for getting the actual string from the locale
	public static String s(String text) {
        if(!localeInitialized) {
            throw new IllegalStateException("Locale not initialized!");
        }
		return messageList.get(text);
	}
}
