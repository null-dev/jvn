package com.nulldev.jvn;

//Basic keyframer class
public class Keyframer {
	JVNActor assignedActor;
	
	//Internal variable to set actor
	public Keyframer setActor(JVNActor assignedActor) {
		this.assignedActor = assignedActor;
		return this;
	}
	
	//Tick
	public void tick() {
	}
}
