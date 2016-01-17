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
	private int[][] alignments;
	private double speed;
	private boolean lightsOn, flicker, fillArc, drawBorder;
	private ArrayList<Point> points;
	private ArrayList<double[][]> style1Thetas;
	private Effect[] effects;
	
	public Beam() {
		super();
		maxBeams = 3;
		style = 2;
		alignments = new int[][]{{1},{2},{3},{4},{5},{6}};
		speed = 1;
		lightsOn = true;
		flicker = false;
		fillArc = true;
		drawBorder = true;
		points = new ArrayList<Point>();
		style1Thetas = new ArrayList<double[][]>();
		effects = new Effect[6];
		for (int i = 0; i < 6; i++) {
			effects[i] = new Clockwise();
		}
		step(0, 0);
	}
	
	public void draw(Graphics2D g, int w, int h) {
		for (int i = 0; i < points.size(); i++) {
			drawLights(g, (int)points.get(i).getX(), (int)points.get(i).getY(), (int)(12 + Math.random() * 4));
		}
		if (flicker ? lightsOn : true) {
			if (style == 1) {
				for (int i = 0; i < points.size(); i++) {
					int x = (int)points.get(i).getX(), y = (int)points.get(i).getY();
					if (style1Thetas.size() > 0) {
						for (int j = 0; j < style1Thetas.get(i).length; j++) {
							new Arc(style1Thetas.get(i)[j][0], style1Thetas.get(i)[j][1],
									new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)))
									.draw(g, x, y, (int)Math.sqrt(w * w + h * h), fillArc, drawBorder);
						}
					}
				}
			} else if (style == 2) {
				for (int i = 0; i < alignments.length; i++) {
					for (int j = 0; j < alignments[i].length; j++) {
						int x = (int)points.get(alignments[i][j] - 1).getX(), y = (int)points.get(alignments[i][j] - 1).getY();
						try {
							effects[i].draw(g, x, y, (int)Math.sqrt(w * w + h * h), fillArc, drawBorder);
						} catch (java.lang.IllegalArgumentException e) {}
					}
				}
			}
		}
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
					effects[i].step(speed);
				}
			}
		}
		lightsOn = !lightsOn;
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

	public void setFlicker(boolean flicker) {
		this.flicker = flicker;
	}

	public boolean getFillArc() {
		return fillArc;
	}

	public void setFillArc(boolean fillArc) {
		this.fillArc = fillArc;
	}

	public boolean getDrawBorder() {
		return drawBorder;
	}

	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder = drawBorder;
	}

	public void setAlignments(int[][] alignments) {
		this.alignments = new int[alignments.length][];
		for (int i = 0; i < alignments.length; i++) {
			this.alignments[i] = new int[alignments[i].length];
			for (int j = 0; j < alignments[i].length; j++) {
				this.alignments[i][j] = alignments[i][j];
			}
		}
	}

	@Override
	public void hat() {
		for (int i = 0; i < effects.length; i++) {
			if (Math.random() > 0.5) {
				effects[i].switchColor();
			}
		}
	}

	@Override
	public void snare() {
		for (int i = 0; i < effects.length; i++) {
			if (Math.random() > 0.5) {
				effects[i].switchDirection();
			}
		}
	}

	@Override
	public void kick() {
		for (int i = 0; i < effects.length; i++) {
			if (Math.random() > 0.5) {
				effects[i].switchDirection();
			}
		}
	}
	
	@Override
	public void freqBands(boolean[] freqBands) {
		
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}