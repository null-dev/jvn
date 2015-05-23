package com.nulldev.jvn;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Basic actor class, can specify position, rotation, scale and opacity
public class JVNActor {
	Keyframer keyframer;
	float x;
	float y;
	
	//Empty contructor
	public JVNActor() {
	}
	
	//Set a keyframer
	public JVNActor addKeyframer(Keyframer keyframer) {
		this.keyframer = keyframer;
		this.keyframer.setActor(this);
		return this;
	}
	
	//Tick
	public void tick() {
		//Tick the keyframer if there is one
		if(this.keyframer != null) {
			this.keyframer.tick();
		}
	}
	
	//Render
	public void render(SpriteBatch batch) {
		
	}
}
