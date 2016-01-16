package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import styles.Visual;
import styles.beam.Beam;
import styles.blades.Blades;
import styles.dots.Dots;
import styles.madness.Madness;
import styles.rgb.RGB;
import styles.spinner.Spinner;
import styles.strobe.Strobe;

public class Lights extends JPanel {
	
	private int style;
	private double speed;
	private boolean paused;
	private Visual[] effects;
	
	public Lights(int style) {
		super();
		this.style = style;
		paused = false;
		effects = new Visual[]{
				new Beam(), new Blades(), new Dots(), new Madness(),
				new RGB(), new Spinner(), new Strobe()};
	}

	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		super.paintComponent(g);
		setBackground(Color.black);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int w = getWidth(), h = getHeight();
		effects[style].draw(g, w, h);
		if (!paused) {
			effects[style].step(w, h);
		}
	}
	
	public void repaintLights() {
		repaint();
	}
	
	public void hat() {
		if (!paused) {
			effects[style].hat();
		}
	}
	
	public void snare() {
		if (!paused) {
			effects[style].snare();
		}
	}
	
	public void kick() {
		if (!paused) {
			effects[style].kick();
		}
	}
	
	public void freqBands(boolean[] freqBands) {
		if (!paused) {
			effects[style].freqBands(freqBands);
		}
	}
	
	public void setSpeed(double speed) {
		effects[style].setSpeed(speed);
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
		return (Beam) effects[0];
	}
	
	public Blades getBlades() {
		return (Blades) effects[1];
	}
	
	public Dots getDots() {
		return (Dots) effects[2];
	}
	
	public Madness getMadness() {
		return (Madness) effects[3];
	}
	
	public RGB getRGB() {
		return (RGB) effects[4];
	}

	public Spinner getSpinner() {
		return (Spinner) effects[5];
	}

	public Strobe getStrobe() {
		return (Strobe) effects[6];
	}
}