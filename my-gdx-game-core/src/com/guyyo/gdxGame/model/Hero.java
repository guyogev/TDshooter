package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hero extends Animation {
	public enum AnimState {
		RUNNING, SHOOTING, RELOADING
	}

	public AnimState animState;
	float offsetX, offsetY, hp;
	int shotsLeft, reloadProgress;

	public Hero() {
		animHash = new Hashtable<AnimState, Integer>();
		animHash.put(AnimState.RUNNING, 0);
		animHash.put(AnimState.SHOOTING, 1);
		animHash.put(AnimState.RELOADING, 2);
		loadTexture(Assets.heroRun, 1, 6);
		loadTexture(Assets.heroPistol, 1, 1);
		loadTexture(Assets.heroPistolReload, 1, 1);
		initParams();

		animState = AnimState.RUNNING;
		setX(Gdx.graphics.getWidth() / 2);
		setY(Gdx.graphics.getHeight() / 2);
		setScale(1.5f);
		speed = 6;
		shotsLeft = 10;
		hp = 100;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		TextureRegion t = getFrame(animHash.get(animState));
		super.draw(t, batch, alpha);
	}

	@Override
	public void animate() {
		if (animState == AnimState.RUNNING)
			frameCol = (frameCol += .1)
					% (getRowLength(animHash.get(animState) - 1));
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

	public void decreaseHp() {
		// hp -= .2;
		if (hp <= 0)
			kill();
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
	public void run() {
		frameCol = 0;
		animState = AnimState.RUNNING;
	}

	public void fire() {
		animState = AnimState.SHOOTING;
		frameCol = 0;
		shotsLeft--;
	}

	public void reload() {
		reloadProgress = 0;
		animState = AnimState.RELOADING;
	}

	public void checkDoneReloading() {
		if (reloadProgress >= 100) {
			shotsLeft = 10;
			animState = AnimState.SHOOTING;
		}
		else
			reloadProgress++;
	}

	public void stand() {
		animState = AnimState.SHOOTING;
		frameCol = 0;
	}

	public boolean canFire() {
		return shotsLeft > 0;
	}

	public boolean isRunning() {
		return animState == AnimState.RUNNING;
	}

	public boolean isShooting() {
		return animState == AnimState.SHOOTING;
	}

	public boolean isReloading() {
		return animState == AnimState.RELOADING;
	}

}
