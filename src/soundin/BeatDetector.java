package soundin;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;

import ddf.minim.*;
import ddf.minim.analysis.*;
import main.Frame;

public class BeatDetector extends JPanel {

	private boolean h, s, k, o;
	
	public BeatDetector() {
		h = s = k = o = false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int wid = getWidth(), hgt = getHeight();
		this.setBackground(Color.black);
		if (h) {
			g.setColor(Color.red);
			g.fillRect(0, 0, wid / 3, hgt);
			
		}
		if (s) {
			g.setColor(Color.green);
			g.fillRect(wid / 3, 0, wid / 3, hgt);
			
		}
		if (k) {
			g.setColor(Color.blue);
			g.fillRect(2 * wid / 3, 0, wid / 3, hgt);
		}
	}
	
	public void start(Frame[] frames) {
		// Beat detect thread
		Thread th = new Thread(new Runnable() {

			public void run() {
				Minim minim = new Minim(new MinimInput());

				AudioInput input = minim.getLineIn(Minim.STEREO, 1024);
				BeatDetect beatDetect = new BeatDetect(1024, 44100.0f);
				beatDetect.setSensitivity(200);
				
				float level = 0f, levelThreshold = 0.2f;

				while (true) {
					beatDetect.detect(input.mix);
					level = input.mix.level() < 0.0001f ? 0f : input.mix.level();

					if(level > levelThreshold && beatDetect.isHat()) {
						System.out.println("HAT");
						for (Frame frame : frames) {
							frame.hat();
						}
						h = true;
					} else {
						h = false;
					}

					if(level > levelThreshold && beatDetect.isSnare()) {
						System.out.println("SNARE");
						for (Frame frame : frames) {
							frame.snare();
						}
						s = true;
					} else {
						s = false;
					}

					if (level > levelThreshold && beatDetect.isKick()) {
						System.out.println("KICK");
						k = true;
						for (Frame frame : frames) {
							frame.kick();
						}
					} else {
						k = false;
					}
					
					if (level > levelThreshold && beatDetect.isOnset()) {
						System.out.println("ONSET");
						o = true;
					} else {
						o = false;
					}
					repaint();
					try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		});

		th.start();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tester");
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		BeatDetector test = new BeatDetector();
		frame.add(test);
		test.start(new Frame[]{});
	}
}
