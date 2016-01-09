package styles.dots;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import styles.Visual;

public class Dots implements Visual {
	
	private int side, border, nHor, nVert, chaseCurrX, chaseCurrY, chaseColorIndex, chaseSpeed;
	private Color currColor;
	private Color[] chaseColors;
	private ArrayList<ArrayList<Dot>> dots;
	private boolean chase, chaseBackwards, rainbow;
	
	public Dots() {
		super();
		side = 80;
		border = 2;
		nHor = 0;
		nVert = 0;
		chaseCurrX = 0;
		chaseCurrY = 0;
		chaseColorIndex = -1;
		chaseSpeed = 1;
		currColor = Color.RED;
		chaseColors = new Color[]{Color.RED, Color.GREEN, Color.BLUE};
		dots = new ArrayList<ArrayList<Dot>>();
		chase = false;
		chaseBackwards = true;
		rainbow = false;
	}
	
	public void draw(Graphics2D g, int w, int h) {
		nHor = w / side;
		nVert = h / side;
		int horSpace = (w - nHor * side) / 2, vertSpace = (h - nVert * side) / 2;
		try {
			for (int i = 0; i < nVert; i++) {
				for (int j = 0; j < nHor; j++) {
					dots.get(i).get(j).draw(g, horSpace + border + j * side, vertSpace + border + i * side, side - border * 2, side - border * 2);
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {}
	}
	
	public void step(int w, int h) {
		try {
			// resize vertically if necessary
			if (nVert < dots.size()) {
				for (int i = dots.size() - 1; i > nVert; i--) {
					dots.remove(i);
				}
			} else if (nVert > dots.size()) {
				for (int i = 0; i < nVert; i++) {
					if (i >= dots.size()) {
						dots.add(new ArrayList<Dot>());
					}
					for (int j = dots.get(i).size(); j < nHor; j++) {
						dots.get(i).add(rainbow ? new Dot(nextRainbow(currColor)) : (chase ? new Dot(Color.BLACK) : new Dot(Color.RED)));
					}
				}
			}
			// resize horizontally if necessary
			if (nHor < dots.get(0).size()) {
				for (int i = dots.size(); i < nVert; i++) {
					for (int j = dots.get(i).size() - 1; j > nHor; j--) {
						dots.remove(j);
					}
				}
			} else if (nHor > dots.get(0).size()) {
				for (int i = 0; i < nVert; i++) {
					for (int j = dots.get(i).size(); j < nHor; j++) {
						dots.get(i).add(rainbow ? new Dot(nextRainbow(currColor)) : (chase ? new Dot(Color.BLACK) : new Dot(Color.RED)));
					}
				}
			}
			// move along the chaser if chase is true
			if (chase) {
				for (int i = 0; i < chaseSpeed; i++) {
					try {
						dots.get(chaseCurrY).set(chaseCurrX, chaseColorIndex == chaseColors.length ? new Dot(randomColor()) : new Dot(chaseColors[chaseColorIndex]));
						if (chaseColorIndex == 0) {
							dots.get(chaseCurrY).get(chaseCurrX).setInRandomChaser(false);
						} else if (chaseColorIndex == chaseColors.length) {
							dots.get(chaseCurrY).get(chaseCurrX).setInRandomChaser(true);
						}
					} catch (java.lang.IndexOutOfBoundsException e) {}
					if (chaseBackwards) {
						chaseCurrX--;
						if (chaseCurrX < 0) {
							chaseCurrX = 0;
							chaseBackwards = false;
							chaseCurrY++;
							if (chaseCurrY >= nVert) {
								restartChase();
							}
						}
					} else {
						chaseCurrX++;
						if (chaseCurrX > nHor - 1) {
							chaseCurrX = nHor - 1;
							chaseCurrY++;
							chaseBackwards = true;
							if (chaseCurrY >= nVert) {
								restartChase();
							}
						}
					}
				}
			}
			// step all the individual dots
			for (int i = 0; i < nVert; i++) {
				for (int j = 0; j < nHor; j++) {
					if (chase) {
						if ((chaseColorIndex == 0 || chaseColorIndex == chaseColors.length) && dots.get(i).get(j).isInRandomChaser()) {
							dots.get(i).get(j).setColor(randomColor());
						}
					} else {
						dots.get(i).get(j).step();
						if (Math.random() < 0.8 / nHor / nVert) {
							dots.get(i).set(j, rainbow ? new Dot(nextRainbow(currColor)) : (chase ? new Dot(Color.BLACK) : new Dot(Color.RED)));
						}
					}
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {}
	}
	
	public int getSide() {
		return side;
	}
	
	public void setSide(int side) {
		this.side = side;
	}
	
	public int getBorder() {
		return border;
	}
	
	public void setBorder(int border) {
		this.border = border;
	}
	
	public int getChaseSpeed() {
		return chaseSpeed;
	}

	public void setChaseSpeed(int chaseSpeed) {
		this.chaseSpeed = chaseSpeed;
	}

	private Color nextRainbow(Color c) {
		// 255	0	0
		// 255	128	0
		// 255	255	0
		// 128	255	0
		// 0	255	0
		// 0	255	128
		// 0	255	255
		// 0	128	255
		// 0	0	255
		// 128	0	255
		// 255	0	255
		// 255	0	128
		
		// g goes up, r goes down, b goes up, g goes down, r goes up, b goes down
		int s = 1, r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		if (r == 255 && g < 255 && b == 0) {
			g = g + s > 255 ? 255 : g + s;
		} else if (r > 0 && g == 255 && b == 0) {
			r = r - s < 0 ? 0 : r - s;
		} else if (r == 0 && g == 255 && b < 255) {
			b = b + s > 255 ? 255 : b + s;
		} else if (r == 0 && g > 0 && b == 255) {
			g = g - s < 0 ? 0 : g - s;
		} else if (r < 255 && g == 0 && b == 255) {
			r = r + s > 255 ? 255 : r + s;
		} else if (r == 255 && g == 0 && b > 0) {
			b = b - s < 0 ? 0 : b - s;
		}
		currColor = new Color(r, g, b);
		return currColor;
	}
	
	private Color randomColor() {
		return new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
	}
	
	public void toggleRainbow() {
		rainbow = !rainbow;
	}
	
	public void toggleChase() {
		chase = !chase;
		if (chase) {
			restartChase();
		}
	}
	
	private void restartChase() {
		chaseBackwards = true;
		chaseCurrX = nHor - 1;
		chaseCurrY = 0;
		chaseColorIndex++;
		if (chaseColorIndex > chaseColors.length) {
			chaseColorIndex = 0;
		}
	}
	
	class Dot {
		
		private double opacity, opDecrSpeed;
		private Color c;
		private boolean inRandomChaser;
		
		public Dot() {
			opacity = 0;
			opDecrSpeed = 0.05;
			c = Color.RED;
			inRandomChaser = false;
		}
		
		public Dot(Color c) {
			opacity = 1;
			opDecrSpeed = 0.05;
			this.c = c;
			inRandomChaser = false;
		}
		
		public void draw(Graphics2D g, int x, int y, int w, int h) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity));
			g.setColor(c);
			g.fillOval(x, y, w, h);
		}
		
		public void step() {
			opacity = opacity - opDecrSpeed <= 0 ? 0 : opacity - opDecrSpeed;
		}
		
		public void setColor(Color c) {
			this.c = c;
		}

		public boolean isInRandomChaser() {
			return inRandomChaser;
		}

		public void setInRandomChaser(boolean inRandomChaser) {
			this.inRandomChaser = inRandomChaser;
		}
	}
}