package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Hero extends Animation {
	public enum AnimState {
		RUN, SHOOT
	}

	Hashtable<AnimState, Integer> animHash;

	public AnimState animState;
	float speed, offsetX, offsetY, hp;
	int shotsLeft;

	public Hero() {
		animHash = new Hashtable<AnimState, Integer>();
		animHash.put(AnimState.RUN, 0);
		animHash.put(AnimState.SHOOT, 1);
		loadTexture(Assets.heroRun, 1, 6);
		loadTexture(Assets.heroPistol, 1, 1);
		initParams();

		animState = AnimState.RUN;
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
		if (animState == AnimState.RUN)
			frameCol = (frameCol += .1) % (getRowLength(animHash.get(animState)-1));
	}

	public void setRotation(double d) {
		setRotation(90 +(float) d);
	}

	public float getSpeed() {
		return speed;
	}

	public void stand() {
		frameCol = 0;
		animState = AnimState.RUN;

	}

	public void fire() {
		animState = AnimState.SHOOT;
		frameCol = 0;
		shotsLeft--;
	}

	public void reload() {
		shotsLeft = 10;
	}

	public float getRelX() {
		return getX() - offsetX;
	}

	public float getRelY() {
		return getY() - offsetY;
	}

	public void setOffsetX(float x) {
		offsetX += x;
	}

	public void setOffsetY(float y) {
		offsetY += y;
	}

	public float getCenterX() {
		return getX() - getOriginX();
	}

	public float getCenterY() {
		return getY() - getOriginY();
	}

	public boolean canFire() {
		return shotsLeft > 0;
	}

	public int getShotsLeft() {
		return shotsLeft;
	}

	public float getHp() {
		return hp;
	}

	public void decreaseHp() {
		hp-=.2;
		if (hp <= 0)
			kill();
	}

	public boolean isShooting() {
		return animState == AnimState.SHOOT;
	}
}
