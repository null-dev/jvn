package com.nulldev.jvn;

import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nulldev.jvn.graphics.JVNActor;

//Take care of ticking all the objects in the map
public class TickManager {
	static long lastTick = 0;
	public static float tps = 0;
	
	//Tick everything, execute every frame
	public static void tick() {
		//Tick all active actors
		for(Entry<Integer, JVNActor> actorToTick : JVN.actorList.entrySet()) {
			actorToTick.getValue().tick();
		}

		//Calculate TPS
		try {
			tps = 1000/(System.currentTimeMillis() - lastTick);
		} catch (ArithmeticException divsByZero) {
			//Division by zero somehow
			tps = 0;
		}
		lastTick = System.currentTimeMillis();
	}
	
	//Render everything, execute every frame
	public static void render(SpriteBatch batch) {
		for(Entry<Integer, JVNActor> actorToRender : JVN.actorList.entrySet()) {
			actorToRender.getValue().render(batch);
		}
	}
}