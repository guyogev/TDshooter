package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Assets;

public class GameOverScreen extends MyScreen {
	Stage stage;
	String messege = "Game Over";

	public GameOverScreen(MyGdxGame game) {
		this.game = game;
		stage = new Stage();
		Table table = new Table();
		table.setFillParent(true);
		table.center();
		Label text = new Label(messege, Assets.defultSkin);
		table.add(text);
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .3f, 0.1f, .5f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		stage.getCamera().update();
		stage.draw();
		
		if (Gdx.input.isTouched()) {
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}
	}

	@Override
	public void dispose() {
		stage.dispose();

	}
}
