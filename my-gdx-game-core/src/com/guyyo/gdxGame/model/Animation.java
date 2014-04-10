package com.guyyo.gdxGame.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Animation extends Actor {
	
	public static enum STATE {
		SPAWN, ALIVE, DEAD
	}
	public STATE state;
	TextureRegion[] frames;
	float frameIndex, speed;

	public abstract void draw(Batch batch, float alpha);

	public abstract void animate();

	public void loadTexture(Texture t, int rows, int cols) {
		TextureRegion[][] tmp = TextureRegion.split(t, t.getWidth() / cols,
				t.getHeight() / rows);
		frames = new TextureRegion[rows * cols ];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		setOrigin(frames[0].getRegionWidth() / 2,
				frames[0].getRegionHeight() / 2);
		setWidth(frames[0].getRegionWidth());
		setHeight(frames[0].getRegionHeight());
	}

	public float getSpeed() {
		return speed;
	}
	
	public void kill(){
		state = STATE.DEAD;
	}
}
