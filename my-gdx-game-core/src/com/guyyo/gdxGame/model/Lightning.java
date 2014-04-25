package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Lightning extends Animation{
	
	public Lightning() {
		loadTexture(Assets.lightning, 5, 5);
		initParams();
		setScale(.5f);
		state = STATE.SPAWN;
	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame((int) frameRow), batch, alpha);
	}

	@Override
	//Animate only once.
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

	public void spawn(float x, float y) {
		setPosition(x, y);
		state = STATE.IN_USE;
		frameCol = 0;
		frameRow = 0;
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub

	}
}
