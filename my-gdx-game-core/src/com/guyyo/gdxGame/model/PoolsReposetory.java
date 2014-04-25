package com.guyyo.gdxGame.model;

public class PoolsReposetory {
	public static EnemyPool enemyPool;
	public static ShotPool shotPool;
	public static BloodPool bloodPool;
	public static SparksPool sparksPool;

	public static void init() {
		enemyPool = new EnemyPool();
		shotPool = new ShotPool();
		bloodPool = new BloodPool();
		sparksPool = new SparksPool();
		
	}

}
