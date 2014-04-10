package com.guyyo.gdxGame;

import com.badlogic.gdx.Game;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.view.GameOverScreen;
import com.guyyo.gdxGame.view.MainMenuScreen;
import com.guyyo.gdxGame.view.PlayScreen;

public class MyGdxGame extends Game {
	public MainMenuScreen mainMenuScreen;
	public PlayScreen playScreen;
	public GameOverScreen gameOverScreen;

	@Override
	public void create() {
		Assets.load();
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(mainMenuScreen);
	}

	public void render() {
		super.render(); 
	}

	public void dispose() {
	}
}
