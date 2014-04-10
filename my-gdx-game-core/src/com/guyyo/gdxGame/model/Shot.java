package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Shot extends Animation {
	private float dx, dy, rotation;
	public static float speed = 15;

	public Shot() {
		loadTexture(Assets.shot, 1, 3);
		state = STATE.SPAWN;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if (state == STATE.ALIVE)
			batch.draw(frames[(int) frameIndex], getX(), getY(), getOriginX(),
					getOriginY(), frames[(int) frameIndex].getRegionWidth(),
					frames[(int) frameIndex].getRegionHeight(), 1, 1, rotation);
	}

	@Override
	public void animate() {
		if (state == STATE.ALIVE)
			if (getX() <= -getWidth() || getX() > Assets.PLAY_SCREEN_WIDTH - getWidth() || getY() <= -getY()
					|| getY() > Assets.PLAY_SCREEN_HEIGTH - getHeight())
				state = STATE.SPAWN;
			else {
				frameIndex = (frameIndex += 1) % 3;
				setPosition(getX() + dx, getY() + dy);
			}
	}

	public void setDx(float dx) {
		this.dx = dx;

	}

	public void setDy(float dy) {
		this.dy = dy;

	}

	public void setRotate(float rotation) {
		this.rotation = rotation;

	}
	
	public void kill(){
		state = STATE.SPAWN;
	}
}
