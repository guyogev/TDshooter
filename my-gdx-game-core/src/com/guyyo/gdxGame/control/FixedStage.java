package com.guyyo.gdxGame.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.SingletonsRepository;

/*
 * Following MVC pattern, the FixedStage will handle game logic.
 * FixedStage process buttons input
 */
public class FixedStage extends Stage implements InputProcessor {

	Camera cam;

	// hero movement controller
	Touchpad touchpad;

//	TextButton reloadButton;
	ImageButton fireButton;

	public FixedStage(InputMultiplexer inputMultiplexer, Camera camera) {
		super();

		touchpad = new Touchpad(5, Assets.touchpadStyle);
		touchpad.setBounds(15, 15, 150, 150);
		addActor(touchpad);
/*
		reloadButton = new TextButton("Reload", Assets.defultSkin);
		reloadButton.setBounds(Gdx.graphics.getWidth() - 100, 15, 70, 70);
		addActor(reloadButton);
*/
		fireButton = new ImageButton(Assets.fireButtonUp,
				Assets.fireButtonDown, Assets.fireButtonDown);
		fireButton.setPosition(Gdx.graphics.getWidth() - 200, 45);
		fireButton.setChecked(false);
		addActor(fireButton);

		inputMultiplexer.addProcessor(this);
		this.cam = camera;
	}

	public void processInput() {
		float x, y;
		// hero & camera movement
		if (touchpad.isTouched()
				&& !SingletonsRepository.hero.isBehaviorLocked()) {
			if (!SingletonsRepository.hero.isRunning())
				SingletonsRepository.hero.run();
			// hero direction
			SingletonsRepository.hero.setDirection(Math.atan2(
					touchpad.getKnobPercentY(), touchpad.getKnobPercentX()));
			// hero position
			x = SingletonsRepository.hero.getX()
					+ SingletonsRepository.hero.getSpeed()
					* touchpad.getKnobPercentX();
			y = SingletonsRepository.hero.getY()
					+ SingletonsRepository.hero.getSpeed()
					* touchpad.getKnobPercentY();
			if (x > 0
					&& x < Assets.PLAY_SCREEN_WIDTH
							- SingletonsRepository.hero.getWidth() / 2)
				SingletonsRepository.hero.setX(x);
			if (y > 0
					&& y < Assets.PLAY_SCREEN_HEIGTH
							- SingletonsRepository.hero.getHeight() / 2)
				SingletonsRepository.hero.setY(y);
			// hero position offset if camera stay static
			if (SingletonsRepository.hero.getX() > Assets.MOVING_CAM_MIN_X
					&& SingletonsRepository.hero.getX() < Assets.MOVING_CAM_MAX_X)
				SingletonsRepository.hero
						.updateOffsetX(SingletonsRepository.hero.getSpeed()
								* touchpad.getKnobPercentX());
			// TODO offsetY
			if (SingletonsRepository.hero.getY() > Assets.MOVING_CAM_MIN_Y
					&& SingletonsRepository.hero.getY() < Assets.MOVING_CAM_MAX_Y)
				SingletonsRepository.hero
						.updateOffsetY(SingletonsRepository.hero.getSpeed()
								* touchpad.getKnobPercentY());

			// camera movement
			x = SingletonsRepository.hero.getX();
			y = SingletonsRepository.hero.getY();
			if (x >= Assets.MOVING_CAM_MIN_X && x <= Assets.MOVING_CAM_MAX_X)
				cam.position.x = SingletonsRepository.hero.getX();
			if (y >= Assets.MOVING_CAM_MIN_Y && y <= Assets.MOVING_CAM_MAX_Y)
				cam.position.y = SingletonsRepository.hero.getY();
		} else if (!SingletonsRepository.hero.isStanding()
				&& !SingletonsRepository.hero.isBehaviorLocked())
			SingletonsRepository.hero.stand();

		// reload button
		/*
		 * if (reloadButton.isChecked() && !hero.isReloading()) { hero.reload();
		 * Assets.reload.play(); reloadButton.setChecked(false); }
		 */

		// fire button
		if (SingletonsRepository.hero.getMana() > 50
				&& !fireButton.isVisible()) {
			fireButton.setVisible(true);
			fireButton.setDisabled(false);
			fireButton.setChecked(false);
		}
		if (!fireButton.isDisabled() && fireButton.isChecked()) {
			SingletonsRepository.fireOrb.spawn();
			Assets.fireFX.play(.7f);
			SingletonsRepository.hero.castSpell();
			SingletonsRepository.hero.decMana(50);;
			fireButton.setDisabled(true);
			fireButton.setVisible(false);
		}

	}
}
