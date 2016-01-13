package styles.dots;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import styles.Visual;

public class Dots implements Visual {
	
	private int side, border, nHor, nVert, chaseCurrX, chaseCurrY, chaseColorIndex, chaseSpeed;
	private Color currColor;
	private ArrayList<Color> chaseColors;
	private ArrayList<ArrayList<Dot>> dots;
	private boolean chase, horizontal, goingDown, chaseBackwards, rainbow;
	
	public Dots() {
		super();
		side = 80;
		border = 2;
		nHor = 0;
		nVert = 0;
		chaseColorIndex = -1;
		chaseSpeed = 1;
		currColor = Color.RED;
		chaseColors = generateChaseColors();
		dots = new ArrayList<ArrayList<Dot>>();
		chase = false;
		rainbow = false;
		restartChase();
	}
	
	public void draw(Graphics2D g, int w, int h) {
		nHor = w / side;
		nVert = h / side;
		int horSpace = (w - nHor * side) / 2, vertSpace = (h - nVert * side) / 2;
		try {
			for (int i = 0; i < nVert; i++) {
				for (int j = 0; j < nHor; j++) {
					dots.get(i).get(j).draw(g, horSpace + border + j * side, vertSpace + border + i * side,
							side - border * 2, side - border * 2);
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {}
	}
	
	public void step(int w, int h) {
		nHor = w / side;
		nVert = h / side;
		try {
			// resize vertically if necessary
			if (nVert < dots.size()) {
				for (int i = dots.size() - 1; i >= nVert; i--) {
					dots.remove(i);
				}
			} else if (nVert > dots.size()) {
				for (int i = 0; i < nVert; i++) {
					if (i >= dots.size()) {
						dots.add(new ArrayList<Dot>());
					}
					for (int j = dots.get(i).size(); j < nHor; j++) {
						dots.get(i).add(new Dot());
					}
				}
			}
			// resize horizontally if necessary
			if (nHor < dots.get(0).size()) {
				for (int i = 0; i < nVert; i++) {
					for (int j = dots.get(i).size() - 1; j >= nHor; j--) {
						dots.get(i).remove(j);
					}
				}
			} else if (nHor > dots.get(0).size()) {
				for (int i = 0; i < nVert; i++) {
					for (int j = dots.get(i).size(); j < nHor; j++) {
						dots.get(i).add(new Dot());
					}
				}
			}
			// move along the chaser if chase is true
			if (chase) {
				for (int i = 0; i < chaseSpeed; i++) {
					try {
						dots.get(chaseCurrY).set(chaseCurrX, chaseColorIndex == chaseColors.size() ?
								new Dot(randomColor()) : new Dot(chaseColors.get(chaseColorIndex)));
						if (chaseColorIndex == 0) {
							dots.get(chaseCurrY).get(chaseCurrX).setInRandomChaser(false);
						} else if (chaseColorIndex == chaseColors.size()) {
							dots.get(chaseCurrY).get(chaseCurrX).setInRandomChaser(true);
						}
					} catch (java.lang.IndexOutOfBoundsException e) {}
					moveChase();
				}
			}
			// step all the individual dots
			for (int i = 0; i < nVert; i++) {
				for (int j = 0; j < nHor; j++) {
					if (chase) {
						if ((chaseColorIndex == 0 || chaseColorIndex == chaseColors.size()) && dots.get(i).get(j).isInRandomChaser()) {
							dots.get(i).get(j).setColor(randomColor());
						}
					} else {
						dots.get(i).get(j).step();
						if (Math.random() < 0.8 / nHor / nVert) {
							dots.get(i).set(j, rainbow ? new Dot(nextRainbow(currColor)) : new Dot(currColor));
						}
					}
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {}
	}
	
	private void moveChase() {
		if (horizontal) {
			if (goingDown) { // horizontal, going down
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
						chaseBackwards = true;
						chaseCurrY++;
						if (chaseCurrY >= nVert) {
							restartChase();
						}
					}
				}
			} else { // horizontal, going up
				if (chaseBackwards) {
					chaseCurrX--;
					if (chaseCurrX < 0) {
						chaseCurrX = 0;
						chaseBackwards = false;
						chaseCurrY--;
						if (chaseCurrY < 0) {
							restartChase();
						}
					}
				} else {
					chaseCurrX++;
					if (chaseCurrX > nHor - 1) {
						chaseCurrX = nHor - 1;
						chaseBackwards = true;
						chaseCurrY--;
						if (chaseCurrY < 0) {
							restartChase();
						}
					}
				}
			}
		} else {
			if (goingDown) { // vertical, going right
				if (chaseBackwards) {
					chaseCurrY--;
					if (chaseCurrY < 0) {
						chaseCurrY = 0;
						chaseBackwards = false;
						chaseCurrX++;
						if (chaseCurrX >= nHor) {
							restartChase();
						}
					}
				} else {
					chaseCurrY++;
					if (chaseCurrY > nVert - 1) {
						chaseCurrY = nVert - 1;
						chaseBackwards = true;
						chaseCurrX++;
						if (chaseCurrX >= nHor) {
							restartChase();
						}
					}
				}
			} else { // vertical, going left
				if (chaseBackwards) {
					chaseCurrY--;
					if (chaseCurrY < 0) {
						chaseCurrY = 0;
						chaseBackwards = false;
						chaseCurrX--;
						if (chaseCurrX < 0) {
							restartChase();
						}
					}
				} else {
					chaseCurrY++;
					if (chaseCurrY > nVert - 1) {
						chaseCurrY = nVert - 1;
						chaseBackwards = true;
						chaseCurrX--;
						if (chaseCurrX < 0) {
							restartChase();
						}
					}
				}
			}
		}
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
		int s = 3, r = c.getRed(), g = c.getGreen(), b = c.getBlue();
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
		if (rainbow) {
			chase = false;
		}
	}
	
	public void toggleChase() {
		chase = !chase;
		if (chase) {
			rainbow = false;
			restartChase();
		}
	}
	
	private void restartChase() {
		horizontal = fiftyFifty();
		goingDown = fiftyFifty();
		chaseBackwards = fiftyFifty();
		if (horizontal) { // horizontal
			chaseCurrX = chaseBackwards ? nHor - 1 : 0; // upper right/left corner
			chaseCurrY = goingDown ? 0 : nVert - 1; // going down/up
		} else { // vertical
			chaseCurrY = chaseBackwards ? nVert - 1 : 0; // bottom/top left corner
			chaseCurrX = goingDown ? 0 : nHor - 1; // going right/left
		}
		chaseColorIndex++;
		if (chaseColorIndex > chaseColors.size()) {
			chaseColorIndex = 0;
			chaseColors = generateChaseColors();
		}
	}
	
	private ArrayList<Color> generateChaseColors() {
		int n = (int)(3 + Math.random() * 5);
		ArrayList<Color> colorList = new ArrayList<Color>();
		for (int i = 0; i < n; i++) {
			colorList.add(randomColor());
		}
		return colorList;
	}
	
	private boolean fiftyFifty() {
		return Math.random() < 0.5;
	}
	
	class Dot {
		
		private double opacity, opDecrSpeed;
		private Color c;
		private boolean inRandomChaser;
		
		public Dot() {
			opacity = 0;
			opDecrSpeed = 0.05;
			c = Color.BLACK;
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

	@Override
	public void hat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void snare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kick() {
		// TODO Auto-generated method stub
		
	}
}