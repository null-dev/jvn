package com.nulldev.jvn.desktop;

import java.util.HashMap;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nulldev.jvn.JVN;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		//Pass launcher params
		new LwjglApplication(new JVN(new HashMap<String, Object>()), config);
	}
}
