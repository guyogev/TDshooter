package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.guyyo.gdxGame.model.Animation.AnimDirection;
import com.guyyo.gdxGame.model.Animation.STATE;

public class ShotPool extends AnimationsPool {

	public ShotPool() {
		size = 30;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Shot());
	}

	public void spawn(float x, float y, AnimDirection dirc) {
		for (Animation s : pool)
			if (s.state == STATE.SPAWN || s.state == STATE.FREE) {
				s.setX(x);
				s.setY(y);
				s.setDirection(dirc);
				float dy, dx, speed = s.getSpeed();
				switch (dirc) {
				case N:
					dx = 0;
					dy = speed;
					break;
				case NE:
					dx = .5f * speed;
					dy = .5f * speed;
					break;
				case E:
					dx = speed;
					dy = 0;
					break;
				case SE:
					dx = .5f * speed;
					dy = -.5f * speed;
					break;
				case S:
					dx = 0;
					dy = -speed;
					break;
				case SW:
					dx = -.5f * speed;
					dy = -.5f * speed;
					break;
				case W:
					dx = -speed;
					dy = 0;
					break;
				default:// NW
					dx = -.5f * speed;
					dy = .5f * speed;
					break;
				}
				System.out.println(""+dx+" "+dy);
				((Shot) s).setDx(dx);
				((Shot) s).setDy(dy);
				s.state = STATE.IN_USE;
				break;
			}
	}

}
