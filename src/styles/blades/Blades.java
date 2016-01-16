package styles.blades;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import styles.Visual;

public class Blades implements Visual {
	
	private boolean hat, snare, kick;
	private boolean[] freqBands;
	
	public Blades() {
		super();
		hat = snare = kick = false;
		freqBands = new boolean[27];
	}
	
	public void draw(Graphics2D g, int w, int h) {
		Graphics2D g2 = (Graphics2D) g;
		if (snare) {
			g.setColor(Color.green);
		} else if (kick) {
			g.setColor(Color.blue);
		} else if (hat) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.white);
		}
		int b = 50;
		for (int i = 0; i < freqBands.length; i++) {
			if (freqBands[i]) {
				Polygon poly = new Polygon(new int[]{w / 2 - 10, w / 2, w / 2 + 15, w / 2 + 25},
						                   new int[]{b + h / 4, 0, 0, b + h / 6}, 4);
				g2.fillPolygon(poly);
			}
			g2.rotate(360 / freqBands.length, w / 2, h / 2);
		}
		g2.fillOval(w / 2 - b, h / 2 - b, b * 2, b * 2);
	}
	
	public void step(int w, int h) {
		hat = snare = kick = false;
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
		// TODO Auto-generated method stub
		
	}
}