package styles.strobe;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import styles.Visual;

public class Strobe implements Visual {
	
	private int strobeCount, minStrobeOn, maxStrobeOn, minStrobeWait, maxStrobeWait;
	private boolean on;
	private Color c;
	
	public Strobe() {
		c = Color.WHITE;
		minStrobeOn = 25;
		maxStrobeOn = 40;
		minStrobeWait = 40;
		maxStrobeWait = 60;
	}
	
	public void draw(Graphics2D g, int w, int h) {
		if (on && strobeCount % 2 == 0) {
			g.setColor(c);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)1));
			g.fillRect(0, 0, w, h);
		}
	}
	
	public void step(int w, int h) {
		strobeCount--;
		if (on) {
			if (strobeCount <= 0) {
				on = false;
				strobeCount = strobeCount();
			}
		} else {
			if (strobeCount <= 0) {
				on = true;
				strobeCount = strobeCount();
			}
		}
	}
	
	public void restart() {
		on = true;
		strobeCount = strobeCount();
	}
	
	public int getMinStrobeOn() {
		return minStrobeOn;
	}

	public void setMinStrobeOn(int minStrobeOn) {
		this.minStrobeOn = minStrobeOn;
	}

	public int getMaxStrobeOn() {
		return maxStrobeOn;
	}

	public void setMaxStrobeOn(int maxStrobeOn) {
		this.maxStrobeOn = maxStrobeOn;
	}

	public int getMinStrobeWait() {
		return minStrobeWait;
	}

	public void setMinStrobeWait(int minStrobeWait) {
		this.minStrobeWait = minStrobeWait;
	}

	public int getMaxStrobeWait() {
		return maxStrobeWait;
	}

	public void setMaxStrobeWait(int maxStrobeWait) {
		this.maxStrobeWait = maxStrobeWait;
	}

	private int strobeCount() {
		return on ? (int)(minStrobeOn + Math.random() * (maxStrobeOn - minStrobeOn)) : (int)(minStrobeWait + Math.random() * (maxStrobeWait - minStrobeWait));
	}
}