package styles.rgb;

import java.awt.Color;
import java.awt.Graphics2D;
import styles.Visual;

public class RGB implements Visual {
	
	public final int NORMAL_MODE = 0, RAINBOW_MODE = 1, RANDOM_MODE = 2;
	private double speed, mode;
	private boolean hat, snare, kick;
	private Color[] colors;
	
	public RGB() {
		super();
		speed = 1.0;
		hat = snare = kick = false;
		colors = new Color[3];
		colors[0] = Color.RED;
		colors[1] = Color.GREEN;
		colors[2] = Color.BLUE;
	}
	
	public void draw(Graphics2D g, int w, int h) {
		if (hat) {
			g.setColor(Color.red);
			g.fillRect(0, 0, w / 3, h);
			
		}
		if (snare) {
			g.setColor(Color.green);
			g.fillRect(w / 3, 0, w / 3, h);
			
		}
		if (kick) {
			g.setColor(Color.blue);
			g.fillRect(2 * w / 3, 0, w / 3, h);
		}
	}
	
	public void step(int w, int h) {
		if (hat || snare || kick) {
			for (int i = 0; i < colors.length; i++) {
				colors[i] = nextRainbow(colors[i]);
			}
		}
		hat = false;
		snare = false;
		kick = false;
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
		return new Color(r, g, b);
	}
	
	private Color randomColor() {
		return new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
	}

	@Override
	public void hat() {
		hat = true;
	}

	@Override
	public void snare() {
		snare = true;
	}

	@Override
	public void kick() {
		kick = true;
	}
	
	@Override
	public void freqBands(boolean[] freqBands) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}