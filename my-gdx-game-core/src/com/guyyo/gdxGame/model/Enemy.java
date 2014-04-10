package com.guyyo.gdxGame.model;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Enemy extends Animation {
	Random rand = new Random();
	
	public Enemy() {
		loadTexture(Assets.enemy_sheet, 4, 11);
		state = STATE.SPAWN;
		speed = 2 + rand.nextInt(3);

	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			batch.draw(frames[(int) frameIndex], getX(), getY());
	}

	public void animate() {
		if (state == STATE.ALIVE)
			frameIndex = (frameIndex += .1) % 6;
		else if (state == STATE.DEAD) {
			frameIndex += .3;
			if (frameIndex > 11)
				state = STATE.SPAWN;
		}
	}
	

	public void spawn() {
		float x = rand.nextFloat();
		setPosition(x * Assets.PLAY_SCREEN_WIDTH, rand.nextFloat()
				* Assets.PLAY_SCREEN_HEIGTH);
		state = STATE.ALIVE;
	}
}
