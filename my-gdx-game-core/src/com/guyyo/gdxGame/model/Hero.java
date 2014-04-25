package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hero extends Animation {

	enum AnimBehavior {
		RUN, ATTACK, ATTACK2, STAND, CAST, DIE
	}

	// stand[0,3] run[4,11] attack[12,15], die[16,23] cast[24,31]
	int STAND_FRAME_INDEX = 0, RUN_FRAME_INDEX = 4, ATTACK_FRAME_INDEX = 12,
			ATTACK2_FRAME_INDEX = 16, DIE_FRAME_INDEX = 18,
			CAST_FRAME_INDEX = 24, SCENE_SIZE = 4;

	AnimBehavior animBehavior;
	boolean behaviorLocked;

	float offsetX, offsetY, hp, mana;
	int shotsLeft, reloadProgress, powerUpLeft;

	public boolean hardAttack = false;

	public Hero() {
		animHash = new Hashtable<AnimDirection, Integer>();
		animHash.put(AnimDirection.W, 0);
		animHash.put(AnimDirection.NW, 1);
		animHash.put(AnimDirection.N, 2);
		animHash.put(AnimDirection.NE, 3);
		animHash.put(AnimDirection.E, 4);
		animHash.put(AnimDirection.SE, 5);
		animHash.put(AnimDirection.S, 6);
		animHash.put(AnimDirection.SW, 7);
		loadTexture(Assets.heroRun, 8, 32);
		initParams();

		animDirection = AnimDirection.N;
		animBehavior = AnimBehavior.STAND;
		behaviorLocked = false;
		setX(Gdx.graphics.getWidth() / 2);
		setY(Gdx.graphics.getHeight() / 2);
		speed = 5;
		shotsLeft = 10;
		hp = mana = 100;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		TextureRegion t = getFrame(animHash.get(animDirection));
		super.draw(t, batch, alpha);
	}

	@Override
	public void animate() {
		switch (animBehavior) {
		case STAND:
			frameCol = STAND_FRAME_INDEX + (sceneIndex += animDelta)
					% SCENE_SIZE;
			break;
		case RUN:
			frameCol = RUN_FRAME_INDEX + (sceneIndex += animDelta)
					% (2 * SCENE_SIZE);
			break;
		case ATTACK:
			frameCol = ATTACK_FRAME_INDEX + (sceneIndex += 1.5 * animDelta);
			if (sceneIndex >= SCENE_SIZE) {
				unlockBehavior();
				stand();
			}
			break;
		case ATTACK2:
			frameCol = ATTACK2_FRAME_INDEX + (sceneIndex += animDelta);
			if (sceneIndex >= .5 * SCENE_SIZE) {
				unlockBehavior();
				stand();
			}
			break;
		case DIE:
			System.out.println(sceneIndex);
			frameCol = DIE_FRAME_INDEX + (sceneIndex += 0.1);
			if (sceneIndex >= 1.5 * SCENE_SIZE) {
				super.kill();
			}
			break;
		case CAST:
			frameCol = CAST_FRAME_INDEX + (sceneIndex += animDelta);
			if (sceneIndex >= 2 * SCENE_SIZE - 1) {
				unlockBehavior();
				stand();
			}
			break;
		}
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub
	}

	/* *********** Setters *********** */
	public void setRotation(double d) {
		setRotation(90 + (float) d);
	}

	public void updateOffsetX(float x) {
		offsetX += x;
	}

	public void updateOffsetY(float y) {
		offsetY += y;
	}

	public void incPowerUp() {
		if (powerUpLeft < 1)
			powerUpLeft++;
	}

	public void decPowerUps() {
		powerUpLeft--;

	}

	public boolean decreaseHp() {
		hp -= 1;
		if (hp <= 0)
			return true;
		else
			return false;
	}

	/* *********** Getters *********** */
	public float getSpeed() {
		return speed;
	}

	public float getRelX() {
		return getX() - offsetX;
	}

	public float getRelY() {
		return getY() - offsetY;
	}

	public int getShotsLeft() {
		return shotsLeft;
	}

	public float getHp() {
		return hp;
	}

	/* *********** Animation State *********** */
	public void unlockBehavior() {
		behaviorLocked = false;
	}

	public void lockBehavior() {
		behaviorLocked = true;
	}

	public void run() {
		if (!behaviorLocked) {
			animBehavior = AnimBehavior.RUN;
			sceneIndex = 0;
		}

	}

	public void attack() {
		if (!behaviorLocked) {
			animBehavior = AnimBehavior.ATTACK;
			lockBehavior();
			sceneIndex = 0;
		}
	}

	public void attack2() {
		if (!behaviorLocked) {
			animBehavior = AnimBehavior.ATTACK2;
			lockBehavior();
			sceneIndex = 0;
		}
	}

	public void stand() {
		if (!behaviorLocked) {
			animBehavior = AnimBehavior.STAND;
			sceneIndex = 0;
		}
	}

	public void die() {
		animBehavior = AnimBehavior.DIE;
		sceneIndex = 0;
		lockBehavior();
	}

	public void castSpell() {
		animBehavior = AnimBehavior.CAST;
		{
			lockBehavior();
			sceneIndex = 0;
		}
	}

	public boolean isRunning() {
		return animBehavior == AnimBehavior.RUN;
	}

	public boolean isStanding() {
		return animBehavior == AnimBehavior.STAND;
	}

	public boolean isAttacking_1() {
		return animBehavior == AnimBehavior.ATTACK;
	}

	public boolean isCasting() {
		return animBehavior == AnimBehavior.CAST;
	}

	public boolean idDying() {
		return animBehavior == AnimBehavior.DIE;
	}

	public boolean isBehaviorLocked() {
		return behaviorLocked;
	}

	public boolean hasPowerUps() {
		return powerUpLeft > 0;
	}

	public void incHp(int inc) {
		hp = Math.min(hp + inc, 100);
	}

	public void incMana(int inc) {
		mana = Math.min(mana+inc, 100);
	}

	public float getMana() {
		return mana;
	}

	public void decMana(int dec) {
		mana = Math.max(0, mana-dec);
	}
}
