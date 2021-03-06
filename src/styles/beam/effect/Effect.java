package styles.beam.effect;
import java.awt.Graphics2D;
import java.util.ArrayList;

import styles.beam.Arc;

public abstract class Effect {
	
	private ArrayList<Arc> beams;
	private boolean done;
	
	public Effect() {
		beams = new ArrayList<Arc>();
		done = false;
	}
	
	public abstract void draw(Graphics2D g, int x, int y, int maxRadius, boolean fillArc, boolean drawBorder);
	
	public abstract void step(double speed);
	
	public abstract void switchDirection();
	
	public abstract void switchColor();
	
	public boolean getDone() {
		return done;
	}
	
	public void setDone(boolean b) {
		done = b;
	}
	
	public void addArc(Arc a) {
		beams.add(a);
	}
	
	public void removeArc(int i) {
		beams.remove(i);
	}
	
	public ArrayList<Arc> getBeams() {
		return beams;
	}
}