package com.guyyo.gdxGame.model;

import java.util.ArrayList;

public class EnemyPool extends AnimationsPool {

	public EnemyPool() {
		size = 9;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size / 3; i++) {
			pool.add(new SkeletonOccultist());
			pool.add(new SkeletonMage());
			pool.add(new SkeletonKnight());
		}
		spawn();
	}
}
