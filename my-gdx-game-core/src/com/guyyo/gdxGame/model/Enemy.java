package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Enemy extends Animation {
	private static float minSpeedDelta = .01f;

	int STAND_FRAME_INDEX = 0, WALK_FRAME_INDEX = 4, ATTACK_FRAME_INDEX = 12,
			DIE_FRAME_INDEX = 22, SCENE_SIZE = 4;

	enum AnimBehavior {
		WALK, ATTACK, STAND, DIE, REST
	}

	Hashtable<AnimDirection, Integer> animHash;
	AnimBehavior animBehavior;
	boolean behaviorLocked = false;
	float minSpeed;

	public void draw(Batch batch, float alpha) {
		if (state != STATE.SPAWN)
			super.draw(getFrame(animHash.get(animDirection)), batch, alpha);
	}

	public void animate() {
		if (state == STATE.IN_USE) {
			switch (animBehavior) {
			case STAND:
				sceneIndex = (sceneIndex + animDelta) % SCENE_SIZE;
				frameCol = STAND_FRAME_INDEX + sceneIndex;
				break;
			case REST:
				sceneIndex = sceneIndex + animDelta;
				frameCol = STAND_FRAME_INDEX + sceneIndex;
				if (sceneIndex >= SCENE_SIZE) {
					unLockBehavior();
					stand();
				}
				break;
			case WALK:
				sceneIndex = (sceneIndex + animDelta) % SCENE_SIZE;
				frameCol = WALK_FRAME_INDEX + sceneIndex;
				break;
			case ATTACK:
				sceneIndex = sceneIndex + animDelta;
				frameCol = ATTACK_FRAME_INDEX + sceneIndex;
				if (sceneIndex >= SCENE_SIZE)
					rest();
				break;
			case DIE:
				sceneIndex = sceneIndex + animDelta;
				frameCol = DIE_FRAME_INDEX + sceneIndex;
				if (sceneIndex >= SCENE_SIZE) {
					spawn();
					unLockBehavior();
				}
				break;

			default:
				break;
			}

		}
	}

	public void spawn() {
		float x = rand.nextFloat();
		setPosition(x * Assets.PLAY_SCREEN_WIDTH, rand.nextFloat()
				* Assets.PLAY_SCREEN_HEIGTH);
		speed = minSpeed + rand.nextFloat();
		state = STATE.IN_USE;
		minSpeed += minSpeedDelta;
		walk();
	}

	public void lockBehavior() {
		behaviorLocked = true;

	}

	public void unLockBehavior() {
		behaviorLocked = false;

	}

	private void stand() {
		animBehavior = AnimBehavior.STAND;
		sceneIndex = 0;
	}

	private void rest() {
		animBehavior = AnimBehavior.REST;
		sceneIndex = 0;
	}

	public void walk() {
		animBehavior = AnimBehavior.WALK;
		sceneIndex = 0;
	}

	public void attack() {
		animBehavior = AnimBehavior.ATTACK;
		sceneIndex = 0;
		lockBehavior();
	}

	public void die() {
		if (!isDying()) {
			frameCol = DIE_FRAME_INDEX;
			animBehavior = AnimBehavior.DIE;
			sceneIndex = 0;
			lockBehavior();
		}
	}

	public boolean isWalking() {
		return animBehavior == AnimBehavior.WALK;
	}

	public boolean isAttacking() {
		return animBehavior == AnimBehavior.ATTACK;
	}

	public boolean isDying() {
		return animBehavior == AnimBehavior.DIE;
	}

	public boolean isBehaviorLocked() {
		return behaviorLocked;
	}

}
