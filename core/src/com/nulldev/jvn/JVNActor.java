package com.nulldev.jvn;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Basic actor class, can specify position, rotation, scale and opacity
public class JVNActor {
	Keyframer keyframer;
	
	//Coordinates
	JVNCoordinate loc = new JVNCoordinate();
	//Rotation going clockwise
	float rotation = 0f;
	//Scale
	float scale = 1f;
	//Opacity 0-1
	float opacity = 1f;
	
	//Empty constructor
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
}
