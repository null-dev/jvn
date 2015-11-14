package xyz.nulldev.jvn.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawableActor extends JVNActor {
	
	Texture tex = null;
	int xorigin = 0;
	int yorigin = 0;
	
	public int getXorigin() {
		return xorigin;
	}

	public void setXorigin(int xorigin) {
		this.xorigin = xorigin;
	}

	public int getYorigin() {
		return yorigin;
	}

	public void setYorigin(int yorigin) {
		this.yorigin = yorigin;
	}

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
				xorigin,
				yorigin,
				tex.getWidth()*this.getScale(),
				tex.getHeight()*this.getScale(),
				1,
				1,
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
