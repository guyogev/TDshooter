package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Assets;

public class MainMenuScreen extends MyScreen {

	Stage stage;
	TextButton startButton;

	String welcome = "\nEscape and shoot comming enemies.\n\n"
			+ "Use the left touchpad to move around.\n"
			+ "Tap anywhere to Shoot fire balls.\n"
			+ "Swipe up/down to use the axe. the faster you swipe the harder your attack.\n "
			+ "Enjoy\n";

	String credits = "Created by Guy Yogev";

	public MainMenuScreen(MyGdxGame game) {
		this.game = game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		float blockWidth = Gdx.graphics.getWidth() / 4;
		float blockHeigth = Gdx.graphics.getHeight() / 4;

		// welcome message
		Label text = new Label(welcome, Assets.defultSkin);
		text.setPosition(blockWidth, 3 * blockHeigth);
		stage.addActor(text);

		startButton = new TextButton("START", Assets.defultSkin);
		startButton.setPosition(blockWidth, 1.5f * blockHeigth);
		startButton.setSize(2 * blockWidth, 1.f * blockHeigth);
		stage.addActor(startButton);

		// credits
		Label text2 = new Label(credits, Assets.defultSkin);
		text2.setPosition(blockWidth, blockHeigth);
		stage.addActor(text2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .3f, 0.1f, .5f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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
