package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.g2d.Batch;
/*
 * Blood animation to simulate injuries
 */
public class Blood extends Animation {

	public Blood(int AnimIndex) {
		//choose a blood Animation
		if (AnimIndex < 25)
			loadTexture(Assets.blood_1, 4, 4);
		else if (AnimIndex < 50)
			loadTexture(Assets.blood_3, 4, 4);
		else if (AnimIndex < 75)
			loadTexture(Assets.blood_4, 4, 4);
		else
			loadTexture(Assets.blood_5, 4, 4);
		initParams();
		state = STATE.SPAWN;
	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame((int) frameRow), batch, alpha);
	}

	@Override
	//Animate only once.
	public void animate() {
		if (state == STATE.ALIVE) {
			if (frameCol >= (getRowLength((int) frameRow) - 1)) {
				state = STATE.SPAWN;
				frameCol = 0;
			} else
				frameCol += .15f;
		}
	}

	public void spawn(float x, float y) {
		setPosition(x, y);
		state = STATE.ALIVE;
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub

	}

}
