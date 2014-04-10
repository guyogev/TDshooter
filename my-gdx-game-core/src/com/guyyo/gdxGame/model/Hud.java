package com.guyyo.gdxGame.model;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guyyo.gdxGame.model.Assets;

public class Hud extends Table {
	Label scoreLable;
	Label hp;
	Label shots;
	int score;

	public Hud() {
		super();
		scoreLable = new Label("Score: 0", Assets.defultSkin);
		hp = new Label("HP: 100%", Assets.defultSkin);
		shots = new Label("Shots", Assets.defultSkin);

		setFillParent(true);
		top().left();
		add(scoreLable).left().row();
		add(hp).left().row();
		add(shots).left().row();
	}

	public void incrementScore() {
		scoreLable.setText("Score: " + ++score);
	}

	public void update(int shotsleft, float hp) {
		shots.setText("Shots: " + shotsleft);
		this.hp.setText("HP: " + (int)hp + "%");
	}
}
