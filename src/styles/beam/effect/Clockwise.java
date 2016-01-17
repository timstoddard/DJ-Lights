package styles.beam.effect;

import java.awt.Color;
import java.awt.Graphics2D;

import styles.beam.Arc;

public class Clockwise extends Effect {
	
	private int vel, dir;
	private Color c1, c2;
	
	public Clockwise() {
		super();
		vel = 8;
		dir = 1;
		c1 = Color.RED;
		c2 = Color.GREEN;
		addArc(new Arc(0, Math.PI / 8, c1));
	}

	public void draw(Graphics2D g, int x, int y, int maxRadius, boolean fillArc, boolean drawBorder) {
		for (Arc a : getBeams()) {
			a.draw(g, x, y, maxRadius, fillArc, drawBorder);
		}
	}

	public void step(double speed) {
		for (Arc a : getBeams()) {
			a.setThetas(new double[]{a.getTheta1() + Math.PI / 180 * vel * speed * dir, a.getTheta2() + Math.PI / 180 * vel * speed * dir});
		}
	}
	
	public void switchDirection() {
		dir *= -1;
	}
	
	public void switchColor() {
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
}