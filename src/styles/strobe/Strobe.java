package styles.strobe;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import styles.Visual;

public class Strobe implements Visual {
	
	private int strobeCount;
	private double speed;
	private boolean on;
	private Color c;
	
	public Strobe() {
		c = Color.WHITE;
	}
	
	public void draw(Graphics2D g, int w, int h) {
		if (on /*&& strobeCount % 2 == 0*/) {
			g.setColor(c);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)1));
			g.fillRect(0, 0, w, h);
		}
	}
	
	public void step(int w, int h) {
		//strobeCount--;
		//strobeCount = strobeCount < 0 ? 0 : strobeCount;
		on = false;
	}

	@Override
	public void hat() {
		on = true;
		strobeCount = 6;
	}

	@Override
	public void snare() {
		on = true;
		strobeCount = 6;
	}

	@Override
	public void kick() {
		on = true;
		strobeCount = 6;
	}
	
	@Override
	public void freqBands(boolean[] freqBands) {
		
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}