package soundin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ddf.minim.*;
import ddf.minim.analysis.*;
import main.Frame;

public class BeatDetector {

	private double levelThreshold;
	private boolean h, s, k, o;
	private Timer noInputTimer;
	
	public BeatDetector(Frame[] frames) {
		levelThreshold = 0.2;
		h = s = k = o = false;
		noInputTimer = new Timer(800, new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  //System.out.println("no sound!!");
		    	  for (Frame frame : frames) {
		    		  frame.setSpeed(0.4);
		    	  }
		      }
		});
		noInputTimer.setRepeats(false);
	}
	
	public void start(Frame[] frames) {
		Thread th = new Thread(new Runnable() {
			public void run() {
				Minim minim = new Minim(new MinimInput());
				AudioInput input = minim.getLineIn(Minim.STEREO, 1024);
				// BeatDetect beatDetect = new BeatDetect(); // sound energy mode
				BeatDetect beatDetect = new BeatDetect(1024, 44100.0f); // frequency energy mode
				beatDetect.setSensitivity(200);
				float level = 0f, levelThreshold = 0.2f;

				while (true) {
					long start = System.currentTimeMillis();
					beatDetect.detect(input.mix);
					level = input.mix.level() < 0.0001f ? 0f : input.mix.level();
					h = level > levelThreshold && beatDetect.isHat();
					s = level > levelThreshold && beatDetect.isSnare();
					k = level > levelThreshold && beatDetect.isKick();
					
					int n1 = 0, n2 = 0;
					boolean[] temp = new boolean[27];
					for (int i = 0; i < 27; i++) {
						if (level > levelThreshold) {
							boolean b = beatDetect.isOnset(i);
							temp[i] = b;
							n1++;
						}
					}
					if (n1 > 0) {
						for (Frame frame : frames) {
							frame.freqBands(temp);
						}
						noInputTimer.restart();
					}
					for (Frame frame : frames) {
						if (h) {
							frame.hat();
							n2++;
						}
						if (s) {
							frame.snare();
							n2++;
						}
						if (k) {
							frame.kick();
							n2++;
						}
						frame.repaintLights();
			    	}
					if (n2 > 0) {
						for (Frame frame : frames) {
							frame.setSpeed(1);
						}
						noInputTimer.restart();
					}
					long end = System.currentTimeMillis();
					try {Thread.sleep(Math.max(30 - (end - start), 0));} catch (InterruptedException e) {}
				}
			}
		});
		th.start();
	}

	public double getLevelThreshold() {
		return levelThreshold;
	}

	public void setLevelThreshold(double threshold) {
		levelThreshold = threshold;
	}
}