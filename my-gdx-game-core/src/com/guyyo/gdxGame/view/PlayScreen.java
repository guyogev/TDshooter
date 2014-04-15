package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.control.FixedStage;
import com.guyyo.gdxGame.control.PlayScreenController;
import com.guyyo.gdxGame.model.Animation;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.BloodPool;
import com.guyyo.gdxGame.model.CowPool;
import com.guyyo.gdxGame.model.EnemyPool;
import com.guyyo.gdxGame.model.FireOrb;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.PowerUpsPool;
import com.guyyo.gdxGame.model.ShotPool;

/*
 * main game screen.
 */
public class PlayScreen extends MyScreen {

	SpriteBatch batch;
	Stage movingStage; // container for game actors
	FixedStage fixedStage; // container for game controls
	PlayScreenController playController; // game logic
	// MyGestureController myGestureController; // Handles Gestures inputs
	InputMultiplexer inputMultiplexer; // merge inputs from stages

	Hero hero;
	EnemyPool enemyPool;
	ShotPool shotPool;
	CowPool cowPool;
	PowerUpsPool powerUpsPool;
	BloodPool bloodPool;
	FireOrb fireOrb;
	Hud hud;

	public PlayScreen(MyGdxGame game) {
		this.game = game;
		batch = new SpriteBatch();
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		movingStage = new Stage();

		// hero
		hero = new Hero();
		movingStage.addActor(hero);
		// enemyPool
		enemyPool = new EnemyPool();
		for (Animation e : enemyPool.getPool())
			movingStage.addActor(e);
		// shots pool
		shotPool = new ShotPool();
		for (Animation s : shotPool.getPool())
			movingStage.addActor(s);
		// powerUps
		powerUpsPool = new PowerUpsPool();
		for (Animation p : powerUpsPool.getPool())
			movingStage.addActor(p);
		// blood
		bloodPool = new BloodPool();
		for (Animation b : bloodPool.getPool())
			movingStage.addActor(b);
		// FX
		fireOrb = new FireOrb();
		movingStage.addActor(fireOrb);

		// displayed controls
		fixedStage = new FixedStage(inputMultiplexer, movingStage.getCamera(),
				hero, enemyPool, shotPool, fireOrb);

		// displayed data
		hud = new Hud();
		fixedStage.addActor(hud);

		// playController
		playController = new PlayScreenController(game, hero, enemyPool,
				shotPool, cowPool, powerUpsPool, bloodPool, fireOrb, hud);
		inputMultiplexer.addProcessor(new GestureDetector(20, 0.5f, 2, 0.15f,
				playController));

		// GestureDetector
		// myGestureController = new
		// MyGestureController(movingStage.getCamera(),
		// hero, shotPool);

		// inputMultiplexer.addProcessor(new GestureDetector(20, 0.5f, 2, 0.15f,
		// myGestureController));
		
		// sound
		Assets.music.setLooping(true);
		Assets.music.setVolume(.5f);
		Assets.music.play();
	}

	@Override
	public void render(float delta) {
		// clear screen;
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		// update camera position & projection
		movingStage.getCamera().update();
		batch.setProjectionMatrix(movingStage.getCamera().combined);
		// draw background
		batch.begin();
		batch.draw(Assets.bg, 0, 0, Assets.PLAY_SCREEN_WIDTH,
				Assets.PLAY_SCREEN_HEIGTH);
		batch.end();
		// update game state
		playController.update();
		// draw actors
		movingStage.draw();
		// draw controllers
		fixedStage.processInput();
		fixedStage.draw();
	}

	@Override
	public void dispose() {
		fixedStage.dispose();
		movingStage.dispose();
		batch.dispose();
	}

}
