package styles.rgb;

import java.awt.Color;
import java.awt.Graphics2D;
import styles.Visual;

public class RGB implements Visual {
	
	private int repeat;
	private boolean hat, snare, kick, vertical;
	private Color[] colors;
	
	public RGB() {
		super();
		repeat = 4;
		hat = snare = kick = false;
		vertical = true;
		colors = new Color[3];
		colors[0] = Color.RED;
		colors[1] = Color.GREEN;
		colors[2] = Color.BLUE;
	}
	
	public void draw(Graphics2D g, int w, int h) {
		if (hat) {
			for (int i = 0; i < repeat * 3; i += 3) {
				g.setColor(colors[0]);
				if (vertical) {
					g.fillRect((int) ((double) w * i / (repeat * 3)), 0, (int) ((double) w / (repeat * 3)), h);
				} else {
					g.fillRect(0, (int) ((double) h * i / (repeat * 3)), w, (int) ((double) h / (repeat * 3)));
				}
			}
		}
		if (snare) {
			for (int i = 1; i < repeat * 3; i += 3) {
				g.setColor(colors[1]);
				if (vertical) {
					g.fillRect((int) ((double) w * i / (repeat * 3)), 0, (int) ((double) w / (repeat * 3)), h);
				} else {
					g.fillRect(0, (int) ((double) h * i / (repeat * 3)), w, (int) ((double) h / (repeat * 3)));
				}
			}
		}
		if (kick) {
			for (int i = 2; i < repeat * 3; i += 3) {
				g.setColor(colors[2]);
				if (vertical) {
					g.fillRect((int) ((double) w * i / (repeat * 3)), 0, (int) ((double) w / (repeat * 3)), h);
				} else {
					g.fillRect(0, (int) ((double) h * i / (repeat * 3)), w, (int) ((double) h / (repeat * 3)));
				}
			}
		}
	}
	
	public void step(int w, int h) {
		hat = false;
		snare = false;
		kick = false;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
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
		
	}

	@Override
	public void setSpeed(double speed) {
		
	}
}