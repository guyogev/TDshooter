package com.guyyo.gdxGame.model;

public class HpPowerUp extends PowerUp {
	
	public HpPowerUp() {
		loadTexture(Assets.HpPowerUp, 1, 8);
		initParams();
		state = STATE.SPAWN;
		spawn();
	}
}
