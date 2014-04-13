package com.guyyo.gdxGame.model;

import java.util.ArrayList;

public class EnemyPool extends AnimationsPool{

	public EnemyPool() {
		size = 7;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Enemy(rand.nextFloat()));
		spawn();
	}
}
