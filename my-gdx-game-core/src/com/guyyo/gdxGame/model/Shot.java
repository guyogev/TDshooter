package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Shot extends Animation {
	private float dx, dy;
	public static float speed = 15;

	public Shot() {
		loadTexture(Assets.shot, 1, 3);
		initParams();
		state = STATE.SPAWN;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if (state == STATE.ALIVE)
			super.draw(getFrame(0), batch, alpha);
	}

	@Override
	public void animate() {
		if (state == STATE.ALIVE)
			if (getX() <= -getWidth() || getX() > Assets.PLAY_SCREEN_WIDTH - getWidth() || getY() <= -getY()
					|| getY() > Assets.PLAY_SCREEN_HEIGTH - getHeight())
				state = STATE.SPAWN;
			else {
				frameCol = (frameCol += .5) % 3;
				setPosition(getX() + dx, getY() + dy);
			}
	}

	public void setDx(float dx) {
		this.dx = dx;

	}

	public void setDy(float dy) {
		this.dy = dy;

	}
	
	public void kill(){
		state = STATE.SPAWN;
	}
}
