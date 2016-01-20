package styles.blades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import styles.Visual;

public class Blades implements Visual {
	
	private boolean hat, snare, kick;
	private boolean[] freqBands;
	private Color[] colors;
	private Color currColor;
	
	public Blades() {
		super();
		hat = snare = kick = false;
		freqBands = new boolean[27];
		colors = new Color[]{Color.GREEN, Color.BLUE, Color.RED, Color.WHITE};
		currColor = colors[0];
	}
	
	public void draw(Graphics2D g, int w, int h) {
		Graphics2D g2 = (Graphics2D) g;
		if (snare) {
			g.setColor(colors[0]);
		} else if (kick) {
			g.setColor(colors[1]);
		} else if (hat) {
			g.setColor(colors[2]);
		} else {
			g.setColor(colors[3]);
		}
		for (int i = 0; i < freqBands.length; i++) {
			if (freqBands[i]) {
				Polygon poly = new Polygon(new int[]{w / 2 - 10, w / 2, w / 2 + 15, w / 2 + 25},
						                   new int[]{h / 4 + 50, 0, 0, h / 6 + 50}, 4);
				g2.fillPolygon(poly);
			}
			g2.rotate(360 / freqBands.length, w / 2, h / 2);
		}
		g.setColor(currColor);
		int size = h < w ? h / 20 : w / 20;
		g2.fillOval(w / 2 - size, h / 2 - size, size * 2, size * 2);
	}
	
	public void step(int w, int h) {
		hat = snare = kick = false;
		freqBands = new boolean[27];
		currColor = colors[(int)(Math.random() * colors.length)];
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
		this.freqBands = freqBands;
	}

	@Override
	public void setSpeed(double speed) {
		
	}
}