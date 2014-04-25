package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.guyyo.gdxGame.model.Animation.STATE;

public class SparksPool extends AnimationsPool{
	
	public  SparksPool() {
		size = 10;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Sparks());
	}
	
	public void spawn(float x, float y) {
		int i = rand.nextInt(size);
		int j = 0;
		Sparks s;
		while (j != size) {
			s = (Sparks) pool.get(i % size);
			if (s.state == STATE.SPAWN) {
				s.setX(x);
				s.setY(y);
				s.state = STATE.IN_USE;
				break;
			}
			j++;
			i++;
		}
	}

}
