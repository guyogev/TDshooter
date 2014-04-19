package com.guyyo.gdxGame.model;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Shot extends Animation {
	private float dx, dy;

	public Shot() {
		animHash = new Hashtable<AnimDirection, Integer>();
		animHash.put(AnimDirection.W, 0);
		animHash.put(AnimDirection.NW, 1);
		animHash.put(AnimDirection.N, 2);
		animHash.put(AnimDirection.NE, 3);
		animHash.put(AnimDirection.E, 4);
		animHash.put(AnimDirection.SE, 5);
		animHash.put(AnimDirection.S, 6);
		animHash.put(AnimDirection.SW, 7);
		loadTexture(Assets.shot, 8, 8);
		initParams();
		speed = 4;
		animDirection = AnimDirection.N;
		state = STATE.SPAWN;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if (state == STATE.IN_USE)
			super.draw(getFrame(animHash.get(animDirection)), batch, alpha);
	}

	@Override
	public void animate() {
		if (state == STATE.IN_USE)
			if (getX() <= -getWidth() || getX() > Assets.PLAY_SCREEN_WIDTH - getWidth() || getY() <= -getY()
					|| getY() > Assets.PLAY_SCREEN_HEIGTH - getHeight())
				state = STATE.SPAWN;
			else {
				frameCol = (frameCol += 3*animDelta) % 8;
				setPosition(getX() + dx, getY() + dy);
			}
	}
	
	@Override
	public void spawn() {
		// TODO Auto-generated method stub	
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	
	public void die(){
		state = STATE.SPAWN;
	}
}
