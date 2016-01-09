package styles.spinner;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import styles.Visual;

public class Spinner implements Visual {
	
	private int layers, rotation, rotationSpeed, x, y, minVel, maxVel, xVel, yVel, radius, movingCount;
	private int count;
	private boolean moving;
	private Color c1, c2;
	
	public Spinner() {
		layers = 4;
		rotation = 0;
		rotationSpeed = 3;
		x = 400;
		y = 400;
		minVel = 10;
		maxVel = 25;
		xVel = (int)(minVel + Math.random() * (maxVel - minVel));
		yVel = (int)(minVel + Math.random() * (maxVel - minVel));
		radius = 25;
		movingCount = movingCount();
		count = 0;
		moving = false;
		c1 = Color.RED;
		c2 = Color.BLUE;
	}

	public void draw(Graphics2D g, int w, int h) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.7));
		for (int i = 0; i < layers; i++) {
			int n = i * 4 + 1;
			for (int j = 0; j < n; j++) {
				g.setColor(i % 2 == 0 ? c1 : c2);
				g.fillOval(x + (int)(i * (radius * 2.5 - 1) * Math.cos((double)j / n * Math.PI * 2 +
								(i % 2 == 0 ? rotation : -rotation) * Math.PI / 180)) - radius,
						y + (int)(i * (radius * 2.5 - 1) * Math.sin((double)j / n * Math.PI * 2 +
								(i % 2 == 0 ? rotation : -rotation) * Math.PI / 180)) - radius,
						radius * 2, radius * 2);
			}
		}		
	}
	
	public void step(int w, int h) {
		count++;
		minVel = (w + h) / 180;
		maxVel = (w + h) / 80;
		if (count % 100 == 0) {
			c1 = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
			c2 = new Color(c1.getRed() + 128 > 255 ? c1.getRed() - 128 : c1.getRed() + 128,
					c1.getGreen() + 128 > 255 ? c1.getGreen() - 128 : c1.getGreen() + 128,
					c1.getBlue() + 128 > 255 ? c1.getBlue() - 128 : c1.getBlue() + 128);
			count = 0;
		}
		rotation += rotationSpeed;
		rotation %= 360;
		movingCount--;
		if (moving) {
			x += xVel;
			y += yVel;
			int border = -(int)Math.round((layers - 1) * (radius * 2.5 - 1) / 2);
			if (movingCount <= 0 ||
					x + maxVel + (layers - 1) * (radius * 2.5 - 1) + border > w ||
					x - maxVel - (layers - 1) * (radius * 2.5 - 1) < border ||
					y + maxVel + (layers - 1) * (radius * 2.5 - 1) + border > h ||
					y - maxVel - (layers - 1) * (radius * 2.5 - 1) < border) {
				moving = false;
				movingCount = movingCount();
			}
		} else {
			if (movingCount <= 0) {
				moving = true;
				double chance1 = Math.random();//, chance2 = 0.7;
				if (chance1 < 1./5) {
					xVel = x < w / 2 ? (int)(minVel + Math.random() * (maxVel - minVel)) :
						-(int)(minVel + Math.random() * (maxVel - minVel));
					yVel = y < h / 2 ? (int)Math.round(Math.random() * 2) : -(int)Math.round(Math.random() * 2);
				} else if (chance1 < 2./5) {
					xVel = x < w / 2 ? (int)Math.round(Math.random() * 2) : -(int)Math.round(Math.random() * 2);
					yVel = y < h / 2 ? (int)(minVel + Math.random() * (maxVel - minVel)) :
						-(int)(minVel + Math.random() * (maxVel - minVel));
				} else {
					xVel = x < w / 2 ? (int)(minVel + Math.random() * (maxVel - minVel)) :
						-(int)(minVel + Math.random() * (maxVel - minVel));
					yVel = y < h / 2 ? (int)(minVel + Math.random() * (maxVel - minVel)) :
						-(int)(minVel + Math.random() * (maxVel - minVel));
				}
				movingCount = movingCount();
			}
			
		}
	}
	
	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	private int movingCount() {
		return moving ? (int)(20 + Math.random() * 10) : (int)(8 + Math.random() * 8);
	}
}