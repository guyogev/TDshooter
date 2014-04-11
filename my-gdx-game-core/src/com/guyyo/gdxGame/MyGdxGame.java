package com.guyyo.gdxGame;

import com.badlogic.gdx.Game;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.view.MainMenuScreen;

public class MyGdxGame extends Game {

	@Override
	public void create() {
		Assets.load();
		setScreen(new MainMenuScreen(this));
		
	}

	public void render() {
		super.render(); 
	}

	public void dispose() {
	}
}
