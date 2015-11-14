package xyz.nulldev.jvn.graphics;

//Basic keyframer class
public class Keyframer {
	JVNActor assignedActor;

	//Keyframes for coordinates
	float coordIncrementRateX = 0;
	float coordIncrementRateY = 0;
	long coordLastTickMs = 0;
	long coordFinishMs = 0;

	//Keyframes for rotation
	float rotationIncrementRate = 0;
	long rotationLastTickMs = 0;
	long rotationFinishMs = 0;

	//Keyframes for scale
	float scaleIncrementRate = 0;
	long scaleLastTickMs = 0;
	long scaleFinishMs = 0;

	//Keyframes for opacity
	float opacityIncrementRate = 0;
	long opacityLastTickMs = 0;
	long opacityFinishMs = 0;

	//Internal variable to set actor
	public Keyframer setActor(JVNActor assignedActor) {
		this.assignedActor = assignedActor;
		return this;
	}

	//Keyframe a coordinate
	public Keyframer keyframeCoordinate(JVNCoordinate nextCoord, long ms) {
		this.coordIncrementRateX = (nextCoord.getX()
				-assignedActor.getCoordinates().getX())/ms;
		this.coordIncrementRateY = (nextCoord.getY()
				-assignedActor.getCoordinates().getY())/ms;
		this.coordLastTickMs = System.currentTimeMillis();
		this.coordFinishMs = System.currentTimeMillis() + ms;
		return this;
	}

	//Keyframe a rotation
	public Keyframer keyframeRotation(float nextAngle, long ms) {
		this.rotationIncrementRate = (nextAngle
				-assignedActor.getRotation())/ms;
		this.rotationLastTickMs = System.currentTimeMillis();
		this.rotationFinishMs = System.currentTimeMillis() + ms;
		return this;
	}

	//Keyframe a scale
	public Keyframer keyframeScale(float nextScale, long ms) {
		this.scaleIncrementRate = (nextScale
				-assignedActor.getScale())/ms;
		this.scaleLastTickMs = System.currentTimeMillis();
		this.scaleFinishMs = System.currentTimeMillis() + ms;
		return this;
	}

	//Keyframe a opacity
	public Keyframer keyframeOpacity(float nextOpacity, long ms) {
		this.opacityIncrementRate = (nextOpacity
				-assignedActor.getOpacity())/ms;
		this.opacityLastTickMs = System.currentTimeMillis();
		this.opacityFinishMs = System.currentTimeMillis() + ms;
		return this;
	}

	//Tick
	public void tick() {
		//Tick coord
		if(!coordinatesDone()) {
			tickCoord();
		}
		//Tick rotation
		if(!rotationDone()) {
			tickRotation();
		}
		//Tick scale
		if(!scaleDone()) {
			tickScale();
		}
		//Tick opacity
		if(!opacityDone()) {
			tickOpacity();
		}
	}

	//Tick coord
	void tickCoord() {
		long curMs = System.currentTimeMillis();
		//Reset if we are done!
		if(curMs > coordFinishMs) {
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
			long toIncrement = curMs - rotationLastTickMs;
			//Update rotation
			assignedActor.setRotation(assignedActor.getRotation()
					+(rotationIncrementRate
							*toIncrement));
			//Update last tick
			rotationLastTickMs = curMs;
		}
	}

	//Tick scale
	void tickScale() {
		long curMs = System.currentTimeMillis();
		if(curMs > scaleFinishMs) {
			scaleIncrementRate = 0;
			scaleLastTickMs = 0;
			scaleFinishMs = 0;
		} else {
			long toIncrement = curMs - scaleLastTickMs;
			//Update scale
			assignedActor.setScale(assignedActor.getScale()
					+(scaleIncrementRate
							*toIncrement));
			//Update last tick
			scaleLastTickMs = curMs;
		}
	}

	//Tick opacity
	void tickOpacity() {
		long curMs = System.currentTimeMillis();
		if(curMs > opacityFinishMs) {
			opacityIncrementRate = 0;
			opacityLastTickMs = 0;
			opacityFinishMs = 0;
		} else {
			long toIncrement = curMs - opacityLastTickMs;
			//Update scale
			assignedActor.setOpacity(assignedActor.getOpacity()
					+(opacityIncrementRate
							*toIncrement));
			//Update last tick
			opacityLastTickMs = curMs;
		}
	}
	
	//Are the keyframes done?
	public boolean coordinatesDone() {
		return (coordIncrementRateX == 0
				&& coordIncrementRateY == 0);
	}
	public boolean rotationDone() {
		return rotationIncrementRate == 0;
	}
	public boolean scaleDone() {
		return scaleIncrementRate == 0;
	}
	public boolean opacityDone() {
		return opacityIncrementRate == 0;
	}
}
