package com.guyyo.gdxGame.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Animation extends Actor {
	public static enum STATE {
		SPAWN, ALIVE, DEAD
	}

	public STATE state;
	ArrayList<TextureRegion[]> frames = new ArrayList<TextureRegion[]>();
	float frameCol, speed;

	public abstract void draw(Batch batch, float alpha);

	int frameRow;

	public abstract void animate();

	public void loadTexture(Texture t, int rows, int cols) {
		TextureRegion[][] tmp = TextureRegion.split(t, t.getWidth() / cols,
				t.getHeight() / rows);
		frames.ensureCapacity(frameRow + rows);
		for (int i = 0; i < rows; i++) {
			frames.add(frameRow, new TextureRegion[cols]);
			for (int j = 0; j < cols; j++) {
				frames.get(frameRow)[j] = tmp[i][j];
			}
			frameRow++;
		}
	}

	public void initParams() {
		frameRow = 0;
		setOrigin(frames.get(frameRow)[0].getRegionWidth() / 2,
				frames.get(frameRow)[0].getRegionHeight() / 2);
		setWidth(frames.get(frameRow)[0].getRegionWidth());
		setHeight(frames.get(frameRow)[0].getRegionHeight());
	}

	public TextureRegion getFrame(int frameRow) {
		return frames.get(frameRow)[(int) frameCol];
	}

	public float getSpeed() {
		return speed;
	}

	public void kill() {
		state = STATE.DEAD;
	}
}
