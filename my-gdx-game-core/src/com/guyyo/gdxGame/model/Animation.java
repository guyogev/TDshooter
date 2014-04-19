package com.guyyo.gdxGame.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
 * Animated game objects.
 */
public abstract class Animation extends Actor {

	// Animation are created only once reused as needed.
	public static enum STATE {
		SPAWN, IN_USE, FREE
	}

	enum AnimDirection {
		N, NE, NW, S, SE, SW, W, E
	}

	Hashtable<AnimDirection, Integer> animHash;
	AnimDirection animDirection;

	public STATE state;
	public static Random rand = new Random();

	// images are held by 2d array, each row is an animated state sequence.
	ArrayList<TextureRegion[]> frames = new ArrayList<TextureRegion[]>();

	float speed, animDelta = .1f;
	int frameRow;
	double frameCol, sceneIndex;
	// Rectangles are used for easy collision detection
	Rectangle rectangle;

	public abstract void animate();

	public abstract void spawn();

	// default draw behavior
	public void draw(TextureRegion t, Batch batch, float alpha) {
		batch.draw(t, getX() - t.getRegionWidth() / 2,
				getY() - t.getRegionHeight() / 2, getOriginX(), getOriginY(),
				t.getRegionWidth(), t.getRegionHeight(), getScaleX(),
				getScaleY(), this.getRotation());
	}

	// load a spread sheet of size rows*cols images, into frames Array. you can
	// load multiple sheets.
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

	// use after loading images to frame Array.
	// set the Actor size & origin parameters and gets ready for drawing.
	public void initParams() {
		frameRow = 0;
		TextureRegion t = frames.get(frameRow)[0];
		setOrigin(t.getRegionWidth() / 2, t.getRegionHeight() / 2);
		setWidth(t.getRegionWidth()/2);
		setHeight(t.getRegionHeight()/2);
		rectangle = new Rectangle();
	}

	public void setDirection(double deg) {
		if (deg >= 0) {
			if (deg < Math.PI / 8)
				faceEast();
			else if (deg < 3 * Math.PI / 8)
				faceNorthEast();
			else if (deg < 5 * Math.PI / 8)
				faceNorth();
			else if (deg < 7 * Math.PI / 8)
				faceNorthWest();
			else 
				faceWest();
		} else {
			if (-deg < Math.PI / 8)
				faceEast();
			else if (-deg < 3 * Math.PI / 8)
				faceSouthEast();
			else if (-deg < 5 * Math.PI / 8)
				faceSouth();
			else if (-deg < 7 * Math.PI / 8)
				faceSouthWest();
			else 
				faceWest();
		}
	}
	
	public void setDirection(AnimDirection dirc) {
		animDirection = dirc;
	}

	/* ********* getters ********* */
	public TextureRegion getFrame(int frameRow) {
		return frames.get(frameRow)[(int) frameCol];
	}

	public AnimDirection getAnimDirection() {
		return animDirection;
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

	/* ********* state modifiers ********* */
	public void kill() {
		state = STATE.FREE;
	}

	public void faceNorth() {
		animDirection = AnimDirection.N;
	}

	public void faceSouth() {
		animDirection = AnimDirection.S;
	}

	public void faceWest() {
		animDirection = AnimDirection.W;
	}

	public void faceEast() {
		animDirection = AnimDirection.E;
	}

	public void faceNorthEast() {
		animDirection = AnimDirection.NE;
	}

	public void faceNorthWest() {
		animDirection = AnimDirection.NW;
	}

	public void faceSouthWest() {
		animDirection = AnimDirection.SW;
	}

	public void faceSouthEast() {
		animDirection = AnimDirection.SE;
	}


}
