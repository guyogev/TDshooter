package com.guyyo.gdxGame.control;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Animation;
import com.guyyo.gdxGame.model.Animation.STATE;
import com.guyyo.gdxGame.model.Assets;
import com.guyyo.gdxGame.model.Enemy;
import com.guyyo.gdxGame.model.PoolsReposetory;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.model.SingaltonsRepository;
import com.guyyo.gdxGame.view.GameOverScreen;

/*
 * Following MVC pattern, the PlayScreenController is the main game logic.
 * PlayScreenController process gesture input, and update the game state.
 */
public class PlayScreenController implements GestureListener {

	private MyGdxGame game;

	public PlayScreenController(MyGdxGame game) {
		this.game = game;
	}

	// the main game logic. process Actors state and position and react as
	// needed.
	public void update() {

		// hero
		if (SingaltonsRepository.hero.state == STATE.FREE) {
			Assets.music.stop();
			game.setScreen(new GameOverScreen(game));
			// game.playScreen.dispose();
		}
		SingaltonsRepository.hero.animate();

		// enemies
		for (Animation e : PoolsReposetory.enemyPool.getPool()) {
			if (e.state == STATE.IN_USE) {
				// move
				if (((Enemy) e).isWalking()) {
					double deg = Math.atan2(
							SingaltonsRepository.hero.getCenterY()
									- e.getCenterY(),
							SingaltonsRepository.hero.getCenterX()
									- e.getCenterX());
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
		if (SingaltonsRepository.hp.state == STATE.IN_USE) {
			SingaltonsRepository.hp.animate();
			if (colliding(SingaltonsRepository.hero, SingaltonsRepository.hp)) {
				SingaltonsRepository.hero.incHp(25);
				SingaltonsRepository.hp.spawn();
			}
		}
		if (SingaltonsRepository.mana.state == STATE.IN_USE) {
			SingaltonsRepository.mana.animate();
			if (colliding(SingaltonsRepository.hero, SingaltonsRepository.mana)) {
				SingaltonsRepository.hero.incMana(25);
				SingaltonsRepository.mana.spawn();
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
		if (SingaltonsRepository.fireOrb.state == STATE.IN_USE) {
			SingaltonsRepository.fireOrb.setPosition(
					SingaltonsRepository.hero.getX(),
					SingaltonsRepository.hero.getY() + 10);
			SingaltonsRepository.fireOrb.animate();
		}

		// score
		SingaltonsRepository.hud.update();
	}

	/* ********** Collisions Detections ********** */

	private boolean colliding(Animation a, Animation b) {
		if (a.getRactangle().overlaps(b.getRactangle()))
			return true;
		return false;
	}

	private void detectHeroEnemyCollisions(Enemy e) {
		if (colliding(SingaltonsRepository.hero, e)) {
			if (!SingaltonsRepository.hero.isBehaviorLocked()) {
				if (SingaltonsRepository.hero.decreaseHp())
					SingaltonsRepository.hero.die();
				if (!e.isAttacking()) {
					e.attack();
					PoolsReposetory.bloodPool.spawn(
							SingaltonsRepository.hero.getX(),
							SingaltonsRepository.hero.getY());
				}
				if (SingaltonsRepository.fireOrb.state == STATE.IN_USE) {
					e.die();
					SingaltonsRepository.hud.incScore();
				}
			}
		} else if (!e.isWalking())
			e.walk();
	}

	private void detectShotEnemyCollisions(Shot s) {
		for (Animation e : PoolsReposetory.enemyPool.getPool())
			if (e.state == STATE.IN_USE) {
				if (colliding(e, s)) {
					PoolsReposetory.sparksPool.spawn(e.getX(), e.getY());
					s.kill();
					((Enemy) e).die();
					SingaltonsRepository.hud.incScore();
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
		if (SingaltonsRepository.hero.getMana() >= 10) {
			SingaltonsRepository.hero.decMana(10);
			SingaltonsRepository.hero.attack2();
			PoolsReposetory.shotPool.spawn(SingaltonsRepository.hero.getX(),
					SingaltonsRepository.hero.getY(),
					SingaltonsRepository.hero.getAnimDirection());
			Assets.shotSound.play();
		}
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		SingaltonsRepository.hero.attack();
		int power = (int) Math.abs(velocityY) / 800;
		for (Animation e : PoolsReposetory.enemyPool.getPool()) {
			if (power > 0 && colliding(SingaltonsRepository.hero, e)
					&& !((Enemy) e).isDying()) {
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
