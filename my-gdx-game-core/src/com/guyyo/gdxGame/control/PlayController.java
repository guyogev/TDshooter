package com.guyyo.gdxGame.control;

import com.badlogic.gdx.math.Rectangle;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Enemy;
import com.guyyo.gdxGame.model.EnemyPool;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.model.Animation.STATE;
import com.guyyo.gdxGame.model.ShotPool;
import com.guyyo.gdxGame.view.MainMenuScreen;

public class PlayController {
	
	private MyGdxGame game;
	private Hero hero;
	private EnemyPool enemyPool;
	private ShotPool shotPool;
	private Hud hud;

	public PlayController(MyGdxGame game, Hero hero, EnemyPool enemyPool,
			ShotPool shotPool, Hud hud) {
		this.game = game;
		this.hero = hero;
		this.enemyPool = enemyPool;
		this.shotPool = shotPool;
		this.hud = hud;
	}

	public void update() {
		// hero
		if (hero.state == STATE.DEAD) {
			game.setScreen(new MainMenuScreen(game));
			// dispose();
		}
		// move enemies
		for (Enemy e : enemyPool.getPool()) {
			if (e.state == STATE.ALIVE) {
				float x = hero.getCenterX() - e.getX();
				float y = hero.getCenterY() - e.getY();
				double deg = Math.atan2(y, x);
				e.moveBy((float) (e.getSpeed() * Math.cos(deg)),
						(float) (e.getSpeed() * Math.sin(deg)));
				detectHeroEnemyCollisions(e);
				e.animate();
			} else if (e.state == STATE.DEAD)
				e.animate();
			else
				e.spawn();
		}
		// shots
		for (Shot s : shotPool.getPool())
			if (s.state == STATE.ALIVE) {
				s.animate();
				detectShotEnemyCollisions(s);
			}
		// score
		hud.update(hero.getShotsLeft(), hero.getHp());
	}

	private void detectHeroEnemyCollisions(Enemy e) {
		Rectangle r = new Rectangle(e.getX(), e.getY(), e.getWidth(),
				e.getHeight());
		if (r.contains(hero.getCenterX(), hero.getCenterY()))
			hero.decreaseHp();
	}

	private void detectShotEnemyCollisions(Shot s) {
		Rectangle r1, r2;
		for (Enemy e : enemyPool.getPool())
			if (e.state == STATE.ALIVE) {
				r1 = new Rectangle(s.getX(), s.getY(), s.getWidth(),
						s.getHeight());
				r2 = new Rectangle(e.getX(), e.getY(), e.getWidth(),
						e.getHeight());
				if (r1.overlaps(r2)) {
					s.kill();
					e.kill();
					hud.incrementScore();
					break;
				}
			}
	}

}
