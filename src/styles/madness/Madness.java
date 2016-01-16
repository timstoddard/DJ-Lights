package styles.madness;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import styles.Visual;

public class Madness extends JPanel implements Visual {
	
	private int n, border, corners, count;
	private double speed;
	private boolean up, down, left, right, color, randomize;
	private Color[] c, c2;
	
	public Madness() {
		super();
		n = 50;
		border = 0;
		corners = 10;
		count = 0;
		up = !true;
		down = !true;
		left = !true;
		right = !true;
		color = true;
		randomize = true;
		// visual ecstasy
		c = new Color[] {
				Color.red, Color.red,
				Color.green, Color.green,
				Color.blue, Color.blue};
		c2 = new Color[] {
				Color.white, Color.white, Color.white,
				Color.black, Color.black, Color.black};
	}
	
	public void draw(Graphics2D g, int w, int h) {
		if (up) {
			if (left) { // state = up + left
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						drawRect(g, (i + j + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			} else if (right) { // state = up + right
				for (int i = n - 1; i >= 0; i--) {
					for (int j = 0; j < n; j++) {
						drawRect(g, (n - i + j + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			} else { // state = up
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						drawRect(g, (j + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			}
		} else if (down) {
			if (left) { // state = down + left
				for (int i = 0; i < n; i++) {
					for (int j = n - 1; j >= 0; j--) {
						drawRect(g, (i + n - j + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			} else if (right) { // state = down + right
				for (int i = n - 1; i >= 0; i--) {
					for (int j = n - 1; j >= 0; j--) {
						drawRect(g, (n - i + n - j + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			} else { // state = down
				for (int i = 0; i < n; i++) {
					for (int j = n - 1; j >= 0; j--) {
						drawRect(g, (n - j + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			}
		} else {
			if (left) { // state = left
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						drawRect(g, (i + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			} else if (right) { // state = right
				for (int i = n - 1; i >= 0; i--) {
					for (int j = 0; j < n; j++) {
						drawRect(g, (n - i + count) % c.length, (int) (i * (double) w / n) + border,
								(int) (j * (double) h / n) + border, (int) ((double) w / n) - border * 2,
								(int) ((double) h / n) - border * 2, corners);
					}
				}
			} else { // state = center
				for (int i = n - 1; i >= 0; i--) {
					for (int j = n - 1; j >= 0; j--) {
						drawRect(g,
								(Math.abs(n / 2 - (i)) + Math.abs(n / 2 - (n - j)) + c.length - 1 - count) % c.length,
								(int) (i * (double) w / n) + border, (int) (j * (double) h / n) + border,
								(int) ((double) w / n) - border * 2, (int) ((double) h / n) - border * 2, corners);
					}
				}
			}
		}
	}
	
	public void step(int w, int h) {
		count++;
		count %= c.length;
		color = !color;
	}
	
	private void drawRect(Graphics2D g, int i, int x, int y, int w, int h, int corner) {
		if (color) {
			g.setColor(c[i]);
		} else {
			g.setColor(c2[i]);
		}
		g.fillRoundRect(x, y, w, h, corner, corner);
	}
	
	public void updateN(int n) {
		this.n = n;
	}
	
	public void updateBorder(int b) {
		border = b;
	}
	
	public void updateCorners(int c) {
		corners = c;
	}
	
	public void updateRandomize(boolean random) {
		randomize = random;
	}
	
	/**
	 * Sets the direction based on the four booleans. Note: there should be no more
	 * than 2 booleans true at any one time. If both up/down or left/right are true,
	 * they will both be set to false.
	 * @param up - sets whether the effect will move up or not
	 * @param down - sets whether the effect will move down or not
	 * @param left - sets whether the effect will move left or not
	 * @param right - sets whether the effect will move right or not
	 */
	public void setDirection(boolean up, boolean down, boolean left, boolean right) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		if (up && down) {
			this.up = false;
			this.down = false;
		}
		if (right && left) {
			this.right = false;
			this.left = false;
		}
	}
	
	public int getN() {
		return n;
	}
	
	public int getBorderSize() {
		return border;
	}
	
	public int getCornerSize() {
		return corners;
	}
	
	public boolean[] getDirection() {
		return new boolean[]{up, down, left, right};
	}
	
	private void newDirection() {
		int choice = (int)(Math.random() * 9);
		if (choice == 0) {
			setDirection(true, false, true, false);
		} else if (choice == 1) {
			setDirection(false, false, true, false);
		} else if (choice == 2) {
			setDirection(false, true, true, false);
		} else if (choice == 3) {
			setDirection(true, false, false, false);
		} else if (choice == 4) {
			setDirection(false, false, false, false);
		} else if (choice == 5) {
			setDirection(false, true, false, false);
		} else if (choice == 6) {
			setDirection(true, false, false, true);
		} else if (choice == 7) {
			setDirection(false, false, false, true);
		} else if (choice == 8) {
			setDirection(false, true, false, true);
		}
	}

	@Override
	public void hat() {
		
	}

	@Override
	public void snare() {
		
	}

	@Override
	public void kick() {
		if (randomize) {
			newDirection();
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