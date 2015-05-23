package com.nulldev.jvn;

import com.nulldev.jvn.graphics.JVNActor;

//Take care of ticking all the objects in the map
public class TickManager {
	static long lastTick = 0;
	public static float tps = 0;

	//Tick everything, execute every frame
	public static void tick() {
		//Tick all active actors
		for(JVNActor actorToTick : JVN.actorList) {
			actorToTick.tick();
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
}
