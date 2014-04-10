package com.guyyo.gdxGame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Hero extends Animation {
	float rotation, frameIndex, speed, offsetX, offsetY;
	int shotsLeft, hp;

	public Hero() {
		loadTexture(Assets.hero_sheet, 4, 2);
		setX(Gdx.graphics.getWidth() / 2);
		setY(Gdx.graphics.getHeight() / 2);
		speed = 6;
		shotsLeft = 10;
		hp = 100;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(frames[(int) frameIndex],
				getX() - frames[(int) frameIndex].getRegionWidth() / 2, getY()
						- frames[(int) frameIndex].getRegionHeight() / 2,
				getOriginX(), getOriginY(),
				frames[(int) frameIndex].getRegionWidth(),
				frames[(int) frameIndex].getRegionHeight(), 1, 1, rotation);
	}

	@Override
	public void animate() {
		frameIndex = (frameIndex += .1) % 5;
	}

	public void setRotation(double d) {
		this.rotation = (float) d;
	}

	public float getSpeed() {
		return speed;
	}

	public void stand() {
		frameIndex = 0;
	}

	public void fire() {
		frameIndex = 6;
		shotsLeft--;
	}
	
	public void reload(){
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

	public int getHp() {
		return hp;
	}

	public void decreaseHp() {
		--hp;
		if (hp <=0)
			kill();
	}
}
