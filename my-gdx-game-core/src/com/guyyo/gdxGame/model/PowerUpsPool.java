package com.guyyo.gdxGame.model;

import java.util.ArrayList;
import java.util.Random;

public class PowerUpsPool extends AnimationsPool {

	public PowerUpsPool() {
		size = 2;
		pool = new ArrayList<Animation>(size);
		for (int i = 0; i < size; i++)
			pool.add(new PowerUp());
		rand = new Random();
		spawn();
	}
}
