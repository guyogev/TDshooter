package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class PowerUp extends Animation {

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame(0), batch, alpha);
	}

	@Override
	public void animate() {
		if (state == STATE.IN_USE) {
			frameCol = (frameCol + .1f)
					% (getRowLength((int) frameRow) - 1);
		}
	}

	public void spawn() {
		frameRow =  rand.nextInt(4);
		float x = rand.nextFloat();
		setPosition(x * Assets.PLAY_SCREEN_WIDTH, rand.nextFloat()
				* Assets.PLAY_SCREEN_HEIGTH);
		state = STATE.IN_USE;
	}
}
