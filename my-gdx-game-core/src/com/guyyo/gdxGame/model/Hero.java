package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hero extends Animation {
	enum AnimState {
		RUNNING, SHOOTING, RELOADING
	}

	AnimState animState;
	float offsetX, offsetY, hp;
	int shotsLeft;

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
		 hp -= .2;
		if (hp <= 0)
			kill();
	}

	/* *********** Getters *********** */
	public float getSpeed() {
		return speed;
	}

	public float getRelX() {
		System.out.println("x " + (getX() - offsetX));
		return getX() - offsetX;
	}

	public float getRelY() {
		System.out.println("y " + (getY() - offsetY));
		return getY() - offsetY;
	}

	public float getCenterX() {
		return getX() - getOriginX();
	}

	public float getCenterY() {
		return getY() - getOriginY();
	}

	public int getShotsLeft() {
		return shotsLeft;
	}

	public float getHp() {
		return hp;
	}

	/* ***********	Animation State	***********	*/
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
		shotsLeft = 10;
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
