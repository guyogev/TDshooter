package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Assets;

public class MainMenuScreen extends MyScreen {

	Stage stage;
	TextButton startButton;
	SpriteBatch batch;

	String welcome = "\nEscape and shoot coming enemies.\n\n"
			+ "Use the left touch pad to move around.\n"
			+ "Axe attack is always available:\n"
			+ "   Swipe up/down to use the axe. the faster you swipe the harder your attack.\n"
			+ "Magic attack require mana:\n"
			+ "   Tap anywhere to Shoot fire balls.\n"
			+ "   Long press & swipe for lightning strike.\n" + "\nEnjoy\n";

	public MainMenuScreen(MyGdxGame game) {
		this.game = game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();

		float blockWidth = Gdx.graphics.getWidth() / 4;
		float blockHeigth = Gdx.graphics.getHeight() / 4;

		// welcome message
		Label text = new Label(welcome, Assets.defultSkin);
		text.setPosition(blockWidth / 3, 2.2f * blockHeigth);
		stage.addActor(text);

		startButton = new TextButton("START", Assets.defultSkin);
		startButton.setPosition(blockWidth / 3, blockHeigth);
		startButton.setSize(blockWidth, blockHeigth);
		stage.addActor(startButton);

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

		if (startButton.isChecked()) {
			// game.playScreen = new PlayScreen(game);
			game.setScreen(new PlayScreen(game));
			dispose();
		}
	}

	@Override
	public void dispose() {
		stage.dispose();

	}
}
