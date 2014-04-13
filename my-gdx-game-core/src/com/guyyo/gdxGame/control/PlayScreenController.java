package com.guyyo.gdxGame.control;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Animation;
import com.guyyo.gdxGame.model.Animation.STATE;
import com.guyyo.gdxGame.model.Hero.AnimState;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.BloodPool;
import com.guyyo.gdxGame.model.CowPool;
import com.guyyo.gdxGame.model.Enemy;
import com.guyyo.gdxGame.model.EnemyPool;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.PowerUpsPool;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.model.ShotPool;
import com.guyyo.gdxGame.view.GameOverScreen;

public class PlayScreenController implements GestureListener {

	private MyGdxGame game;
	private Hero hero;
	private EnemyPool enemyPool;
	private ShotPool shotPool;
	private PowerUpsPool powerUpPool;
	private BloodPool bloodPool;
	// private CowPool cowPool;
	private Hud hud;

	public PlayScreenController(MyGdxGame game, Hero hero, EnemyPool enemyPool,
			ShotPool shotPool, CowPool cowPool, PowerUpsPool powerUpsPool,
			BloodPool bloodPool, Hud hud) {
		this.game = game;
		this.hero = hero;
		this.enemyPool = enemyPool;
		this.shotPool = shotPool;
		this.powerUpPool = powerUpsPool;
		this.bloodPool = bloodPool;
		// this.cowPool = cowPool;
		this.hud = hud;
	}

	public void update() {
		// hero
		if (hero.state == STATE.DEAD) {
			Assets.music.stop();
			game.setScreen(new GameOverScreen(game));
			// game.playScreen.dispose();
		}
		if (hero.animState == AnimState.RELOADING)
			hero.checkDoneReloading();
		if (hero.isRunning())
			hero.animate();
		// move enemies
		for (Animation e : enemyPool.getPool()) {
			if (e.state == STATE.ALIVE) {
				float x = hero.getCenterX() - e.getCenterX();
				float y = hero.getCenterY() - e.getCenterY();
				double deg = Math.atan2(y, x);
				double cos = Math.cos(deg);
				double sin = Math.sin(deg);
				e.moveBy((float) (e.getSpeed() * cos),
						(float) (e.getSpeed() * sin));
				// change animation
				if (sin / cos <= 0)
					if (cos >= 0)
						((Enemy) e).faceRight();
					else
						((Enemy) e).faceLeft();
				else if (sin >= 0)
					((Enemy) e).faceUp();
				else
					((Enemy) e).faceDown();
				// Collisions
				detectHeroEnemyCollisions((Enemy) e);
				e.animate();
			} else if (e.state == STATE.DEAD)
				e.animate();
			else
				e.spawn();
		}
		// shots
		for (Animation s : shotPool.getPool())
			if (s.state == STATE.ALIVE) {
				s.animate();
				detectShotEnemyCollisions((Shot) s);
			}
		// powerUps
		for (Animation p : powerUpPool.getPool())
			if (p.state == STATE.ALIVE) {
				p.animate();
			}
		// blood
		for (Animation b : bloodPool.getPool())
			if (b.state == STATE.ALIVE) {
				b.animate();
			}
		/*
		 * cows for (Cow c : cowPool.getPool()) if (c.state == STATE.ALIVE) {
		 * c.animate(); //detectShotEnemyCollisions(c); }
		 */
		// score
		hud.update(hero.getShotsLeft(), hero.getHp());
	}

	private void detectHeroEnemyCollisions(Enemy e) {
		if (colliding(hero, e))
			hero.decreaseHp();
	}

	private void detectShotEnemyCollisions(Shot s) {
		for (Animation e : enemyPool.getPool())
			if (e.state == STATE.ALIVE) {
				if (colliding(e, s)) {
					bloodPool.spawn(e.getCenterX() + e.getOriginX(),
							e.getCenterY());
					s.kill();
					e.kill();
					hud.incrementScore();
					break;
				}
			}
	}

	private boolean colliding(Animation a, Animation b) {
		if (a.getRactangle().overlaps(b.getRactangle()))
			return true;
		return false;

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		System.out.println("tapped at x=" + x + " y=" + y);
		System.out.println("hero at x=" + hero.getX() + " y=" + hero.getY());
		System.out.println("hero relative at x=" + hero.getRelX() + " y="
				+ hero.getRelY());
		double rad = Math.atan2(x - hero.getRelX(), hero.getRelY() - y);
		double deg = rad * 180 / Math.PI;
		hero.setRotation(-deg);
		if (hero.canFire()) {
			hero.fire();
			float dy = (float) (Shot.speed * Math.cos(rad));
			float dx = (float) (Shot.speed * Math.sin(rad));
			shotPool.spawn(hero.getX(), hero.getY(), dx, dy, deg);
			Assets.shotSound.play();
		} else if (!hero.isReloading())
			Assets.pistolEmpty.play();
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
