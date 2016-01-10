package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import styles.Visual;
import styles.beam.Beam;
import styles.dots.Dots;
import styles.seizure.Seizure;
import styles.spinner.Spinner;
import styles.strobe.Strobe;

public class Lights extends JPanel {
	
	private int refTime, style;
	private boolean paused;
	private Visual[] effects;
	
	public Lights(int style) {
		super();
		refTime = 40;
		this.style = style;
		paused = false;
		effects = new Visual[]{
				new Beam(), new Dots(), new Seizure(), new Spinner(), new Strobe()};
	}

	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		super.paintComponent(g);
		setBackground(Color.black);
		long start = System.currentTimeMillis();
		
		int w = getWidth(), h = getHeight();
		effects[style].draw(g, w, h);
		if (!paused) {
			effects[style].step(w, h);
		}
		
		long end = System.currentTimeMillis();
		try {Thread.sleep(Math.max(refTime - (end - start), 0));}
		catch (InterruptedException e) {}
		repaint();
	}
	
	public int getRefTime() {
		return refTime;
	}

	public void setRefTime(int refTime) {
		this.refTime = refTime;
	}
	
	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void togglePause() {
		paused = !paused;
	}

	public Beam getBeam() {
		return (Beam)effects[0];
	}
	
	public Dots getDots() {
		return (Dots)effects[1];
	}

	public Seizure getSeizure() {
		return (Seizure)effects[2];
	}

	public Spinner getSpinner() {
		return (Spinner)effects[3];
	}

	public Strobe getStrobe() {
		return (Strobe)effects[4];
	}
	
	public void restartStrobe() {
		((Strobe)effects[4]).restart();
	}
}