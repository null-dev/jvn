package com.nulldev.jvn;

/*
 * Basic coordinate class to make my life easier
 */

public class JVNCoordinate {
	private float x;
	private float y;

	public JVNCoordinate() {
		this.x = 0;
		this.y = 0;
	}

	public JVNCoordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public JVNCoordinate setX(float x) {
		this.x = x;
		return this;
	}

	public JVNCoordinate setY(float y) {
		this.y = y;
		return this;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

	@Override
	public JVNCoordinate clone() {
		return new JVNCoordinate(this.x, this.y);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof JVNCoordinate) {
			JVNCoordinate casted = (JVNCoordinate) obj;
			if(this.getX() == casted.getX()
					&& this.getY() == casted.getY()) {
				return true;
			}
		}
		return false;
	}
}
