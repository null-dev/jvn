package com.nulldev.jvn.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.nulldev.jvn.JVN;
import com.nulldev.jvn.graphics.DrawableActor;
import com.nulldev.jvn.graphics.JVNCoordinate;
import com.nulldev.jvn.graphics.Keyframer;

public class DemoSplash {

	/*
	 * As simple example for a fading splash!
	 */
	
	boolean splashStarted = false;
	boolean splashMiddle = false;
	boolean splashMiddleWaited = false;
	boolean splashDone = false;

	//Tick the scene
	public void tick() {
		if(!splashStarted) {
			DrawableActor demoSplashLogo = new DrawableActor(new Texture(Gdx.files.internal("img/icon_white.png")));
			demoSplashLogo.setOpacity(0f);
			demoSplashLogo.setScale(20f);
			demoSplashLogo.setRotation(-50f);
			JVNCoordinate coords = new JVNCoordinate();
			coords.setX(JVN.camera.position.x/2-(demoSplashLogo.getScale()*demoSplashLogo.getTexture().getWidth()/2));
			coords.setY(JVN.camera.position.y/2-(demoSplashLogo.getScale()*demoSplashLogo.getTexture().getHeight()/3));
			demoSplashLogo.setCoordinates(coords);
			Keyframer demoKeyframer = new Keyframer();
			demoSplashLogo.setKeyframer(demoKeyframer);
			demoKeyframer.keyframeOpacity(1f, 2000);
			demoKeyframer.keyframeScale(0.5f, 2000);
			demoKeyframer.keyframeRotation(0f, 2000);
			JVN.actorList.put(999, demoSplashLogo);
			splashStarted = true;
		} else if(!splashMiddle) {
			DrawableActor demoSplashLogo = (DrawableActor) JVN.actorList.get(999);
			demoSplashLogo.getCoordinates().setX(JVN.camera.position.x/2-(demoSplashLogo.getScale()*demoSplashLogo.getTexture().getWidth()/2));
			demoSplashLogo.getCoordinates().setY(JVN.camera.position.y/2-(demoSplashLogo.getScale()*demoSplashLogo.getTexture().getHeight()/3));
			if(demoSplashLogo.getKeyframer().opacityDone()) {
				demoSplashLogo.getKeyframer().keyframeOpacity(1f, 2000);
				splashMiddle = true;
			}
		} else if(!splashMiddleWaited) {
			DrawableActor demoSplashLogo = (DrawableActor) JVN.actorList.get(999);
			if(demoSplashLogo.getKeyframer().opacityDone()) {
				demoSplashLogo.getKeyframer().keyframeOpacity(0f, 2000);
				splashMiddleWaited = true;
			}
		} else if(!splashDone) {
			DrawableActor demoSplashLogo = (DrawableActor) JVN.actorList.get(999);
			if(demoSplashLogo.getKeyframer().opacityDone()) {
				JVN.actorList.remove(999);
				splashDone = true;
			}
		}
	}
}
