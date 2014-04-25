package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Sparks extends Animation {

	public Sparks() {
		loadTexture(Assets.sparks, 6, 4);
		initParams();
		state = STATE.SPAWN;
	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame(2), batch, alpha);
	}

	@Override
	//Animate only once.
	public void animate() {
		if (state == STATE.IN_USE) {
			if (frameCol >= (getRowLength(2) - 1)) {
				state = STATE.SPAWN;
				frameCol = 0;
			} else{
				frameCol += .1f;
			}
		}
	}

	public void spawn(float x, float y) {
		setPosition(x, y);
		state = STATE.IN_USE;
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub

	}
}
