package xyz.nulldev.jvn.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Basic actor class, can specify position, rotation, scale and opacity
public abstract class JVNActor {
	Keyframer keyframer;
	
	//Coordinates
	JVNCoordinate loc = new JVNCoordinate();
	//Rotation going clockwise
	float rotation = 0f;
	//Scale
	float scale = 1f;
	//Opacity 0-1
	float opacity = 1f;
	//Z index
	int zindex = 0;
	
	//Set a keyframer
	public JVNActor setKeyframer(Keyframer keyframer) {
		this.keyframer = keyframer;
		this.keyframer.setActor(this);
		return this;
	}
	
	public Keyframer getKeyframer() {
		return this.keyframer;
	}
	
	//Tick
	public void tick() {
		//Tick the keyframer if there is one
		if(this.keyframer != null) {
			this.keyframer.tick();
		}
	}
	
	//Render the actor, JVNActor has nothing to render so this is not needed!
	abstract public void render(SpriteBatch batch);
	
	//Getters and setters
	public JVNCoordinate getCoordinates() {
		return this.loc;
	}
	
	public JVNActor setCoordinates(JVNCoordinate coord) {
		this.loc = coord;
		return this;
	}

	public float getRotation() {
		return rotation;
	}

	public JVNActor setRotation(float rotation) {
		this.rotation = rotation;
		return this;
	}

	public float getScale() {
		return scale;
	}

	public JVNActor setScale(float scale) {
		this.scale = scale;
		return this;
	}

	public float getOpacity() {
		return opacity;
	}

	public JVNActor setOpacity(float opacity) {
		this.opacity = opacity;
		return this;
	}
	
	public int getZIndex() {
		return this.zindex;
	}
	
	public JVNActor setZIndex(int zindex) {
		this.zindex = zindex;
		return this;
	}
}
