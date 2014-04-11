package com.guyyo.gdxGame.control;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.model.ShotPool;

public class MyGestureController implements GestureListener {

	float velX, velY;
	boolean flinging = false;
	float initialScale = 1;
	OrthographicCamera camera;
	private Hero hero;
	ShotPool shotPool;

	public MyGestureController(Camera camera, Hero hero, ShotPool shotPool) {
		super();
		this.camera = (OrthographicCamera) camera;
		this.hero = hero;
		this.shotPool = shotPool;
	}

	public boolean touchDown(float x, float y, int pointer, int button) {
		// flinging = false;
		// initialScale = camera.zoom;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		System.out.println(hero.getRelX());
		double rad = Math.atan2(x - hero.getRelX(), hero.getRelY() - y);
		double deg = rad * 180 / Math.PI;
		hero.setRotation(-deg);
		if (hero.canFire()) {
			hero.fire();
			float dy = (float) (Shot.speed * Math.cos(rad));
			float dx = (float) (Shot.speed * Math.sin(rad));
			shotPool.spawn(hero.getX(), hero.getY(), dx, dy, deg);
			Assets.shotSound.play();
		}
		else if (!hero.isReloading())
			Assets.pistolEmpty.play();
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// Gdx.app.log("GestureDetectorTest", "long press at " + x + ", " + y);
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", " +
		// velocityY);
		// flinging = true;
		// velX = camera.zoom * velocityX * 0.5f;
		// velY = camera.zoom * velocityY * 0.5f;
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// Gdx.app.log("GestureDetectorTest", "pan at " + x + ", " + y);
		// camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// Gdx.app.log("GestureDetectorTest", "pan stop at " + x + ", " + y);
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		// float ratio = originalDistance / currentDistance;
		// camera.zoom = initialScale * ratio;
		// System.out.println(camera.zoom);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer,
			Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		return false;
	}

	public void update() {
		// if (flinging) {
		// velX *= 0.98f;
		// velY *= 0.98f;
		// camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY *
		// Gdx.graphics.getDeltaTime(), 0);
		// if (Math.abs(velX) < 0.01f) velX = 0;
		// if (Math.abs(velY) < 0.01f) velY = 0;
		// }
	}
}
