package styles.beam.effect;

import java.awt.Color;
import java.awt.Graphics2D;

import styles.beam.Arc;

public class Clockwise extends Effect {
	
	private int vel, dir, dirCount, colorCount;
	private Color c1, c2;
	
	public Clockwise() {
		super();
		vel = 6;
		dir = 1;
		c1 = Color.RED;
		c2 = Color.GREEN;
		addArc(new Arc(0, Math.PI / 8, c1));
	}

	public void draw(Graphics2D g, int x, int y, int maxRadius) {
		for (Arc a : getBeams()) {
			a.draw(g, x, y, maxRadius);
		}
	}

	public void step() {
		for (Arc a : getBeams()) {
			a.setThetas(new double[]{a.getTheta1() + Math.PI / 180 * vel * dir, a.getTheta2() + Math.PI / 180 * vel * dir});
		}
		if (dirCount <= 0) {
			dirCount = dirCount();
			//switchDirection();
		}
		dirCount--;
		if (colorCount <= 0) {
			colorCount = colorCount();
			switchColor();
		}
		colorCount--;
	}
	
	public void switchDirection() {
		dir *= -1;
	}
	
	private void switchColor() {
		for (Arc a : getBeams()) {
			if (a.getColor().equals(c1)) {
				a.setColor(c2);
			} else {
				a.setColor(c1);
			}
		}
	}
	
	public int getVel() {
		return vel;
	}
	
	public void setVel(int vel) {
		this.vel = vel;
	}
	
	private int dirCount() {
		return (int)(50 + Math.random() * 20);
	}
	
	private int colorCount() {
		return (int)(20 + Math.random() * 15);
	}
}