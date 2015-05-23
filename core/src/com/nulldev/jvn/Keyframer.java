package com.nulldev.jvn;

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
