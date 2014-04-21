package com.guyyo.gdxGame.control;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Animation;
import com.guyyo.gdxGame.model.Animation.STATE;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.Enemy;
import com.guyyo.gdxGame.model.FireOrb;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.PoolsReposetory;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.view.GameOverScreen;

/*
 * Following MVC pattern, the PlayScreenController is the main game logic.
 * PlayScreenController process gesture input, and update the game state.
 */
public class PlayScreenController implements GestureListener {

	private MyGdxGame game;
	private Hero hero;
	private FireOrb fireOrb;
	// private CowPool cowPool;
	private Hud hud;

	public PlayScreenController(MyGdxGame game, Hero hero, FireOrb fireOrb,
			Hud hud) {
		this.game = game;
		this.hero = hero;
		this.fireOrb = fireOrb;
		// this.cowPool = cowPool;
		this.hud = hud;
	}

	// the main game logic. process Actors state and position and react as
	// needed.
	public void update() {

		// hero
		if (hero.state == STATE.FREE) {
			Assets.music.stop();
			game.setScreen(new GameOverScreen(game));
			// game.playScreen.dispose();
		}
		hero.animate();

		// enemies
		for (Animation e : PoolsReposetory.enemyPool.getPool()) {
			if (e.state == STATE.IN_USE) {
				// move
				if (((Enemy) e).isWalking()) {
					double deg = Math.atan2(hero.getCenterY() - e.getCenterY(),
							hero.getCenterX() - e.getCenterX());
					double cos = Math.cos(deg);
					double sin = Math.sin(deg);
					e.moveBy((float) (e.getSpeed() * cos),
							(float) (e.getSpeed() * sin));
					e.setDirection(deg);
				}
				// Collisions
				if (!((Enemy) e).isBehaviorLocked())
					detectHeroEnemyCollisions((Enemy) e);
				e.animate();
			} // else
				// e.spawn();
		}

		// shots
		for (Animation s : PoolsReposetory.shotPool.getPool())
			if (s.state == STATE.IN_USE) {
				s.animate();
				detectShotEnemyCollisions((Shot) s);
			}

		// powerUps
		for (Animation p : PoolsReposetory.powerUpsPool.getPool())
			if (p.state == STATE.IN_USE) {
				p.animate();
				if (colliding(hero, p) && !hero.hasPowerUps()) {
					hero.incPowerUp();
					p.spawn();
				}
			}

		// blood
		for (Animation b : PoolsReposetory.bloodPool.getPool())
			if (b.state == STATE.IN_USE) {
				b.animate();
			}

		for (Animation s : PoolsReposetory.sparksPool.getPool())
			if (s.state == STATE.IN_USE) {
				s.animate();
			}

		// FX
		if (fireOrb.state == STATE.IN_USE) {
			fireOrb.setPosition(hero.getX(), hero.getY() + 10);
			fireOrb.animate();
		}

		// score
		hud.update(hero.getShotsLeft(), hero.getHp());
	}

	/* ********** Collisions Detections ********** */

	private boolean colliding(Animation a, Animation b) {
		if (a.getRactangle().overlaps(b.getRactangle()))
			return true;
		return false;
	}

	private void detectHeroEnemyCollisions(Enemy e) {
		if (colliding(hero, e)) {
			if (!hero.isBehaviorLocked()) {
				if (hero.decreaseHp())
					hero.die();
				if (!e.isAttacking()) {
					e.attack();
					PoolsReposetory.bloodPool.spawn(
							hero.getX() , hero.getY() );
				}
				if (fireOrb.state == STATE.IN_USE) {
					e.die();
					hud.incScore();
				}
			}
		} else if (!e.isWalking())
			e.walk();
	}

	private void detectShotEnemyCollisions(Shot s) {
		for (Animation e : PoolsReposetory.enemyPool.getPool())
			if (e.state == STATE.IN_USE) {
				if (colliding(e, s)) {
					PoolsReposetory.sparksPool.spawn(
							e.getX(), e.getY());
					s.kill();
					((Enemy) e).die();
					hud.incScore();
					break;
				}
			}
	}

	/* ********** Gesture Handling ********** */

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		hero.attack2();
		PoolsReposetory.shotPool.spawn(hero.getX(), hero.getY(),
				hero.getAnimDirection());
		Assets.shotSound.play();
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		hero.attack();
		int power = (int) Math.abs(velocityY) / 800;
		for (Animation e : PoolsReposetory.enemyPool.getPool()) {
			if (power > 0 && colliding(hero, e) && !((Enemy) e).isDying()) {
				((Enemy) e).die();
				if (--power <= 0)
					break;
			}
		}
		Assets.axe.play();
		return true;
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
