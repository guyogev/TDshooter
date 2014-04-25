package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Assets;

public class GameOverScreen extends MyScreen {
	Stage stage;
	String messege = "Game Over";
	SpriteBatch batch;

	public GameOverScreen(MyGdxGame game) {
		this.game = game;
		batch = new SpriteBatch();
		stage = new Stage();
		Table table = new Table();
		table.setFillParent(true);
		table.top().pad(60);
		Label text = new Label(messege, Assets.defultSkin);
		table.add(text).row();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(Assets.menuBg,
				(Math.max(0, Gdx.graphics.getWidth() - 700) / 2),
				(Math.max(0, Gdx.graphics.getHeight() - 480) / 2));
		batch.end();

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
