package com.guyyo.gdxGame.model;

import java.util.ArrayList;
import java.util.Random;

public class EnemyPool extends AnimationsPool{

	public EnemyPool() {
		size = 5;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Enemy());
		rand = new Random();
		spawn();
	}
}
