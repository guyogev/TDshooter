package com.guyyo.gdxGame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/*
 * Game assets repository 
 */
public final class Assets {
	// play screen size
	public static float PLAY_SCREEN_WIDTH;
	public static float PLAY_SCREEN_HEIGTH;
	// moving camera boundaries
	public static float MOVING_CAM_MIN_X, MOVING_CAM_MAX_X, MOVING_CAM_MIN_Y,
			MOVING_CAM_MAX_Y;

	// Textures
	public static Texture bg, badlogic, heroRun, hero2, enemy, shot,
			heroPistol, heroPistolReload, cow, powerUps, enemy2, blood_1,
			blood_3, blood_4, blood_5, fireOrb, skeletonOccultist,
			skeletonMage, skeletonKnight;

	// drawables
	public static Drawable fireButtonUp, fireButtonDown;

	// Skins
	public static Skin defultSkin;
	public static TouchpadStyle touchpadStyle;

	// sound
	public static Sound shotSound, pistolEmpty, reload, bones, fireFX, axe;
	public static Music music;

	// tile maps
	public static TiledMap tileMap;

	// load assets to memory
	public static void load() {
		tileMap = new TmxMapLoader().load("maps/tilemap.tmx");
		MapProperties prop = tileMap.getProperties();

		// parameters
		PLAY_SCREEN_WIDTH = prop.get("width", Integer.class)
				* prop.get("tilewidth", Integer.class)/2;
		PLAY_SCREEN_HEIGTH = prop.get("height", Integer.class)
				* prop.get("tileheight", Integer.class)/2;

		MOVING_CAM_MIN_X = Gdx.graphics.getWidth() / 2;
		MOVING_CAM_MAX_X = PLAY_SCREEN_WIDTH - Gdx.graphics.getWidth() / 2;
		MOVING_CAM_MIN_Y = Gdx.graphics.getHeight() / 2;
		MOVING_CAM_MAX_Y = PLAY_SCREEN_HEIGTH - Gdx.graphics.getHeight() / 2;

		// images
		bg = new Texture(Gdx.files.internal("bg1.png"));
		badlogic = new Texture(Gdx.files.internal("badlogic.jpg"));
		heroRun = new Texture(Gdx.files.internal("hero/orc_elite.png"));
		heroPistol = new Texture(Gdx.files.internal("hero pistol.png"));
		heroPistolReload = new Texture(
				Gdx.files.internal("hero pistol reload.png"));
		enemy = new Texture(Gdx.files.internal("enemy.png"));
		enemy2 = new Texture(Gdx.files.internal("player.png"));
		shot = new Texture(Gdx.files.internal("fireball.png"));
		hero2 = new Texture(Gdx.files.internal("hero2.png"));
		powerUps = new Texture(Gdx.files.internal("powerups.png"));
		cow = new Texture(Gdx.files.internal("cowsheet.png"));
		blood_1 = new Texture(Gdx.files.internal("blood_hit_01.png"));
		// blood_2 = new Texture(Gdx.files.internal("blood_hit_02.png"));
		blood_3 = new Texture(Gdx.files.internal("blood_hit_03.png"));
		blood_4 = new Texture(Gdx.files.internal("blood_hit_04.png"));
		blood_5 = new Texture(Gdx.files.internal("blood_hit_05.png"));
		fireOrb = new Texture(Gdx.files.internal("fire orb.png"));
		skeletonOccultist = new Texture(
				Gdx.files.internal("enemy/skeleton_occultist.png"));
		skeletonMage = new Texture(
				Gdx.files.internal("enemy/skeleton_mage.png"));
		skeletonKnight = new Texture(
				Gdx.files.internal("enemy/skeleton_knight.png"));
		// sound
		shotSound = Gdx.audio.newSound(Gdx.files.internal("sound/foom_0.wav"));
		fireFX = Gdx.audio.newSound(Gdx.files
				.internal("sound/fire_crackling 2.mp3"));
		pistolEmpty = Gdx.audio.newSound(Gdx.files
				.internal("sound/pistol empty.wav"));
		reload = Gdx.audio.newSound(Gdx.files.internal("sound/reload.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/loop.wav"));
		bones = Gdx.audio.newSound(Gdx.files.internal("sound/bones.wav"));
		axe = Gdx.audio.newSound(Gdx.files.internal("sound/axe.wav"));

		// gui
		defultSkin = new Skin(Gdx.files.internal("uiskin.json"));
		Skin touchpadSkin = new Skin();

		touchpadSkin.add("touchBackground",
				new Texture(Gdx.files.internal("gui/touchpad bg 2.png")));
		touchpadSkin.add("touchKnob",
				new Texture(Gdx.files.internal("gui/touchpad knob 2.png")));
		touchpadStyle = new TouchpadStyle();
		Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
		Drawable touchKnob = touchpadSkin.getDrawable("touchKnob");
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;

		fireButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(
				Gdx.files.internal("gui/fire button on.png"))));
		fireButtonDown = new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("gui/fire button off.png"))));

	}
}
