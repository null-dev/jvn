package com.nulldev.jvn;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nulldev.jvn.debug.DebugUI;
import com.nulldev.jvn.debug.JVNLogger;
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
	OrthographicCamera camera;
	HashMap<String, Object> launcherParams;
	JVNLogger stateLogger;
	static ArrayList<JVNActor> actorList = new ArrayList<JVNActor>();
	
	JVNNative nativeCode;
	
	//Launcher can pass parameters
	public JVN(HashMap<String, Object> launcherParams) {
		this.launcherParams = launcherParams;
		if(this.launcherParams == null) {
			this.launcherParams = new HashMap<String, Object>();
		}
	}
	
	@Override
	public void create () {
		JVNLogger creationLogger = new JVNLogger("JVNInit");
		//Not localized at this point
		creationLogger.info("Initializing JVN...");
		//Localize the program...
		if(!JVNLocale.loadLocale(JVNConfig.readString("locale"))) {
			creationLogger.severe("Failed to initialize localizations! Aborting...");
			System.exit(-1);
		}
		//Create state logger
		stateLogger = new JVNLogger("StateLogger");
		//Print device info...
		DebugUI.printDeviceInfo();
		//Create camera
		camera = new OrthographicCamera(Gdx.graphics.getWidth()
				, Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		camera.update();
		
		//Configure graphics
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		
		TickManager.tick();
		
		//Debug stuff
		//ALWAYS PUT THIS LAST! (So it can be ontop of everything)
		DebugUI.debugLoop(camera);
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
