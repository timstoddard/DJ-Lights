package soundin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ddf.minim.*;
import ddf.minim.analysis.*;
import main.Frame;

public class BeatDetector {

	private boolean h, s, k;
	private Timer noInputTimer;
	private Frame frame;
	
	public BeatDetector(Frame frame) {
		this.frame = frame;
		h = s = k = false;
		noInputTimer = new Timer(750, new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    		 frame.setSpeed(0.25);
		      }
		});
		noInputTimer.setRepeats(false);
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				Minim minim = new Minim(new MinimInput());
				AudioInput input = minim.getLineIn(Minim.STEREO, 1024);
				// BeatDetect beatDetect = new BeatDetect(); // sound energy mode
				BeatDetect beatDetect = new BeatDetect(1024, 44100.0f); // frequency energy mode
				int lastSensitivity = 0;
				float level = 0f;
				while (true) {
					beatDetect.detect(input.mix);
					long start = System.currentTimeMillis();
					if (frame.getSensitivity() != lastSensitivity) {
						beatDetect.setSensitivity(frame.getSensitivity());
						lastSensitivity = frame.getSensitivity();
					}
					level = input.mix.level() < 0.0001f ? 0f : input.mix.level();
					h = level > frame.getLevelThreshold() && beatDetect.isHat();
					s = level > frame.getLevelThreshold() && beatDetect.isSnare();
					k = level > frame.getLevelThreshold() && beatDetect.isKick();
					
					int n1 = 0, n2 = 0;
					boolean[] temp = new boolean[27];
					for (int i = 0; i < 27; i++) {
						if (level > frame.getLevelThreshold()) {
							boolean b = beatDetect.isOnset(i);
							temp[i] = b;
							n1++;
						}
					}
					if (n1 > 0) {
						frame.freqBands(temp);
						noInputTimer.restart();
					}
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
					if (n2 > 0) {
						frame.setSpeed(1);
						noInputTimer.restart();
					}
					frame.repaintLights();
					long end = System.currentTimeMillis();
					try {Thread.sleep(Math.max(frame.getRefTime() - (end - start), 0));} catch (InterruptedException e) {}
				}
			}
		});
		thread.start();
	}
}