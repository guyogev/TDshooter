package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.guyyo.gdxGame.model.Animation.STATE;

public class BloodPool extends AnimationsPool {
	public BloodPool() {
		size = 10;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Blood(rand.nextInt(100)));
	}

	public void spawn(float x, float y) {
		int i = rand.nextInt(size);
		int j = 0;
		Blood b;
		while (j != size) {
			b = (Blood) pool.get(i % size);
			if (b.state == STATE.SPAWN) {
				b.setX(x);
				b.setY(y);
				b.state = STATE.IN_USE;
				break;
			}
			j++;
			i++;
		}
	}
}
