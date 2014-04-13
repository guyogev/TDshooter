package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.guyyo.gdxGame.model.Animation.STATE;


public class ShotPool extends AnimationsPool{

	public ShotPool() {
		size = 30;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Shot());
	}

	public void spawn(float x, float y, float dx, float dy, double deg) {
		for (Animation s : pool)
			if (s.state == STATE.SPAWN) {
				s.setX(x);
				s.setY(y);
				((Shot) s).setDx(dx);
				((Shot) s).setDy(dy);
				s.setRotation((float) 0);//TODO
				s.state = STATE.ALIVE;
				break;
			}
	}

}
