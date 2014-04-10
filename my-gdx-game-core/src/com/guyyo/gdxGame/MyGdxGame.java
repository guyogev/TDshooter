package com.guyyo.gdxGame;

import com.badlogic.gdx.Game;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.view.MainMenuScreen;
import com.guyyo.gdxGame.view.PlayScreen;

public class MyGdxGame extends Game {
	MainMenuScreen mainMenuScreen;
	PlayScreen playScreen;

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
