package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class PowerUp extends Animation {

	public PowerUp() {
		loadTexture(Assets.powerUps, 4, 4);
		initParams();
		state = STATE.SPAWN;
	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame((int) frameRow), batch, alpha);
	}

	@Override
	public void animate() {
		if (state == STATE.ALIVE) {
			frameCol = (frameCol + .1f)
					% (getRowLength((int) frameRow) - 1);
		}
	}

	public void spawn() {
		frameRow =  rand.nextInt(4);
		float x = rand.nextFloat();
		setPosition(x * Assets.PLAY_SCREEN_WIDTH, rand.nextFloat()
				* Assets.PLAY_SCREEN_HEIGTH);
		state = STATE.ALIVE;
	}
}
