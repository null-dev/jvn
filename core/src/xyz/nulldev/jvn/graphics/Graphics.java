package xyz.nulldev.jvn.graphics;

import com.badlogic.gdx.Gdx;
import xyz.nulldev.jvn.JVN;
import xyz.nulldev.jvn.JVNConfig;
import xyz.nulldev.jvn.locale.JVNLocale;

public class Graphics {

	public static boolean fullscreen = false;
	
	public static void fullscreen(boolean toggle) {
		fullscreen = toggle;
		Gdx.graphics.setDisplayMode(Integer.parseInt(JVNConfig.readString("defaultRes").split("x")[0]),
				Integer.parseInt(JVNConfig.readString("defaultRes").split("x")[1]),
				fullscreen);
		JVN.STATE_LOGGER.info(String.format(JVNLocale.s("stateLoggerFullscreen"),
				fullscreen));
	}
}
