package com.nulldev.jvn.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawableActor extends JVNActor {
	
	Texture tex = null;
	
	//Blank constructor
	public DrawableActor() {}
	
	//Construct with texture
	public DrawableActor(Texture tex) {
		this.tex = tex;
	}
	
	//Render our texture
	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		Color c = batch.getColor();
		batch.setColor(c.r, c.g, c.b, this.getOpacity());
		batch.draw(new TextureRegion(tex),
				this.getCoordinates().getX(),
				this.getCoordinates().getY(),
				0,
				0,
				tex.getWidth(),
				tex.getHeight(),
				this.getScale(),
				this.getScale(),
				rotation);
		batch.end();
	}
	
	public Texture getTexture() {
		return tex;
	}

	public DrawableActor setTexture(Texture tex) {
		this.tex = tex;
		return this;
	}
}
