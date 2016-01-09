package main;
import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import styles.beam.BeamControls;
import styles.seizure.SeizureControls;
import styles.spinner.SpinnerControls;
import styles.strobe.StrobeControls;

public class Frame extends JFrame implements MouseListener {
	
	private int style, delay;
	private boolean cycle, randomize, showControls;
	private Lights l;
	private BeamControls beamcontrols;
	private SeizureControls seizurecontrols;
	private SpinnerControls spinnercontrols;
	private StrobeControls strobecontrols;
	private DisplayMode dispModeOld;
	private Timer timer;
	
	public Frame() {
		super("Turn Up");
		style = 0;
		delay = 1000;
		cycle = false;
		randomize = false;
		showControls = false;
		l = new Lights();
		l.setStyle(style);
		beamcontrols = new BeamControls(this);
		seizurecontrols = new SeizureControls(this);
		spinnercontrols = new SpinnerControls(this);
		strobecontrols = new StrobeControls(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(l, BorderLayout.CENTER);
		setVisible(true);
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addMouseListener(this);
		dispModeOld = null;
		timer = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cycle) {
					style++;
					style = style - 4 >= 0 ? style - 4 : style;
					updateStyle(style);
				} else if (randomize) {
					int oldStyle = style;
					while (style == oldStyle /*|| style == 1*/) {
						style = (int)(Math.random() * 4);
					}
					updateStyle(style);
				}
			}
		});
	}
	
	public void showControls() {
		showControls = true;
		getContentPane().removeAll();
		getContentPane().revalidate();
		add(l, BorderLayout.CENTER);
		if (style == 0) {
			beamcontrols.updateStyle(style);
			add(beamcontrols, BorderLayout.SOUTH);
		} else if (style == 1) {
			seizurecontrols.updateStyle(style);
			add(seizurecontrols, BorderLayout.SOUTH);
		} else if (style == 2) {
			spinnercontrols.updateStyle(style);
			add(spinnercontrols, BorderLayout.SOUTH);
		} else if (style == 3) {
			strobecontrols.updateStyle(style);
			add(strobecontrols, BorderLayout.SOUTH);
		}
		getContentPane().repaint();
	}
	
	public void hideControls() {
		showControls = false;
		getContentPane().removeAll();
		getContentPane().revalidate();
		add(l, BorderLayout.CENTER);
		getContentPane().repaint();
	}
	
	public void updateStyle(int style) {
		this.style = style;
		l.setStyle(style);
		if (style == 3) {
			l.restartStrobe();
		}
		if (showControls) {
			showControls();
		} else {
			hideControls();
		}
	}
	
	public Lights getLights() {
		return l;
	}

	/**
	 * Moves the frame to the largest monitor available and sets it as fullscreen or windowed depending on the value of the parameter
	 * @param fullScreen - If true, the frame is now in fullscreen; if false, the frame is now windowed.
	 */
	public void setFullScreen(boolean fullScreen) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gds = ge.getScreenDevices();
		int max = Integer.MIN_VALUE, screen = -1;
		/*for (int i = 0; i < gds.length; i++) {
			int dimensions = gds[i].getDisplayMode().getWidth() * gds[i].getDisplayMode().getHeight();
			if (dimensions > max) {
				max = dimensions;
				screen = i;
			}
		}*/
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < gds.length; i++) {
			int dimensions = gds[i].getDisplayMode().getWidth() * gds[i].getDisplayMode().getHeight();
			if (dimensions < min) {
				min = dimensions;
				screen = i;
			}
		}
		this.setLocation(gds[screen].getDefaultConfiguration().getBounds().x, this.getY());
		if (fullScreen) {
			dispModeOld = gds[screen].getDisplayMode();
			setVisible(false);
			dispose();
			setUndecorated(true);
			gds[screen].setFullScreenWindow(this);
			setVisible(true);
			hideControls();
		} else {
			gds[screen].setDisplayMode(dispModeOld);
			setVisible(false);
			dispose();
			setUndecorated(false);
			gds[screen].setFullScreenWindow(null);
			setResizable(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setLocationRelativeTo(null);
			setVisible(true);
		}
		repaint();
	}
	
	public int getStyle() {
		return style;
	}
	
	public void setCycle(boolean b) {
		cycle = b;
		timer.start();
	}
	
	public void setRandomize(boolean b) {
		randomize = b;
		timer.start();
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int ms) {
		delay = ms;
		timer.setDelay(ms);
	}
	
	public void displayDelayDialog() {
		JPanel pan=new JPanel();
		pan.add(new JLabel("Choose the delay (in milliseconds) between new effects"));
		JSlider delaySlider = new JSlider(JSlider.HORIZONTAL, 0, 20000, delay);
		delaySlider.setMajorTickSpacing(5000);
		delaySlider.setMinorTickSpacing(1000);
		delaySlider.setPaintTicks(true);
		delaySlider.setPaintLabels(true);
		JDialog jd=new JDialog();
		JButton button = new JButton("Okay");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDelay(delaySlider.getValue());
				jd.dispose();
			}
		});
		pan.add(delaySlider);
		pan.add(button);
		jd.add(pan);
		jd.setSize(300, 150);
        jd.setLocationRelativeTo(this);
        jd.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		if (button == MouseEvent.BUTTON1) {
			if (!showControls) {
				showControls();
			}
		} else if (button == MouseEvent.BUTTON3) {
			cycle = false;
			randomize = false;
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) {
		Frame frame = new Frame();
		//Frame frame2 = new Frame();
	}
}