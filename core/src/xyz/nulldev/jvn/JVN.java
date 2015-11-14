package xyz.nulldev.jvn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.nulldev.jvn.debug.DebugUI;
import xyz.nulldev.jvn.debug.JVNLogger;
import xyz.nulldev.jvn.demo.DemoSplash;
import xyz.nulldev.jvn.graphics.Graphics;
import xyz.nulldev.jvn.graphics.JVNActor;
import xyz.nulldev.jvn.locale.JVNLocale;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

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
    public static OrthographicCamera CAMERA;
    HashMap<String, Object> launcherParams;
    public static JVNLogger STATE_LOGGER;
    JVNLogger coreLogger;
    DebugUI DEBUG_UI;

    //We want our new map to be sorted!
    public static TreeMap<Integer, JVNActor> ACTOR_LIST = new TreeMap<>();
    public static File ASSET_ROOT = new File(".");

    //DEMO STUFF
    DemoSplash ds = new DemoSplash();

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
        STATE_LOGGER = new JVNLogger("StateLogger");
        //Create core logger
        coreLogger = new JVNLogger("Core");
        try{
            //Create CAMERA
            CAMERA = new OrthographicCamera(Gdx.graphics.getWidth()
                    , Gdx.graphics.getHeight());
            CAMERA.position.set(1280/2, 720/2, 0);
            CAMERA.update();
            batch = new SpriteBatch();
            img = new Texture(new File(ASSET_ROOT, "badlogic.jpg").getAbsolutePath());

            //Add test drawable actor
			/*SimpleTextureActor tempActor = new SimpleTextureActor(new Texture(Gdx.files.internal("img/icon_white.png")));
			tempActor.setScale(0.5f);
			Keyframer tempKeyframer = new Keyframer();
			tempActor.setKeyframer(tempKeyframer);
			//You must add the keyframer to an actor first, then keyframe coords and stuff
			tempKeyframer.keyframeCoordinate(new JVNCoordinate(CAMERA.viewportWidth,0), 2000);
			tempKeyframer.keyframeOpacity(0f, 2000);
			tempKeyframer.keyframeRotation(90, 2000);
			tempKeyframer.keyframeScale(2f, 2000);
			ACTOR_LIST.put(tempActor.getZIndex(), tempActor);*/
            Graphics.fullscreen(false);
        } catch(Exception e) {
            e.printStackTrace();
            creationLogger.severe(JVNLocale.s("creationError"));
            if(JVNConfig.readBoolean("debug")) {
                creationLogger.severe(JVNLocale.s("ignoringError"));
            } else {
                creationLogger.severe(JVNLocale.s("abortError"));
                System.exit(-1);
            }
        }
        DEBUG_UI = new DebugUI();
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
            DEBUG_UI.debugLoop(CAMERA);
        }
    }

    @Override
    public void dispose() {
        DEBUG_UI.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        //Resize the CAMERA
        CAMERA.viewportHeight = height;
        CAMERA.viewportWidth = width;
        CAMERA.update();

        STATE_LOGGER.info(String.format(JVNLocale.s("stateLoggerResized"), height, width));
    }

    @Override
    public void pause() {
        STATE_LOGGER.info(JVNLocale.s("stateLoggerPaused"));
    }

    @Override
    public void resume() {
        STATE_LOGGER.info(JVNLocale.s("stateLoggerResumed"));
    }

    public DebugUI getDebugUi() {
        return DEBUG_UI;
    }

    public void setDebugUi(DebugUI DEBUG_UI) {
        this.DEBUG_UI = DEBUG_UI;
    }
}
