package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class FireOrb extends Animation {

	public FireOrb() {
		loadTexture(Assets.fireOrb, 8, 8);
		initParams();
		setScale(2f);
		state = STATE.SPAWN;
	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame((int) frameRow), batch, alpha);
	}

	@Override
	public void animate() {
		if (state == STATE.IN_USE) {
			if (frameCol >= (getRowLength((int) frameRow) - 1)) {
				if (frameRow >= (getRowLength((int) frameRow) - 1)) {
					state = STATE.SPAWN;
				} else {
					frameRow++;
					frameCol = 0;
				}
			} else
				frameCol += .2f;
		}
	}

	@Override
	public void spawn() {
		state = STATE.IN_USE;
		frameCol = 0;
		frameRow = 0;

	}

}
