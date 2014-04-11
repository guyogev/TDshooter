package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Enemy extends Animation {
	private static float minSpeed = 2;
	private static float minSpeedDelta = .01f;

	enum AnimState {
		UP, DOWN, LEFT, RIGHT
	}

	Hashtable<AnimState, Integer> animHash;
	AnimState animState;

	public Enemy() {
		animHash = new Hashtable<Enemy.AnimState, Integer>();
		animHash.put(AnimState.DOWN, 0);
		animHash.put(AnimState.UP, 1);
		animHash.put(AnimState.LEFT, 2);
		animHash.put(AnimState.RIGHT, 3);
		loadTexture(Assets.enemy_sheet, 4, 11);
		initParams();
		state = STATE.SPAWN;
		speed = 3;
		animState = AnimState.DOWN;
	}

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame(animHash.get(animState)), batch, alpha);
	}

	public void animate() {
		if (state == STATE.ALIVE)
			frameCol = (frameCol += .1) % 6; // TODO after frame 6 is dead
												// animation
		else if (state == STATE.DEAD) {
			frameCol += .3;
			if (frameCol >= getRowLength(animHash.get(animState)) - 1)
				state = STATE.SPAWN;
		}
	}

	public void spawn() {
		float x = rand.nextFloat();
		setPosition(x * Assets.PLAY_SCREEN_WIDTH, rand.nextFloat()
				* Assets.PLAY_SCREEN_HEIGTH);
		speed = minSpeed + rand.nextInt(3);
		state = STATE.ALIVE;
		minSpeed += minSpeedDelta;
	}

	public void faceRight() {
		animState = AnimState.RIGHT;
	}

	public void faceLeft() {
		animState = AnimState.LEFT;
	}

	public void faceUp() {
		animState = AnimState.UP;
	}

	public void faceDown() {
		animState = AnimState.DOWN;
	}
}
