package com.guyyo.gdxGame.model;

import java.util.ArrayList;

public class PowerUpsPool extends AnimationsPool {

	public PowerUpsPool() {
		size = 2;
		pool = new ArrayList<Animation>(size);
		pool.add(new HpPowerUp());
		pool.add(new ManaPowerUp());
	}
}
