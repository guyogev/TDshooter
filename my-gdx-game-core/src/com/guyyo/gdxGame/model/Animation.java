package com.guyyo.gdxGame.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guyyo.gdxGame.model.Hero.AnimState;

public abstract class Animation extends Actor {
	public static enum STATE {
		SPAWN, ALIVE, DEAD
	}
	public static Random rand = new Random();
	public STATE state;

	Hashtable<AnimState, Integer> animHash;
	ArrayList<TextureRegion[]> frames = new ArrayList<TextureRegion[]>();
	float frameCol, speed;
	int frameRow;
	Rectangle rectangle;

	public abstract void animate();
	public abstract void spawn();

	public void draw(TextureRegion t, Batch batch, float alpha) {
		batch.draw(t, getX() - t.getRegionWidth() / 2,
				getY() - t.getRegionHeight() / 2, getOriginX(), getOriginY(),
				t.getRegionWidth(), t.getRegionHeight(), getScaleX(),
				getScaleY(), this.getRotation());
	}

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
		TextureRegion t = frames.get(frameRow)[0];
		setOrigin(t.getRegionWidth() / 2, t.getRegionHeight() / 2);
		setWidth(t.getRegionWidth());
		setHeight(t.getRegionHeight());
		rectangle = new Rectangle();
	}

	public TextureRegion getFrame(int frameRow) {
		return frames.get(frameRow)[(int) frameCol];
	}

	public float getSpeed() {
		return speed;
	}
	
	public float getCenterX() {
		return getX() - getOriginX();
	}

	public float getCenterY() {
		return getY() - getOriginY();
	}

	public int getRowLength(int row) {
		return frames.get(0).length;
	}

	public Rectangle getRactangle() {
		rectangle.set(getX(), getY(), getWidth(), getHeight());
		return rectangle;
	}

	public void kill() {
		state = STATE.DEAD;
	}
}
