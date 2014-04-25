package com.guyyo.gdxGame.model;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/*
 * game statistics
 */
public class Hud extends Table {
	Label scoreLable;
	Label hpLable;
	Label manaLable;
	ProgressBar hpBar, manaBar;
	int score;

	public Hud() {
		super();
		scoreLable = new Label("Score: 0", Assets.defultSkin);
		hpLable = new Label("HP:", Assets.defultSkin);
		manaLable = new Label("Mana:", Assets.defultSkin);
		hpBar = new ProgressBar(0, 100, 1, false, Assets.defultSkin);
		hpBar.setValue(100);
		manaBar = new ProgressBar(0, 100, 1, false, Assets.defultSkin);
		manaBar.setValue(100);

		setFillParent(true);
		top().left();
		add(scoreLable).left().row();
		add(hpLable).left().row();
		add(hpBar).row();
		add(manaLable).left().row();
		add(manaBar).row();
	}

	public void incScore() {
		scoreLable.setText("Score: " + ++score);
	}

	public void update() {
		hpBar.setValue(SingaltonsRepository.hero.getHp());
		manaBar.setValue(SingaltonsRepository.hero.getMana());
	}
}
