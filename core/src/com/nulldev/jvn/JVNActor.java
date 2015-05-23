package com.nulldev.jvn;

public class JVNActor {
	Keyframer keyframer;
	
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
}
