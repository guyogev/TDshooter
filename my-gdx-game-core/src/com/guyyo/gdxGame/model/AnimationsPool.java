package com.guyyo.gdxGame.model;

import java.util.ArrayList;
import java.util.Random;

import com.guyyo.gdxGame.model.Animation.STATE;
/*
 * Pools Animations into groups.
 * Animation are created only once reused as needed.
 */
public abstract class AnimationsPool {
	int size;
	ArrayList<Animation> pool;
	static Random rand = new Random();
	
	public void spawn() {
		for (Animation a : pool) {
			if (a.state == STATE.SPAWN)
				a.spawn();
		}
	}
	
	public ArrayList<Animation> getPool() {
		return pool;
	}
}
