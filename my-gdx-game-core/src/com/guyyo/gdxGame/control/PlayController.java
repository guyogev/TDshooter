package com.guyyo.gdxGame.control;

import com.badlogic.gdx.math.Rectangle;
import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Animation.STATE;
import com.guyyo.gdxGame.model.Enemy;
import com.guyyo.gdxGame.model.EnemyPool;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.model.ShotPool;
import com.guyyo.gdxGame.view.GameOverScreen;

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
			game.setScreen(new GameOverScreen(game));
			game.playScreen.dispose();
		}
		// move enemies
		for (Enemy e : enemyPool.getPool()) {
			if (e.state == STATE.ALIVE) {
				float x = hero.getX() - e.getX();
				float y = hero.getY() - e.getY();
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
		if (hero.getRactangle().overlaps(e.getRactangle()))
			hero.decreaseHp();
	}

	private void detectShotEnemyCollisions(Shot s) {
		Rectangle r = s.getRactangle();
		for (Enemy e : enemyPool.getPool())
			if (e.state == STATE.ALIVE) {
				if (e.getRactangle().overlaps(r)) {
					s.kill();
					e.kill();
					hud.incrementScore();
					break;
				}
			}
	}

}
