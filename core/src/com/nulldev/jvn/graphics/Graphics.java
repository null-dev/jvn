package com.nulldev.jvn.graphics;

import com.badlogic.gdx.Gdx;
import com.nulldev.jvn.JVN;
import com.nulldev.jvn.JVNConfig;
import com.nulldev.jvn.locale.JVNLocale;

public class Graphics {
	
	public static boolean fullscreen = false;
	
	public static void fullscreen(boolean toggle) {
		fullscreen = toggle;
		Gdx.graphics.setDisplayMode(Integer.parseInt(JVNConfig.readString("defaultRes").split("x")[0]),
				Integer.parseInt(JVNConfig.readString("defaultRes").split("x")[1]),
				fullscreen);
		JVN.stateLogger.info(String.format(JVNLocale.s("stateLoggerFullscreen"),
				fullscreen));
	}
}
