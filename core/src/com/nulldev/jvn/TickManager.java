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

class JVNBubbleSorter {
	  
    // logic to sort the elements
    public static JVNActor[] bubble_srt(JVNActor array[]) {
        int n = array.length;
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (array[i].getZIndex() > array[k].getZIndex()) {
                    swapNumbers(i, k, array);
                }
            }
        }
        return array;
    }
  
    private static void swapNumbers(int i, int j, JVNActor[] array) {
  
        JVNActor temp;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}