package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Screen;
import com.guyyo.gdxGame.MyGdxGame;
/*
 * flowing MVC pattern, screens are the game View.
 * screen will extract data from the game modal and display it.
 */
public abstract class MyScreen implements Screen {
	//screen hold pointer to the Game object
	MyGdxGame game;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
