package main;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import styles.Controls;
import styles.beam.BeamControls;
import styles.dots.DotsControls;
import styles.seizure.SeizureControls;
import styles.spinner.SpinnerControls;
import styles.strobe.StrobeControls;

public class Frame extends JFrame implements MouseListener {
	
	public static final int NORMAL_MODE = 0, CYCLE_MODE = 1, RANDOMIZE_MODE = 2;
	private int style, delay, screen;
	private boolean cycle, randomize, showControls;
	private Lights l;
	private Controls[] controls;
	private Timer timer;
	
	public Frame() {
		super("Turn Up");
		style = 1;
		delay = 1000;
		screen = 0;
		cycle = false;
		randomize = false;
		showControls = false;
		l = new Lights(style);
		controls = new Controls[]{
				new BeamControls(this), new DotsControls(this), new SeizureControls(this),
				new SpinnerControls(this), new StrobeControls(this)};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(l, BorderLayout.CENTER);
		setVisible(true);
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addMouseListener(this);
		timer = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cycle) {
					style++;
					style = style - controls.length >= 0 ? style - controls.length : style;
					updateStyle(style);
				} else if (randomize) {
					int oldStyle = style;
					while (style == oldStyle || style == 1) {
						style = (int)(Math.random() * controls.length);
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
		controls[style].updateStyle(style);
		add(controls[style], BorderLayout.SOUTH);
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
		if (style == 4) {
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

	public void setFullScreen(boolean fullScreen) {
		GraphicsDevice[] gds = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		String chosenScreen = null;
		if (gds.length > 0 && fullScreen) {
			Object[] screens = new Object[gds.length];
			for (int i = 0; i < gds.length; i++) {
			    Rectangle bounds = gds[i].getDefaultConfiguration().getBounds();
			    screens[i] = "Screen " + (i + 1) + "  [x=" + (int)bounds.getX() + ", y=" + (int)bounds.getY()
			    		+ ", width=" + (int)bounds.getWidth() + ", height=" + (int)bounds.getHeight() + "]";
			}
			chosenScreen = (String)JOptionPane.showInputDialog(
					this, "Please choose which screen you would like to use.", "Full Screen Options",
					JOptionPane.PLAIN_MESSAGE, null, screens, screens[0]);
			for (int i = 0; i < gds.length; i++) {
				if (chosenScreen == screens[i]) {
					screen = i;
					break;
				}
			}
		}
		this.setLocation(gds[screen].getDefaultConfiguration().getBounds().x, this.getY());
		if (fullScreen) {
			if (chosenScreen != null) {
				setVisible(false);
				dispose();
				setUndecorated(true);
				gds[screen].setFullScreenWindow(this);
				setVisible(true);
				hideControls();
			} else {
				controls[style].setFullScreenCheckBoxFalse();
			}
		} else {
			setVisible(false);
			dispose();
			setUndecorated(false);
			gds[screen].setFullScreenWindow(null);
			setResizable(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setVisible(true);
		}
		repaint();
	}
	
	public int getStyle() {
		return style;
	}
	
	public void setCycle(boolean b) {
		cycle = b;
		randomize = false;
		timer.start();
	}
	
	public void setRandomize(boolean b) {
		randomize = b;
		cycle = false;
		timer.start();
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int ms) {
		delay = ms;
		timer.setDelay(ms);
	}
	
	public void displayDelayDialog(int mode) {
		boolean wasPaused = l.isPaused();
		l.setPaused(true);
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
				l.setPaused(wasPaused);
				if (mode == 1) {
					setCycle(true);
				} else if (mode ==  2) {
					setRandomize(true);
				}
				jd.dispose();
			}
		});
		pan.add(delaySlider);
		pan.add(button);
		jd.add(pan);
		jd.setSize(380, 120);
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