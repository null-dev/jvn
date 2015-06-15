package com.nulldev.jvn;

import java.util.HashMap;
import java.util.TreeMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nulldev.jvn.debug.DebugUI;
import com.nulldev.jvn.debug.JVNLogger;
import com.nulldev.jvn.demo.DemoSplash;
import com.nulldev.jvn.graphics.Graphics;
import com.nulldev.jvn.graphics.JVNActor;
import com.nulldev.jvn.locale.JVNLocale;

/*
 * JavaVisualNovel (JVN) by: nulldev
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or (at your option) any later version.
 */

public class JVN extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static OrthographicCamera camera;
	HashMap<String, Object> launcherParams;
	public static JVNLogger stateLogger;
	JVNLogger coreLogger;
	//We want our new map to be sorted!
	//public static ArrayList<JVNActor> actorList = new ArrayList<JVNActor>();
	public static TreeMap<Integer, JVNActor> actorList= new TreeMap<>();

	//DEMO STUFF
	DemoSplash ds = new DemoSplash();
	
	//VARIABLES
	
	//JVNNative nativeCode;
    //TODO IMPLEMENT NATIVE CODE

	//Launcher can pass parameters
	public JVN(HashMap<String, Object> launcherParams) {
		this.launcherParams = launcherParams;
		if(this.launcherParams == null) {
			this.launcherParams = new HashMap<>();
		}
	}

	@Override
	public void create () {
		JVNLogger creationLogger = new JVNLogger("JVNInit");
		//Not localized at this point
		creationLogger.info("Initializing JVN...");
		//Localize the program...
		if(!JVNLocale.loadLocale(JVNConfig.readString("locale"))) {
			if(JVNConfig.readBoolean("debug")) {
				creationLogger.severe("Failed to initialize localizations! Ignoring because of debug mode! Errors will be frequent!");
			} else {
				creationLogger.severe("Failed to initialize localizations! Aborting...");
				System.exit(-1);
			}
		}
		//Create state logger
		stateLogger = new JVNLogger("StateLogger");
		//Create core logger
		coreLogger = new JVNLogger("Core");
		try{ 
			//Print device info...
			DebugUI.printDeviceInfo();
			//Create camera
			camera = new OrthographicCamera(Gdx.graphics.getWidth()
					, Gdx.graphics.getHeight());
			camera.position.set(1280/2, 720/2, 0);
			camera.update();
			batch = new SpriteBatch();
			img = new Texture("badlogic.jpg");

			//Add test drawable actor
			/*DrawableActor tempActor = new DrawableActor(new Texture(Gdx.files.internal("img/icon_white.png")));
			tempActor.setScale(0.5f);
			Keyframer tempKeyframer = new Keyframer();
			tempActor.setKeyframer(tempKeyframer);
			//You must add the keyframer to an actor first, then keyframe coords and stuff
			tempKeyframer.keyframeCoordinate(new JVNCoordinate(camera.viewportWidth,0), 2000);
			tempKeyframer.keyframeOpacity(0f, 2000);
			tempKeyframer.keyframeRotation(90, 2000);
			tempKeyframer.keyframeScale(2f, 2000);
			actorList.put(tempActor.getZIndex(), tempActor);*/
			Graphics.fullscreen(false);
		} catch(Exception e) {
			creationLogger.severe(JVNLocale.s("creationError"));
			e.printStackTrace();
			if(JVNConfig.readBoolean("debug")) {
				creationLogger.severe(JVNLocale.s("ignoringError"));
			} else {
				creationLogger.severe(JVNLocale.s("abortError"));
				System.exit(-1);
			}
		}
	}

	@Override
	public void render () {
		try {
			//Configure graphics
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//Tick the demo scene
			ds.tick();
			
			//Tick everything
			TickManager.tick();
			//Render everything
			TickManager.render(batch);

		} catch(Exception e) {
			coreLogger.severe(JVNLocale.s("tickRenderError"));
			e.printStackTrace();
			if(JVNConfig.readBoolean("debug")) {
				coreLogger.severe(JVNLocale.s("ignoringError"));
			} else {
				coreLogger.severe(JVNLocale.s("abortError"));
				System.exit(-1);
			}
		} finally {
			//Debug stuff
			//ALWAYS PUT THIS LAST! (So it can be ontop of everything)
			DebugUI.debugLoop(camera);
		}
	}

	@Override
	public void dispose() {
		DebugUI.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		//Resize the camera
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
		
		stateLogger.info(String.format(JVNLocale.s("stateLoggerResized"), height, width));
	}

	@Override
	public void pause() {
		stateLogger.info(JVNLocale.s("stateLoggerPaused"));
	}

	@Override
	public void resume() {
		stateLogger.info(JVNLocale.s("stateLoggerResumed"));
	}
}
