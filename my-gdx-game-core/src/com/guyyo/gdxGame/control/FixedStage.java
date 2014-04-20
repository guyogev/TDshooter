package com.guyyo.gdxGame.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.FireOrb;
import com.guyyo.gdxGame.model.Hero;

/*
 * Following MVC pattern, the FixedStage will handle game logic.
 * FixedStage process buttons input
 */
public class FixedStage extends Stage implements InputProcessor {

	Camera cam;

	// hero movement controller
	Touchpad touchpad;

	TextButton reloadButton;
	ImageButton fireButton;

	Hero hero;
	FireOrb fireOrb;

	public FixedStage(InputMultiplexer inputMultiplexer, Camera camera,
			Hero hero, FireOrb fireOrb) {
		super();

		touchpad = new Touchpad(5, Assets.touchpadStyle);
		touchpad.setBounds(15, 15, 150, 150);
		addActor(touchpad);

		reloadButton = new TextButton("Reload", Assets.defultSkin);
		reloadButton.setBounds(Gdx.graphics.getWidth() - 100, 15, 70, 70);
		addActor(reloadButton);

		fireButton = new ImageButton(Assets.fireButtonUp,
				Assets.fireButtonDown, Assets.fireButtonDown);
		fireButton.setPosition(Gdx.graphics.getWidth() - 200, 45);
		fireButton.setChecked(false);
		addActor(fireButton);

		inputMultiplexer.addProcessor(this);
		this.hero = hero;
		this.cam = camera;
		this.fireOrb = fireOrb;
	}

	public void processInput() {
		float x, y;
		// hero & camera movement
		if (touchpad.isTouched() && !hero.isBehaviorLocked()) {
			if (!hero.isRunning())
				hero.run();
			// hero direction
			hero.setDirection(Math.atan2(touchpad.getKnobPercentY(),
					touchpad.getKnobPercentX()));
			// hero position
			x = hero.getX() + hero.getSpeed() * touchpad.getKnobPercentX();
			y = hero.getY() + hero.getSpeed() * touchpad.getKnobPercentY();
			if (x > 0 && x < Assets.PLAY_SCREEN_WIDTH - hero.getWidth() / 2)
				hero.setX(x);
			if (y > 0 && y < Assets.PLAY_SCREEN_HEIGTH - hero.getHeight() / 2)
				hero.setY(y);
			// hero position offset if camera stay static
			if (hero.getX() > Assets.MOVING_CAM_MIN_X
					&& hero.getX() < Assets.MOVING_CAM_MAX_X)
				hero.updateOffsetX(hero.getSpeed() * touchpad.getKnobPercentX());
			// TODO offsetY
			if (hero.getY() > Assets.MOVING_CAM_MIN_Y
					&& hero.getY() < Assets.MOVING_CAM_MAX_Y)
				hero.updateOffsetY(hero.getSpeed() * touchpad.getKnobPercentY());

			// camera movement
			x = hero.getX();
			y = hero.getY();
			if (x >= Assets.MOVING_CAM_MIN_X && x <= Assets.MOVING_CAM_MAX_X)
				cam.position.x = hero.getX();
			if (y >= Assets.MOVING_CAM_MIN_Y && y <= Assets.MOVING_CAM_MAX_Y)
				cam.position.y = hero.getY();
		} else if (!hero.isStanding() && !hero.isBehaviorLocked())
			hero.stand();

		// reload button
		/*
		 * if (reloadButton.isChecked() && !hero.isReloading()) { hero.reload();
		 * Assets.reload.play(); reloadButton.setChecked(false); }
		 */
		
		// fire button
		if (hero.hasPowerUps() && fireButton.isDisabled()) {
			fireButton.setDisabled(false);
			fireButton.setChecked(false);
		}
		if (!fireButton.isDisabled() && fireButton.isChecked()) {
			System.out.println("fireorb");
			fireOrb.spawn();
			Assets.fireFX.play();
			hero.castSpell();
			hero.decPowerUps();
			fireButton.setDisabled(true);
		}

	}
}
