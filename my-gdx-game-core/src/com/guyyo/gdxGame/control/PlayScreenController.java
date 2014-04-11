package com.guyyo.gdxGame.control;

import com.guyyo.gdxGame.MyGdxGame;
import com.guyyo.gdxGame.model.Animation;
import com.guyyo.gdxGame.model.Animation.STATE;
import com.guyyo.gdxGame.model.CowPool;
import com.guyyo.gdxGame.model.Enemy;
import com.guyyo.gdxGame.model.EnemyPool;
import com.guyyo.gdxGame.model.Hero;
import com.guyyo.gdxGame.model.Hud;
import com.guyyo.gdxGame.model.Shot;
import com.guyyo.gdxGame.model.ShotPool;
import com.guyyo.gdxGame.view.GameOverScreen;

public class PlayScreenController {
	
	private MyGdxGame game;
	private Hero hero;
	private EnemyPool enemyPool;
	private ShotPool shotPool;
	//private CowPool cowPool;
	private Hud hud;


	public PlayScreenController(MyGdxGame game, Hero hero, EnemyPool enemyPool,
			ShotPool shotPool,CowPool cowPool, Hud hud) {
		this.game = game;
		this.hero = hero;
		this.enemyPool = enemyPool;
		this.shotPool = shotPool;
		//this.cowPool = cowPool;
		this.hud = hud;
	}

	public void update() {
		// hero
		if (hero.state == STATE.DEAD) {
			game.setScreen(new GameOverScreen(game));
			//game.playScreen.dispose();
		}
		hero.animate();
		// move enemies
		for (Enemy e : enemyPool.getPool()) {
			if (e.state == STATE.ALIVE) {
				float x = hero.getX() - e.getX();
				float y = hero.getCenterY() - e.getY();
				double deg = Math.atan2(y, x);
				double cos = Math.cos(deg);
				double sin = Math.sin(deg);
				e.moveBy((float) (e.getSpeed() * cos),
						(float) (e.getSpeed() * sin));
				//change animation
				if (sin/cos <=0)
					if (cos >=0)
						e.faceRight();
					else 
						e.faceLeft();
				else 
					if (sin >=0)
						e.faceUp();
					else 
						e.faceDown();
				//Collisions
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
		/* cows
		for (Cow c : cowPool.getPool())
			if (c.state == STATE.ALIVE) {
				c.animate();
				//detectShotEnemyCollisions(c);
			}
			*/
		// score
		hud.update(hero.getShotsLeft(), hero.getHp());
	}

	private void detectHeroEnemyCollisions(Enemy e) {
		if (colliding(hero, e))
			hero.decreaseHp();
	}

	private void detectShotEnemyCollisions(Shot s) {
		for (Enemy e : enemyPool.getPool())
			if (e.state == STATE.ALIVE) {
				if (colliding(e, s)) {
					s.kill();
					e.kill();
					hud.incrementScore();
					break;
				}
			}
	}
	
	private boolean colliding(Animation a, Animation b){
		if (a.getRactangle().overlaps(b.getRactangle()))
			return true;
		return false;
		
	}

}
