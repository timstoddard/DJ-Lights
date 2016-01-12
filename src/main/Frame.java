package main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import styles.Controls;
import styles.beam.BeamControls;
import styles.dots.DotsControls;
import styles.seizure.SeizureControls;
import styles.spinner.SpinnerControls;
import styles.strobe.StrobeControls;

public class Frame extends JFrame implements MouseListener {
	
	public static final int NORMAL_MODE = 0, CYCLE_MODE = 1, RANDOMIZE_MODE = 2;
	private int style, cycleIndex, randIndex, screen;
	private boolean cycle, randomize, showControls;
	private Lights l;
	private Controls controlPanel;
	private Controls[] controls;
	private ArrayList<Integer> includedEffects;
	private Timer timer;
	
	public Frame() {
		super("Turn Up");
	}
	
	public Frame(String title) {
		super(title);
	}
	
	public void createFrame() {
		style = 0;
		cycleIndex = 0;
		randIndex = 0;
		screen = 0;
		cycle = false;
		randomize = false;
		showControls = false;
		l = new Lights(style);
		controlPanel = new Controls(this);
		controlPanel.basicPanel();
		controls = new Controls[]{
				new BeamControls(this), new DotsControls(this), new SeizureControls(this),
				new SpinnerControls(this), new StrobeControls(this)};
		includedEffects = generateIncludedEffects();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(l, BorderLayout.CENTER);
		setVisible(true);
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addMouseListener(this);
		timer = new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cycle) {
					updateStyle(includedEffects.get(cycleIndex));
					cycleIndex = cycleIndex + 1 >= includedEffects.size() ? 0 : cycleIndex + 1;
				} else if (randomize) {
					updateStyle(includedEffects.get(randIndex));
					int oldRandIndex = randIndex;
					while (randIndex == oldRandIndex) {
						randIndex = (int)(Math.random() * includedEffects.size());
					}
				}
			}
		});
	}
	
	public void showControls() {
		showControls = true;
		getContentPane().removeAll();
		getContentPane().revalidate();
		add(l, BorderLayout.CENTER);
		controlPanel.clearAndAddPanels(controls[style].getPanels());
		add(controlPanel, BorderLayout.SOUTH);
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
		if (cycle) {
			randomize = false;
			cycleIndex = 0;
			timer.start();
		}
	}
	
	public void setRandomize(boolean b) {
		randomize = b;
		if (randomize) {
			cycle = false;
			randIndex = 0;
			timer.start();
		}
	}
	
	public int getDelay() {
		return timer.getDelay();
	}
	
	public void setDelay(int ms) {
		timer.setDelay(ms);
	}
	
	public void displayDelayDialog(int mode) {
		includedEffects = generateIncludedEffects();
		timer.stop();
		JDialog dialog = new JDialog(this, "Full Screen Options", Dialog.ModalityType.APPLICATION_MODAL);
		JLabel label = new JLabel("Choose the time (ms) between effects", SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSlider delaySlider = new JSlider(JSlider.HORIZONTAL, 0, 20000, 10000);
		delaySlider.setMajorTickSpacing(5000);
		delaySlider.setMinorTickSpacing(1000);
		delaySlider.setPaintTicks(true);
		delaySlider.setPaintLabels(true);
		delaySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JSlider theSlider = (JSlider) changeEvent.getSource();
				if (!theSlider.getValueIsAdjusting()) {
					setDelay(delaySlider.getValue());
				}
			}
		});
		JPanel delayPanel = new JPanel(new BorderLayout());
		delayPanel.add(label, BorderLayout.NORTH);
		delayPanel.add(delaySlider, BorderLayout.SOUTH);
		JButton cancel = new JButton("Cancel");
		cancel.setSize(80, 20);
		cancel.setMaximumSize(new Dimension(80, 20));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		JButton okay = new JButton("OK");
		okay.setSize(80, 20);
		okay.setMaximumSize(new Dimension(80, 20));
		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				if (mode == 0) {
					setCycle(false);
					setRandomize(false);
				} else if (mode == CYCLE_MODE) {
					setCycle(true);
				} else if (mode ==  RANDOMIZE_MODE) {
					setRandomize(true);
				}
				Collections.sort(includedEffects);
				l.setPaused(false);
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancel);
		buttonPanel.add(okay);
		JCheckBox[] cbs = new JCheckBox[]{
				new JCheckBox("Beams"), new JCheckBox("Dots"), new JCheckBox("Madness"), new JCheckBox("Spinner"), new JCheckBox("Strobe")
		};
		JPanel checkBoxPanel = new JPanel(new GridLayout(cbs.length, 1));
		JLabel checkBoxLabel = new JLabel("Choose which visual effects you would like to include", SwingConstants.CENTER);
		checkBoxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkBoxPanel.add(checkBoxLabel);
		for (JCheckBox cb : cbs) {
			cb.setAlignmentX(Component.CENTER_ALIGNMENT);
			checkBoxPanel.add(cb);
			cb.setSelected(true);
			cb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < cbs.length; i++) {
						if (cb.getText().equals(cbs[i].getText())) {
							if (cb.isSelected() && !includedEffects.contains(i)) {
								includedEffects.add(i);
							} else if (!cb.isSelected() && includedEffects.contains(i)) {
								includedEffects.remove(includedEffects.indexOf(i));
							}
						}
					}
				}
			});
		}
		checkBoxPanel.setMaximumSize(new Dimension(100, getHeight() / 2));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalStrut(10));
		panel.add(delayPanel);
		panel.add(Box.createVerticalStrut(10));
		panel.add(checkBoxLabel);
		panel.add(checkBoxPanel);
		panel.add(buttonPanel);
	    dialog.add(panel);
	    int width = 420, height = 180 + 20 * cbs.length;
	    dialog.setBounds(new Rectangle((getWidth() - width) / 2, (getHeight() - height) / 2, width, height));
	    dialog.setVisible(true);
	    dialog.setModal(true);
	}
	
	private ArrayList<Integer> generateIncludedEffects() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < controls.length; i++) {
			temp.add(i);
		}
		return temp;
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
			controlPanel.updateStyle(style);
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	private static boolean isInt(String s) {
		if (s == null || s.equals("")) {
			return false;
		}
		for (char c : s.toCharArray()) {
	        if (!Character.isDigit(c)) {
	        	return false;
	        }
	    }
	    return true;
	}
	
	public static void main(String[] args) {
		String frames = "";
		int failedAttempts = 0;
		while (!isInt(frames)) {
			frames = (String)JOptionPane.showInputDialog(
	                null, "How many frames do you want?\n" +
	                		(failedAttempts > 0 ? "Please enter in integer greater than zero."
	                				: "WARNING: If you make a lot of frames,\nyour computer may run very slowly!"),
	                "Number of Frames", JOptionPane.PLAIN_MESSAGE, null, null, "1");
			if (frames == null) {
				//frames = "1";
				//break;
				System.exit(1);
			}
			failedAttempts++;
		}
		for (int i = 0; i < Integer.parseInt(frames); i++) {
			Frame frame = new Frame("Turn Up" + (Integer.parseInt(frames) > 1 ? " " + (i + 1) : "!"));
			frame.createFrame();
		}
	}
}