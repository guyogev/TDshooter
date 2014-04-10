package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.guyyo.gdxGame.model.Animation.STATE;


public class ShotPool {
	int capacity = 30;
	ArrayList<Shot> pool;

	public ShotPool() {
		this.pool = new ArrayList<Shot>(capacity);
		for (int i = 0; i < capacity; i++)
			pool.add(new Shot());
	}

	public void spawn(float x, float y, float dx, float dy, double deg) {
		for (Shot s : pool)
			if (s.state == STATE.SPAWN) {
				s.setX(x);
				s.setY(y);
				s.setDx(dx);
				s.setDy(dy);
				s.setRotation((float) deg);
				s.state = STATE.ALIVE;
				Assets.shotSound.play();
				break;
			}
	}

	public ArrayList<Shot> getPool() {
		return pool;
	}

}
