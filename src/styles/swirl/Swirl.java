package styles.swirl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import styles.Visual;

public class Swirl implements Visual {
	
	private int currColorIndex;
	private double speed, currInitDirection;
	private boolean adding;
	private ArrayList<ArrayList<Point>> points;
	private final Color[] colors = new Color[]{Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW};
	
	public Swirl() {
		currColorIndex = 0;
		speed = 1;
		currInitDirection = newDirection();
		adding = false;
		points = new ArrayList<ArrayList<Point>>();
	}
	
	public void draw(Graphics2D g, int w, int h) {
		g.setColor(colors[currColorIndex]);
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).size() > 1) {
				for (int j = 0; j < points.get(i).size() - 1; j++) {
					g.drawLine((int) points.get(i).get(j).getX1(), (int) points.get(i).get(j).getY1(),
							(int) points.get(i).get(j + 1).getX1(), (int) points.get(i).get(j + 1).getY1());
					g.drawLine((int) points.get(i).get(j).getX2(), (int) points.get(i).get(j).getY2(),
							(int) points.get(i).get(j + 1).getX2(), (int) points.get(i).get(j + 1).getY2());
				}
			}
		}
	}
	
	public void step(int w, int h) {
		if (points.size() > 0) {
			for (int i = 0; i < points.size(); i++) {
				if (points.get(i).size() > 0) {
					for (int j = 0; j < points.get(i).size(); j++) {
						points.get(i).get(j).step(speed);
						if ((points.get(i).get(j).getX1() < 0 || points.get(i).get(j).getX1() > w ||
								points.get(i).get(j).getY1() < 0 || points.get(i).get(j).getY1() > h)) {
							if (points.get(i).size() == 1) {
								points.remove(i);
								break;
							} else {
								points.get(i).remove(j);
							}
						}
					}
				}
			}
		}
		if (adding) {
			points.get(points.size() - 1).add(new Point(w / 2., h / 2., currInitDirection));
		}
	}
	
	private double newDirection() {
		return Math.random() * 360;
	}

	@Override
	public void hat() {
		adding = !adding;
		if (adding) {
			currInitDirection = newDirection();
			points.add(new ArrayList<Point>());
		}
		int oldColorIndex = currColorIndex;
		while (currColorIndex == oldColorIndex) {
			currColorIndex = (int) (Math.random() * colors.length);
		}
	}

	@Override
	public void snare() {
		adding = !adding;
		if (adding) {
			currInitDirection = newDirection();
			points.add(new ArrayList<Point>());
		}
		int oldColorIndex = currColorIndex;
		while (currColorIndex == oldColorIndex) {
			currColorIndex = (int) (Math.random() * colors.length);
		}
	}

	@Override
	public void kick() {
		adding = !adding;
		if (adding) {
			currInitDirection = newDirection();
			points.add(new ArrayList<Point>());
		}
		int oldColorIndex = currColorIndex;
		while (currColorIndex == oldColorIndex) {
			currColorIndex = (int) (Math.random() * colors.length);
		}
	}
	
	@Override
	public void freqBands(boolean[] freqBands) {
		
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
		if (speed != 1) {
			adding = false;
		}
	}
	
	class Point {
		
		private int steps;
		private double x1, y1, x2, y2, initX, initY, direction;
		
		public Point(double x, double y, double initialDirection) {
			steps = 0;
			x1 = x2 = initX = x;
			y1 = y2 = initY = y;
			direction = initialDirection;
		}
		
		public double getX1() {
			return x1;
		}
		
		public double getY1() {
			return y1;
		}
		
		public double getX2() {
			return x2;
		}
		
		public double getY2() {
			return y2;
		}
		
		public void step(double speed) {
			steps += 5;
			direction += 2 * speed;
			x1 = initX + steps * Math.cos(direction * Math.PI / 180);
			y1 = initY + steps * Math.sin(direction * Math.PI / 180);
			x2 = initX - steps * Math.cos(direction * Math.PI / 180);
			y2 = initY - steps * Math.sin(direction * Math.PI / 180);
		}
	}
}