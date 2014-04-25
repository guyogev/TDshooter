package com.guyyo.gdxGame.model;


public class ManaPowerUp extends PowerUp {
	
	public ManaPowerUp() {
		loadTexture(Assets.ManaPowerUp, 1, 8);
		initParams();
		state = STATE.SPAWN;
		spawn();
	}
}
