package com.guyyo.gdxGame.model;

public class SingaltonsRepository {
	public static Hero hero;
	public static FireOrb fireOrb;
	public static ManaPowerUp mana;
	public static HpPowerUp hp;
	public static Hud hud;

	public static void init() {
		hero = new Hero();
		fireOrb = new FireOrb();
		mana = new ManaPowerUp();
		hp = new HpPowerUp();
		hud = new Hud();
	}
}
