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
import com.guyyo.gdxGame.model.SingletonsRepository;
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
		if (SingletonsRepository.hero.state == STATE.FREE) {
			Assets.music.stop();
			game.setScreen(new GameOverScreen(game));
			// game.playScreen.dispose();
		}
		SingletonsRepository.hero.animate();

		// enemies
		for (Animation e : PoolsReposetory.enemyPool.getPool()) {
			if (e.state == STATE.IN_USE) {
				// move
				if (((Enemy) e).isWalking()) {
					double deg = Math.atan2(
							SingletonsRepository.hero.getCenterY()
									- e.getCenterY(),
							SingletonsRepository.hero.getCenterX()
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
			}
		}

		// shots
		for (Animation s : PoolsReposetory.shotPool.getPool())
			if (s.state == STATE.IN_USE) {
				s.animate();
				detectShotEnemyCollisions((Shot) s);
			}

		// Hp
		if (SingletonsRepository.hp.state == STATE.IN_USE) {
			SingletonsRepository.hp.animate();
			if (colliding(SingletonsRepository.hero, SingletonsRepository.hp)) {
				SingletonsRepository.hero.incHp(25);
				SingletonsRepository.hp.spawn();
			}
		}
		// Mana
		if (SingletonsRepository.mana.state == STATE.IN_USE) {
			SingletonsRepository.mana.animate();
			if (colliding(SingletonsRepository.hero, SingletonsRepository.mana)) {
				SingletonsRepository.hero.incMana(25);
				SingletonsRepository.mana.spawn();
			}
		}

		// blood
		for (Animation b : PoolsReposetory.bloodPool.getPool())
			if (b.state == STATE.IN_USE) {
				b.animate();
			}

		// sparks
		for (Animation s : PoolsReposetory.sparksPool.getPool())
			if (s.state == STATE.IN_USE) {
				s.animate();
			}

		// Lightning
		for (Animation l : PoolsReposetory.lightningPool.getPool())
			if (l.state == STATE.IN_USE) {
				l.animate();
			}

		// fireOrb
		if (SingletonsRepository.fireOrb.state == STATE.IN_USE) {
			SingletonsRepository.fireOrb.setPosition(
					SingletonsRepository.hero.getX(),
					SingletonsRepository.hero.getY() + 10);
			SingletonsRepository.fireOrb.animate();
		}

		// score
		SingletonsRepository.hud.update();
	}

	/* ********** Collisions Detections ********** */

	private boolean colliding(Animation a, Animation b) {
		if (a.getRactangle().overlaps(b.getRactangle()))
			return true;
		return false;
	}

	private void detectHeroEnemyCollisions(Enemy e) {
		if (colliding(SingletonsRepository.hero, e)) {
			if (!SingletonsRepository.hero.isBehaviorLocked()) {
				if (SingletonsRepository.hero.decreaseHp())
					SingletonsRepository.hero.die();
				if (!e.isAttacking()) {
					e.attack();
					PoolsReposetory.bloodPool.spawn(
							SingletonsRepository.hero.getX(),
							SingletonsRepository.hero.getY());
				}
				if (SingletonsRepository.fireOrb.state == STATE.IN_USE) {
					e.die();
					SingletonsRepository.hud.incScore();
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
					SingletonsRepository.hud.incScore();
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
		if (SingletonsRepository.hero.getMana() >= 10) {
			SingletonsRepository.hero.decMana(10);
			SingletonsRepository.hero.attack2();
			PoolsReposetory.shotPool.spawn(SingletonsRepository.hero.getX(),
					SingletonsRepository.hero.getY(),
					SingletonsRepository.hero.getAnimDirection());
			Assets.shotSound.play();
		}
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		SingletonsRepository.hero.hardAttack = true;
		System.out.println("longpress");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (SingletonsRepository.hero.hardAttack) {
			for (Animation e : PoolsReposetory.enemyPool.getPool())
				if (colliding(SingletonsRepository.hero, e)
						&& !((Enemy) e).isDying()) {
					((Enemy) e).die();
					PoolsReposetory.lightningPool.spawn(((Enemy) e).getX(),
							((Enemy) e).getY());
					Assets.thonder.play();
					SingletonsRepository.hero.decMana(5);
					if (SingletonsRepository.hero.getMana() < 5)
						break;
				}
			SingletonsRepository.hero.hardAttack = false;
		} else {
			SingletonsRepository.hero.attack();
			int power = (int) Math.abs(velocityY) / 800;
			for (Animation e : PoolsReposetory.enemyPool.getPool()) {
				if (power > 0 && colliding(SingletonsRepository.hero, e)
						&& !((Enemy) e).isDying()) {
					((Enemy) e).die();
					if (--power <= 0)
						break;
				}
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
