package com.guyyo.gdxGame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public final class Assets {
	// play screen size
	public static float PLAY_SCREEN_WIDTH;
	public static float PLAY_SCREEN_HEIGTH;
	// moving camera boundaries
	public static float MOVING_CAM_MIN_X, MOVING_CAM_MAX_X, MOVING_CAM_MIN_Y,
			MOVING_CAM_MAX_Y;
	// Textures
	public static Texture bg, badlogic, heroRun, hero2, enemy_sheet, shot,
			heroPistol, heroPistolReload, cow, powerUps;
	// Skins
	public static Skin defultSkin;
	public static TouchpadStyle touchpadStyle;
	// sound
	public static Sound shotSound, pistolEmpty, reload;
	public static Music music;

	public static void load() {

		PLAY_SCREEN_WIDTH = Gdx.graphics.getWidth() * 3;
		PLAY_SCREEN_HEIGTH = Gdx.graphics.getHeight() * 3;

		MOVING_CAM_MIN_X = Gdx.graphics.getWidth() / 2;
		MOVING_CAM_MAX_X = PLAY_SCREEN_WIDTH - Gdx.graphics.getWidth()/2;
		MOVING_CAM_MIN_Y = Gdx.graphics.getHeight() / 2;
		MOVING_CAM_MAX_Y = PLAY_SCREEN_HEIGTH - Gdx.graphics.getHeight()/2;

		bg = new Texture(Gdx.files.internal("bg1.png"));
		badlogic = new Texture(Gdx.files.internal("badlogic.jpg"));
		heroRun = new Texture(Gdx.files.internal("hero run.png"));
		heroPistol = new Texture(Gdx.files.internal("hero pistol.png"));
		heroPistolReload = new Texture(Gdx.files.internal("hero pistol reload.png"));
		enemy_sheet = new Texture(Gdx.files.internal("enemy.png"));
		shot = new Texture(Gdx.files.internal("shot2.png"));
		hero2 = new Texture(Gdx.files.internal("hero2.png"));
		powerUps = new Texture(Gdx.files.internal("powerups.png"));
		cow = new Texture(Gdx.files.internal("cowsheet.png"));

		shotSound = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
		pistolEmpty = Gdx.audio.newSound(Gdx.files.internal("sound/pistol empty.wav"));
		reload = Gdx.audio.newSound(Gdx.files.internal("sound/reload.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/loop.wav"));
		
		defultSkin = new Skin(Gdx.files.internal("uiskin.json"));
		Skin touchpadSkin = new Skin();
        
        touchpadSkin.add("touchBackground", new Texture(Gdx.files.internal("gui/touchpad bg.png")));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture(Gdx.files.internal("gui/touchpad knob.png")));
        //Create TouchPad Style
        touchpadStyle = new TouchpadStyle();
        //Create Drawable's from TouchPad skin
        Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
        Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style


	}
}
