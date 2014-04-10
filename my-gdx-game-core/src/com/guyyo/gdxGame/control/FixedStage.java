package com.guyyo.gdxGame.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.EnemyPool;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.ShotPool;

public class FixedStage extends Stage implements InputProcessor {
	Touchpad touchpad;
	TextButton reloadButton;
	Hero hero;
	Camera cam;
	EnemyPool enemyPool;
	ShotPool shotpool;

	public FixedStage(InputMultiplexer inputMultiplexer, Camera camera,
			Hero hero, EnemyPool enemyPool, ShotPool shotpool) {
		super();
		touchpad = new Touchpad(10, Assets.defultSkin);
		touchpad.setBounds(15, 15, 150, 150);
		addActor(touchpad);
		reloadButton = new TextButton("Reload", Assets.defultSkin);
		reloadButton.setBounds(Gdx.graphics.getWidth()-100, 15, 70, 70);
		addActor(reloadButton);
		inputMultiplexer.addProcessor(this);
		this.hero = hero;
		this.cam = camera;
		this.enemyPool = enemyPool;
		this.shotpool = shotpool;
	}

	public void processInput() {
		float x, y;
		// hero & camera movement
		if (touchpad.isTouched()) {
			// hero rotation
			hero.setRotation(-Math.atan2(touchpad.getKnobPercentX(),
					touchpad.getKnobPercentY())
					* 180 / Math.PI);
			//hero position
			x = hero.getX() + hero.getSpeed() * touchpad.getKnobPercentX();
			y = hero.getY() + hero.getSpeed() * touchpad.getKnobPercentY();
			if (x > 0 && x < Assets.PLAY_SCREEN_WIDTH - 3 * hero.getWidth()) {
				hero.setX(x);
				hero.setOffsetX(hero.getSpeed() * touchpad.getKnobPercentX());
			}
			if (y > 0 && y < Assets.PLAY_SCREEN_HEIGTH - 2 * hero.getHeight()) {
				hero.setY(y);
				hero.setOffsetY(hero.getSpeed() * touchpad.getKnobPercentY());
			}
			hero.animate();
			
			// camera movement
			x = hero.getX();
			y = hero.getY();
			if (x >= Assets.MOVING_CAM_MIN_X && x <= Assets.MOVING_CAM_MAX_X)
				cam.position.x = hero.getX();
			if (y >= Assets.MOVING_CAM_MIN_Y && y <= Assets.MOVING_CAM_MAX_Y)
				cam.position.y = hero.getY();

		}
		// reload button
		if (reloadButton.isChecked()){
			hero.reload();
			reloadButton.setChecked(false);
		}
		
	}
}
