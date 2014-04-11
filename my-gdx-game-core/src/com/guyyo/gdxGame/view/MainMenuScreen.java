package com.guyyo.gdxGame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Assets;

public class MainMenuScreen extends MyScreen {

	Stage stage;
	String message = "fire by tapping. move with left touchpad.";
	Table table;
	TextButton startButton;
	
	
	public MainMenuScreen(MyGdxGame game) {
		this.game = game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//create table
		table = new Table();
		table.setFillParent(true);
		table.center();
		//add welcome message
		Label text = new Label(message, Assets.defultSkin);
		table.add(text).top().fill().pad(50).row();
		//add start button

		startButton = new TextButton("START", Assets.defultSkin);
		//startButton.pad(20);
		table.add(startButton).fill().row();
		
		Label text2 = new Label("created by Guy Yogev", Assets.defultSkin);
		table.add(text2).top().fill().pad(50).row();
		//add table
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .3f, 0.1f, .5f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		stage.getCamera().update();
		stage.draw();

		if (startButton.isChecked()) {
			//game.playScreen = new  PlayScreen(game);
			game.setScreen(new  PlayScreen(game));
			dispose();
		}
	}

	@Override
	public void dispose() {
		stage.dispose();

	}
}
