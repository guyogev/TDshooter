package com.guyyo.gdxGame.model;

import java.util.ArrayList;
import java.util.Random;

import com.guyyo.gdxGame.model.Animation.STATE;


public class EnemyPool {
	int size = 5;
	ArrayList<Enemy> pool;
	Random rand;

	public EnemyPool() {
		pool = new ArrayList<Enemy>(size);
		for (int i = 0; i < size; i++)
			pool.add(new Enemy());
		rand = new Random();
		spawn();

	}

	public ArrayList<Enemy> getPool() {
		return pool;
	}

	public void spawn() {
		for (Enemy e : pool) {
			if (e.state == STATE.SPAWN) 
				e.spawn();
		}
	}

}
