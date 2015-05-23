package com.nulldev.jvn;

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
		tps = 1000/(System.currentTimeMillis() - lastTick);
		lastTick = System.currentTimeMillis();
	}
}
