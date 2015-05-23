package com.nulldev.jvn;

//Basic keyframer class
public class Keyframer {
	JVNActor assignedActor;
	
	//Keyframes for coordinates
	JVNCoordinate nextCoord = null;
	float coordIncrementRateX = 0;
	float coordIncrementRateY = 0;
	long coordLastTickMs = 0;
	long coordFinishMs = 0;
	
	float rotationIncrementRate = 0;
	long rotationLastTickMs = 0;
	long rotationFinishMs = 0;
	
	//Internal variable to set actor
	public Keyframer setActor(JVNActor assignedActor) {
		this.assignedActor = assignedActor;
		return this;
	}
	
	//Keyframe a coordinate
	public Keyframer keyframeCoordinate(JVNCoordinate nextCoord, long ms) {
		this.nextCoord = nextCoord;
		this.coordIncrementRateX = (nextCoord.getX()
				-assignedActor.getCoordinates().getX())/ms;
		this.coordIncrementRateY = (nextCoord.getY()
				-assignedActor.getCoordinates().getY())/ms;
		this.coordLastTickMs = 0;
		this.coordFinishMs = System.currentTimeMillis() + ms;
		return this;
	}
	
	public Keyframer keyframeRotation(float nextAngle, long ms) {
		this.rotationIncrementRate = nextAngle/ms;
		this.rotationLastTickMs = 0;
		this.rotationFinishMs = System.currentTimeMillis() + ms;
		return this;
	}
	
	//Tick
	public void tick() {
		//Tick coord
		if(nextCoord != null) {
			tickCoord();
		}
		//Tick rotation
		if(rotationIncrementRate != 0) {
			tickRotation();
		}
	}
	
	//Tick coord
	void tickCoord() {
		long curMs = System.currentTimeMillis();
		//Reset if we are done!
		if(curMs > coordFinishMs) {
			nextCoord = null;
			coordIncrementRateX = 0;
			coordIncrementRateY = 0;
			coordLastTickMs = 0;
			coordFinishMs = 0;
		} else {
			long toIncrement = curMs - coordLastTickMs;
			//Update X
			assignedActor.getCoordinates().setX(assignedActor.getCoordinates().getX()
					+(coordIncrementRateX
							*toIncrement));
			//Update Y
			assignedActor.getCoordinates().setY(assignedActor.getCoordinates().getY()
					+(coordIncrementRateY
							*toIncrement));
			//Update last tick
			coordLastTickMs = curMs;
		}
	}
	
	//Tick rotation
	void tickRotation() {
		long curMs = System.currentTimeMillis();
		if(curMs > rotationFinishMs) {
			rotationIncrementRate = 0;
			rotationLastTickMs = 0;
			rotationFinishMs = 0;
		} else {
			long toIncrement = curMs - coordLastTickMs;
			//Update rotation
			assignedActor.setRotation(assignedActor.getRotation()
					+(rotationIncrementRate
							*toIncrement));
			//Update last tick
			rotationLastTickMs = curMs;
		}
	}
}
