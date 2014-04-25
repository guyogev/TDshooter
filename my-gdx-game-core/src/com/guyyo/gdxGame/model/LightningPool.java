package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.guyyo.gdxGame.model.Animation.STATE;

public class LightningPool extends AnimationsPool {

	public LightningPool() {
		size = 20;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Lightning());
	}

	public void spawn(float x, float y) {
		int i = 0;
		for (Animation l : pool) {
			System.out.println(i++);
			if (l.state == STATE.SPAWN) {
				((Lightning) l).spawn(x, y);
				break;
			}
		}
	}
}
