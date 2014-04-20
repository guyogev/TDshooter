package com.guyyo.gdxGame.model;

import java.util.Hashtable;

public class SkeletonMage extends Enemy {
	public SkeletonMage() {
		animHash = new Hashtable<Enemy.AnimDirection, Integer>();
		animHash.put(AnimDirection.W, 0);
		animHash.put(AnimDirection.NW, 1);
		animHash.put(AnimDirection.N, 2);
		animHash.put(AnimDirection.NE, 3);
		animHash.put(AnimDirection.E, 4);
		animHash.put(AnimDirection.SE, 5);
		animHash.put(AnimDirection.S, 6);
		animHash.put(AnimDirection.SW, 7);
		loadTexture(Assets.skeletonMage, 8, 28);
		initParams();
		
		state = STATE.SPAWN;
		minSpeed = 2;
		animDirection = AnimDirection.N;
		animBehavior = AnimBehavior.WALK;

		walk();
	}
}
