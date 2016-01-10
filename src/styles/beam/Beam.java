package styles.beam;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;

import styles.Visual;
import styles.beam.effect.*;

public class Beam implements Visual {
	
	private int maxBeams, style;
	private boolean lightsOn;
	private ArrayList<Point> points;
	private ArrayList<double[][]> style1Thetas;
	private Effect[][] effects;
	
	public Beam() {
		super();
		maxBeams = 3;
		style = 2;
		lightsOn = true;
		points = new ArrayList<Point>();
		style1Thetas = new ArrayList<double[][]>();//new double[(int)Math.round(Math.random() * maxBeams)][2];
		effects = new Effect[1][6];
		for (int i = 0; i < 6; i++) {
			effects[0][i] = new Clockwise();
		}
	}
	
	public void draw(Graphics2D g, int w, int h) {
		for (int i = 0; i < points.size(); i++) {
			drawLights(g, (int)points.get(i).getX(), (int)points.get(i).getY(), (int)(12 + Math.random() * 4));
		}
		try {
		if (lightsOn) {
			for (int i = 0; i < points.size(); i++) {
				int x = (int)points.get(i).getX(), y = (int)points.get(i).getY();
				if (style == 1) {
					for (int j = 0; j < style1Thetas.get(i).length; j++) {
						new Arc(style1Thetas.get(i)[j][0], style1Thetas.get(i)[j][1],
								new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)))
								.draw(g, x, y, (int)Math.sqrt(w * w + h * h));
				}
				} else if (style == 2) {
					effects[0][i].draw(g, x, y, (int)Math.sqrt(w * w + h * h));
				}
			}
		}
		} catch (java.lang.IndexOutOfBoundsException e) {}
		g.dispose();
	}
	
	public void step(int w, int h) {
		int xBorder = 150, yBorder = 200;
		points.clear();
		points.add(new Point(xBorder,     yBorder));
		points.add(new Point(w / 2,       yBorder));
		points.add(new Point(w - xBorder, yBorder));
		points.add(new Point(xBorder,     h - yBorder));
		points.add(new Point(w / 2,       h - yBorder));
		points.add(new Point(w - xBorder, h - yBorder));
		lightsOn = !lightsOn;
		
		if (lightsOn) {
			for (int i = 0; i < points.size(); i++) {
				if (style == 1) {
					double[][] thetas = new double[(int)Math.round(Math.random() * maxBeams)][2];
					for (int j = 0; j < thetas.length; j++) {
						thetas[j][0] = j > 0 ? thetas[j - 1][1] : Math.PI * 2 * Math.random();
						thetas[j][1] = thetas[j][0] + 0.1 + Math.PI / 8 * Math.random();
					}
					if (style1Thetas.size() <= i) {
						style1Thetas.add(thetas);
					} else {
						style1Thetas.set(i, thetas);
					}
				} else if (style == 2) {
					effects[0][i].step();
				}
			}
		}
	}

	private void drawLights(Graphics2D g, int x, int y, int cRad) {
		RadialGradientPaint light = new RadialGradientPaint(
				new Point(x, y),
				cRad,
				new float[]{0.5f, 1f},
				new Color[]{Color.WHITE, Color.BLACK});
		g.setColor(Color.WHITE);
		g.setPaint(light);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)1));
		g.fillOval(x - cRad, y - cRad, cRad * 2, cRad * 2);
	}
	
	public int getMaxBeams() {
		return maxBeams;
	}

	public void setMaxBeams(int n) {
		maxBeams = n;
	}
	
	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
}