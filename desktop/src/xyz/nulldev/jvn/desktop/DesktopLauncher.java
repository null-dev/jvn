package xyz.nulldev.jvn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import xyz.nulldev.jvn.JVN;

import java.util.HashMap;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
		new LwjglApplication(new JVN(new HashMap<String, Object>()), config);
	}
}
