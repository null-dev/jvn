package xyz.nulldev.jvn.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import xyz.nulldev.jvn.JVN;
import xyz.nulldev.jvn.graphics.SimpleTextureActor;
import xyz.nulldev.jvn.graphics.JVNCoordinate;
import xyz.nulldev.jvn.graphics.Keyframer;

public class DemoSplash {

	/*
	 * As simple example for a fading splash!
	 */
	
	boolean splashStarted = false;
	boolean splashMiddle = false;
	boolean splashMiddleWaited = false;
	boolean splashDone = false;

    SimpleTextureActor demoSplashLogo;

	//Tick the scene
	public void tick() {
		if(!splashStarted) {
			demoSplashLogo = new SimpleTextureActor(new Texture(Gdx.files.internal("img/icon_white.png")));
			demoSplashLogo.setOpacity(0f);
			demoSplashLogo.setScale(20f);
			demoSplashLogo.setRotation(-50f);
			JVNCoordinate coords = new JVNCoordinate();
			coords.setX(JVN.CAMERA.position.x / 2 - (demoSplashLogo.getScale() * demoSplashLogo.getTexture().getWidth() / 2));
			coords.setY(JVN.CAMERA.position.y / 2 - (demoSplashLogo.getScale() * demoSplashLogo.getTexture().getHeight() / 3));
			demoSplashLogo.setCoordinates(coords);
			Keyframer demoKeyframer = demoSplashLogo.getKeyframer();
			demoKeyframer.keyframeOpacity(1f, 2000);
			demoKeyframer.keyframeScale(0.5f, 2000);
			demoKeyframer.keyframeRotation(0f, 2000);
            demoSplashLogo.setZIndex(999);
            demoSplashLogo.setVisible(true);
			splashStarted = true;
		} else if(!splashMiddle) {
			demoSplashLogo.getCoordinates().setX(JVN.CAMERA.position.x/2-(demoSplashLogo.getScale()*demoSplashLogo.getTexture().getWidth()/2));
			demoSplashLogo.getCoordinates().setY(JVN.CAMERA.position.y/2-(demoSplashLogo.getScale()*demoSplashLogo.getTexture().getHeight()/3));
			if(demoSplashLogo.getKeyframer().opacityDone()) {
				demoSplashLogo.getKeyframer().keyframeOpacity(1f, 2000);
				splashMiddle = true;
			}
		} else if(!splashMiddleWaited) {
			if(demoSplashLogo.getKeyframer().opacityDone()) {
				demoSplashLogo.getKeyframer().keyframeOpacity(0f, 2000);
				splashMiddleWaited = true;
			}
		} else if(!splashDone) {
			if(demoSplashLogo.getKeyframer().opacityDone()) {
				JVN.ACTOR_LIST.remove(999);
				splashDone = true;
			}
		}
	}
}
