package com.guyyo.gdxGame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Assets {
	// play screen size
	public static float PLAY_SCREEN_WIDTH;
	public static float PLAY_SCREEN_HEIGTH;
	// moving camera boundaries
	public static float MOVING_CAM_MIN_X;
	public static float MOVING_CAM_MAX_X;
	public static float MOVING_CAM_MIN_Y;
	public static float MOVING_CAM_MAX_Y;
	// Textures
	public static Texture bg;
	public static Texture badlogic;
	public static Texture heroRun;
	public static Texture hero2;
	public static Texture enemy_sheet;
	public static Texture shot;
	public static Texture heroPistol;
	// Skins
	public static Skin defultSkin;
	//sound
	public static Sound shotSound;
	public static Music music;

	public static void load() {

		PLAY_SCREEN_WIDTH = Gdx.graphics.getWidth() * 3;
		PLAY_SCREEN_HEIGTH = Gdx.graphics.getHeight() * 3;

		MOVING_CAM_MIN_X = Gdx.graphics.getWidth() / 2;
		MOVING_CAM_MAX_X = PLAY_SCREEN_WIDTH - Gdx.graphics.getWidth();
		MOVING_CAM_MIN_Y = Gdx.graphics.getHeight() / 2;
		MOVING_CAM_MAX_Y = PLAY_SCREEN_HEIGTH - Gdx.graphics.getHeight();

		bg = new Texture(Gdx.files.internal("bg1.png"));
		badlogic = new Texture(Gdx.files.internal("badlogic.jpg"));
		heroRun = new Texture(Gdx.files.internal("heroRun.png"));
		heroPistol = new Texture(Gdx.files.internal("heroPistol.png"));
		enemy_sheet = new Texture(Gdx.files.internal("enemy.png"));
		shot = new Texture(Gdx.files.internal("shot2.png"));
		hero2 = new Texture(Gdx.files.internal("hero2.png"));

		defultSkin = new Skin(Gdx.files.internal("uiskin.json"));

		shotSound = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("loop.wav"));

	}
}
