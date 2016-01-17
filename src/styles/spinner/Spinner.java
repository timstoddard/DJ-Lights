package styles.spinner;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import styles.Visual;

public class Spinner implements Visual {
	
	private int layers, minVel, maxVel, radius;
	private double rotation, rotationSpeed, x, y,  xVel, yVel, speed;
	private boolean moving, posRotation, kick;
	private Color c1, c2;
	
	public Spinner() {
		layers = 3;
		minVel = 10;
		maxVel = 25;
		radius = 25;
		rotation = 0;
		rotationSpeed = 3;
		x = y = 400;
		xVel = minVel + Math.random() * (maxVel - minVel);
		yVel = minVel + Math.random() * (maxVel - minVel);
		speed = 1;
		moving = false;
		posRotation = true;
		c1 = Color.RED;
		c2 = Color.BLUE;
	}

	public void draw(Graphics2D g, int w, int h) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.7));
		for (int i = 0; i < layers; i++) {
			int n = i * 5 + 1;
			for (int j = 0; j < n; j++) {
				g.setColor(i % 2 == 0 ? c1 : c2);
				g.fillOval((int)(x + i * (radius * 3 - 1) * Math.cos((double)j / n * Math.PI * 2 +
								(i % 2 == 0 ? rotation : -rotation) * Math.PI / 180)) - radius,
						(int)(y + i * (radius * 3 - 1) * Math.sin((double)j / n * Math.PI * 2 +
								(i % 2 == 0 ? rotation : -rotation) * Math.PI / 180)) - radius,
						radius * 2, radius * 2);
			}
		}		
	}
	
	public void step(int w, int h) {
		minVel = (w + h) / 180;
		maxVel = (w + h) / 80;
		rotation += posRotation ? rotationSpeed * speed : rotationSpeed * -speed;
		rotation %= 360;
		if (moving) {
			x += xVel * speed;
			y += yVel * speed;
			int border = 10;
			if (xVel > 0 && x + maxVel + (layers - 1) * (radius * 3 - 1) + border > w ||
					xVel < 0 && x - maxVel - (layers - 1) * (radius * 3 - 1) < border ||
					yVel > 0 && y + maxVel + (layers - 1) * (radius * 3 - 1) + border > h ||
					yVel < 0 && y - maxVel - (layers - 1) * (radius * 3 - 1) < border) {
				moving = false;
			}
		}
		if (kick) {
			double chance1 = Math.random();
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
			moving = true;
			kick = false;
		}
	}
	
	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public double getRotationSpeed() {
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

	@Override
	public void hat() {
		
	}

	@Override
	public void snare() {
		posRotation = !posRotation;
	}

	@Override
	public void kick() {
		kick = true;
		c1 = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
		c2 = new Color(c1.getRed() + 128 > 255 ? c1.getRed() - 128 : c1.getRed() + 128,
				c1.getGreen() + 128 > 255 ? c1.getGreen() - 128 : c1.getGreen() + 128,
				c1.getBlue() + 128 > 255 ? c1.getBlue() - 128 : c1.getBlue() + 128);
	}
	
	@Override
	public void freqBands(boolean[] freqBands) {
		
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}