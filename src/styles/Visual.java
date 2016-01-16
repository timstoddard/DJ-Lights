package styles;

import java.awt.Graphics2D;

public interface Visual {
	
	public void draw(Graphics2D g, int w, int h);
	
	public void step(int w, int h);
	
	public void hat();
	
	public void snare();
	
	public void kick();
	
	public void freqBands(boolean[] freqBands);
	
	public void setSpeed(double speed);
}