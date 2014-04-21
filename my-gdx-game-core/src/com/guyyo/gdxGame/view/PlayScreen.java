package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.control.FixedStage;
import com.guyyo.gdxGame.control.PlayScreenController;
import com.guyyo.gdxGame.model.Animation;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.FireOrb;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.PoolsReposetory;

/*
 * main game screen.
 */
public class PlayScreen extends MyScreen {

	SpriteBatch batch;
	IsometricStaggeredTiledMapRenderer tileMapRenderer;

	Stage movingStage; // container for game actors
	FixedStage fixedStage; // container for game controls
	PlayScreenController playController; // game logic
	// MyGestureController myGestureController; // Handles Gestures inputs
	InputMultiplexer inputMultiplexer; // merge inputs from stages

	Hero hero;
	FireOrb fireOrb;
	Hud hud;

	public PlayScreen(MyGdxGame game) {
		this.game = game;
		batch = new SpriteBatch();
		inputMultiplexer = new InputMultiplexer();

		tileMapRenderer = new IsometricStaggeredTiledMapRenderer(Assets.tileMap);

		Gdx.input.setInputProcessor(inputMultiplexer);
		movingStage = new Stage();

		// hero
		hero = new Hero();
		movingStage.addActor(hero);

		PoolsReposetory.init();
		for (Animation e : PoolsReposetory.enemyPool.getPool())
			movingStage.addActor(e);

		for (Animation s : PoolsReposetory.shotPool.getPool())
			movingStage.addActor(s);
		for (Animation p : PoolsReposetory.powerUpsPool.getPool())
			movingStage.addActor(p);
		for (Animation b : PoolsReposetory.bloodPool.getPool())
			movingStage.addActor(b);
		for (Animation b : PoolsReposetory.sparksPool.getPool())
			movingStage.addActor(b);

		// FX
		fireOrb = new FireOrb();
		movingStage.addActor(fireOrb);

		// displayed controls
		fixedStage = new FixedStage(inputMultiplexer, movingStage.getCamera(),
				hero, fireOrb);

		// displayed data
		hud = new Hud();
		fixedStage.addActor(hud);

		// playController
		playController = new PlayScreenController(game, hero, fireOrb, hud);
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
		tileMapRenderer.setView((OrthographicCamera)movingStage.getCamera());
		tileMapRenderer.render();
		batch.setProjectionMatrix(movingStage.getCamera().combined);
		// draw background
/*		batch.begin();
		batch.draw(Assets.bg, 0, 0, Assets.PLAY_SCREEN_WIDTH,
				Assets.PLAY_SCREEN_HEIGTH);
		batch.end();
*/		// update game state
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
		Assets.tileMap.dispose();
		tileMapRenderer.dispose();
	}

}
