package styles;

import java.awt.Graphics2D;

public interface Visual {
	
	public void draw(Graphics2D g, int w, int h);
	
	public void step(int w, int h);

}