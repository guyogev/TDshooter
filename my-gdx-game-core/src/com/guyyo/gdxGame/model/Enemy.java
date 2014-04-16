package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Enemy extends Animation {
	private static float minSpeedDelta = .01f;
	private int WALK_FRAME_INDEX = 4, ATTACK_FRAME_INDEX = 12,
			DIE_FRAME_INDEX = 22, SCENE_SIZE = 4;

	enum AnimDirection {
		N, NE, NW, S, SE, SW, W, E
	}

	enum AnimBehavior {
		WALK, ATTACK, STAND
	}

	Hashtable<AnimDirection, Integer> animHash;
	AnimDirection animDirection;
	AnimBehavior animBehavior;
	float minSpeed;

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame(animHash.get(animDirection)), batch, alpha);
	}

	public void animate() {
		if (state == STATE.ALIVE) {
			if (animBehavior == AnimBehavior.WALK)
				frameCol = WALK_FRAME_INDEX + (frameCol + .1f) % SCENE_SIZE;
			else if (animBehavior == AnimBehavior.ATTACK)
				frameCol = ATTACK_FRAME_INDEX + (frameCol + .1f) % SCENE_SIZE;
		} else if (state == STATE.DEAD) {
			frameCol += .1;
			if (frameCol >= getRowLength(animHash.get(animDirection)) - 1)
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

	@Override
	public void kill() {
		frameCol = DIE_FRAME_INDEX;
		state = STATE.DEAD;
		//.Assets.bones.play(.5f);
	}

	public void walk() {
		animBehavior = AnimBehavior.WALK;
	}

	public void attack() {
		animBehavior = AnimBehavior.ATTACK;
	}

	public void faceNorth() {
		animDirection = AnimDirection.N;
	}

	public void faceSouth() {
		animDirection = AnimDirection.S;
	}

	public void faceWest() {
		animDirection = AnimDirection.W;
	}

	public void faceEast() {
		animDirection = AnimDirection.E;
	}

	public void faceNorthEast() {
		animDirection = AnimDirection.NE;
	}

	public void faceNorthWest() {
		animDirection = AnimDirection.NW;
	}

	public void faceSouthWest() {
		animDirection = AnimDirection.SW;
	}

	public void faceSouthEast() {
		animDirection = AnimDirection.SE;
	}

	public boolean isWalking() {
		return animBehavior == AnimBehavior.WALK;
	}
}
