package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Method;
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

import styles.ControlPanel;

public class Frame extends JFrame implements MouseListener {
	
	public static final int NORMAL_MODE = 0, CYCLE_MODE = 1, RANDOMIZE_MODE = 2;
	private int style, cycleIndex, randIndex, screen, refTime, sensitivity;
	private double levelThreshold;
	private boolean cycle, randomize, fullScreen, showControls, canOSXFullScreen;
	private final boolean needsAttachedControls = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length == 1;
	private Lights lights;
	private ControlPanel controlPanel;
	private ArrayList<Integer> includedEffects;
	private Timer timer;
	private JFrame externalControls;
	
	/**
	 * Creates a frame with a specified title and beat detector options.
	 * @param title
	 */
	public Frame(String title) {
		super(title);
		createFrame();
	}
	
	/**
	 * Initializes all the variables necessary for the frame to work.
	 */
	public void createFrame() {
		style = 0;
		cycleIndex = 0;
		randIndex = 0;
		screen = 0;
		refTime = 30;
		sensitivity = 1000;
		levelThreshold = 0.2;
		cycle = false;
		randomize = false;
		fullScreen = false;
		showControls = false;
		canOSXFullScreen = enableOSXFullscreen(this);
		if (!canOSXFullScreen) {
			setUndecorated(false);
		}
		lights = new Lights(style);
		controlPanel = new ControlPanel(this);
		includedEffects = generateIncludedEffects();
		setLayout(new BorderLayout());
		add(lights, BorderLayout.CENTER);
		setVisible(true);
		setResizable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		externalControls = new JFrame("Controls");
		updateStyle(style);
	}
	
	public static boolean enableOSXFullscreen(Window window) {
	    if (window == null) {
	    	return true;
	    }
	    try {
	        Class<?> util = Class.forName("com.apple.eawt.FullScreenUtilities");
	        Class<?>[] params = new Class[]{Window.class, Boolean.TYPE};
	        Method method = util.getMethod("setWindowCanFullScreen", params);
	        method.invoke(util, window, true);
		    return true;
	    } catch (ClassNotFoundException e) {
	    	System.out.println("ERROR: OSX FULLSCREEN CLASS NOT FOUND");
	        e.printStackTrace();
	    	return false;
	    } catch (Exception e) {
	        System.out.println("ERROR: OSX FULLSCREEN FAIL");
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean needsAttachedControls() {
		return needsAttachedControls;
	}
	
	/**
	 * Shows the controls, unless they are in a separate window.
	 */
	public void showControls() {
		showControls = true;
		if (needsAttachedControls) {
			getContentPane().removeAll();
			getContentPane().revalidate();
			add(lights, BorderLayout.CENTER);
			controlPanel.update(style);
			add(controlPanel, BorderLayout.SOUTH);
			getContentPane().revalidate();
			getContentPane().repaint();
		} else {
			initExternalControls();
		}
	}
	
	/**
	 * Hides the controls.
	 */
	public void hideControls() {
		showControls = false;
		if (needsAttachedControls) {
			getContentPane().removeAll();
			getContentPane().revalidate();
			add(lights, BorderLayout.CENTER);
			getContentPane().repaint();
		} else {
			externalControls.dispose();
		}
	}
	
	public void updateStyle(int style) {
		this.style = style;
		lights.setStyle(style);
		controlPanel.update(style);
		externalControls.pack();
	}
	
	public void hat() {
		lights.hat();
	}
	
	public void snare() {
		lights.snare();
	}
	
	public void kick() {
		lights.kick();
	}
	
	public void freqBands(boolean[] freqBands) {
		lights.freqBands(freqBands);
		
	}
	
	public Lights getLights() {
		return lights;
	}
	
	public void setSpeed(double speed) {
		lights.setSpeed(speed);
	}
	
	public void repaintLights() {
		lights.repaintLights();
	}
	
	public void setAntialiasing(boolean antialiasing) {
		lights.setAntialiasing(antialiasing);
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
		// figure out what graphics devices are available
		GraphicsDevice[] gds = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		// have the user choose which screen they want to display on
		String chosenScreen = null;
		if (gds.length > 1 && fullScreen) {
			Object[] screens = new Object[gds.length];
			for (int i = 0; i < gds.length; i++) {
			    Rectangle bounds = gds[i].getDefaultConfiguration().getBounds();
			    screens[i] = "Screen " + (i + 1) + "  [x=" + (int)bounds.getX() + ", y=" + (int)bounds.getY()
			    		+ ", width=" + (int)bounds.getWidth() + ", height=" + (int)bounds.getHeight() + "]";
			}
			chosenScreen = (String)JOptionPane.showInputDialog(
					this, "Please choose which screen you would like to use.", "Full Screen Options",
					JOptionPane.PLAIN_MESSAGE, null, screens, screens[0]);
			if (chosenScreen == null) {
				controlPanel.setFullScreenUnselected();
				return;
			}
			for (int i = 0; i < gds.length; i++) {
				if (chosenScreen == screens[i]) {
					screen = i;
					break;
				}
			}
		}
		Rectangle bounds = gds[screen].getDefaultConfiguration().getBounds();
		this.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		if (fullScreen) {
			if (gds.length == 1 || chosenScreen != null) {
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
		revalidate();
		repaint();
		if (!needsAttachedControls) {
			initExternalControls();
		}
	}
	
	private void initExternalControls() {
		externalControls.dispose();
		externalControls = new JFrame("Controls");
		externalControls.add(controlPanel, BorderLayout.CENTER);
		externalControls.pack();
		externalControls.setResizable(false);
		externalControls.setAlwaysOnTop(true);
		int ecScreen = 0;
		if (fullScreen) {
			ecScreen = screen != 0 ? 0 : 1;
		}
		Rectangle ecBounds = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getScreenDevices()[ecScreen].getDefaultConfiguration().getBounds();
		externalControls.setLocation(ecBounds.x, ecBounds.y);
		externalControls.setVisible(true);
		externalControls.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				showControls = false;
			}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {
				initExternalControls();
			}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		});
	}
	
	public boolean canOSXFullScreen() {
		return canOSXFullScreen;
	}
	
	public int getStyle() {
		return style;
	}
	
	public int getRefTime() {
		return refTime;
	}

	public void setRefTime(int refTime) {
		this.refTime = refTime;
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public double getLevelThreshold() {
		return levelThreshold;
	}

	public void setLevelThreshold(double threshold) {
		levelThreshold = threshold;
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
	
	public void displayDelayDialog(int mode) {
		includedEffects = generateIncludedEffects();
		timer.stop();
		JDialog dialog = new JDialog(this, "Full Screen Options", Dialog.ModalityType.APPLICATION_MODAL);
		JLabel label = new JLabel("Choose the time (seconds) between effects", SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSlider delaySlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
		delaySlider.setSnapToTicks(true);
		delaySlider.setMajorTickSpacing(5);
		delaySlider.setMinorTickSpacing(1);
		delaySlider.setPaintTicks(true);
		delaySlider.setPaintLabels(true);
		delaySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				timer.setDelay(delaySlider.getValue() * 1000);
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
				if (mode == NORMAL_MODE) {
					setCycle(false);
					setRandomize(false);
				} else if (mode == CYCLE_MODE) {
					setCycle(true);
				} else if (mode ==  RANDOMIZE_MODE) {
					setRandomize(true);
				}
				Collections.sort(includedEffects);
				lights.setPaused(false);
				timer.setDelay(delaySlider.getValue() * 1000);
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancel);
		buttonPanel.add(okay);
		JCheckBox[] cbs = new JCheckBox[]{
				new JCheckBox("Beams"), new JCheckBox("Blades"), new JCheckBox("Dots"),
				new JCheckBox("RGB"), new JCheckBox("Madness"), new JCheckBox("Spinner"),
				new JCheckBox("Strobe"), new JCheckBox("Swirl")
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
	    dialog.pack();
	    dialog.setSize(dialog.getWidth() + 40, dialog.getHeight());
	    dialog.setLocation((getWidth() - dialog.getWidth()) / 2, (getHeight() - dialog.getHeight()) / 2); 
	    dialog.setVisible(true);
	    dialog.setModal(true);
	}
	
	private ArrayList<Integer> generateIncludedEffects() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < controlPanel.length(); i++) {
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
			controlPanel.updateStyleChooserIndex(style);
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}