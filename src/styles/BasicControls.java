package styles;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Frame;

public class BasicControls extends JPanel implements Controls {
	
	private JPanel basicControls, basicControlsMini, sliderPanel;
	private JComboBox<?> styleChooser;
	private JCheckBox fullScreen, antialiasing;
	private boolean shownAntialiasingWarning;
	private JSlider refTime, sensitivity, levelThreshold;
	private JButton pause, close;
	private Frame f;
	
	public BasicControls(Frame f) {
		super();
		this.f = f;
		createPanel();
	}
	
	public void createPanel() {
		// style switcher
		styleChooser = new JComboBox<String>(new String[]{"Beams", "Blades", "Dots", "Madness",
				"RGB", "Spinner", "Strobe", "Swirl", "Cycle", "Randomize"});
		styleChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = ((JComboBox<?>)e.getSource()).getSelectedIndex();
				if (0 <= index && index <= styleChooser.getItemCount() - 3) {
					f.updateStyle(index);
				} else if (index == styleChooser.getItemCount() - 2) {
					f.displayDelayDialog(Frame.CYCLE_MODE);
				} else if (index == styleChooser.getItemCount() - 1) {
					f.displayDelayDialog(Frame.RANDOMIZE_MODE);
				} 
			}
		});
		
		// toggles between windowed mode and fullscreen mode
		fullScreen = new JCheckBox("Toggle Full Screen");
		fullScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setFullScreen(fullScreen.isSelected());
			}
		});
		
		// toggles antialiasing of the graphics
		antialiasing = new JCheckBox("Toggle Antialiasing");
		antialiasing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!shownAntialiasingWarning) {
					JOptionPane.showMessageDialog(f, "WARNING: Antialiasing can take a lot of"
							+ " processing power.\nSome effects may lag if you turn this feature"
							+ " on.", "Antialiasing warning", JOptionPane.WARNING_MESSAGE, null);
					shownAntialiasingWarning = true;
				}
				f.setAntialiasing(antialiasing.isSelected());
			}
		});
		shownAntialiasingWarning = false;
		
		// pause button
		pause = new JButton("Pause Effects");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.getLights().togglePause();
				if (f.getLights().isPaused()) {
					pause.setText("Unpause Effects");
				} else {
					pause.setText("Pause Effects");
				}
			}
		});
		
		// close button
		close = new JButton("Close Controls");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.hideControls();
			}
		});
		add(close);
		
		// slider for refresh time -- probably not going to be included in final product
		refTime = new JSlider(JSlider.HORIZONTAL, 0, 40, f.getRefTime());
		refTime.setSnapToTicks(true);
		refTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				f.setRefTime(refTime.getValue());
			}
		});
		refTime.setMajorTickSpacing(10);
		refTime.setMinorTickSpacing(2);
		refTime.setPaintTicks(true);
		refTime.setPaintLabels(true);
		refTime.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel refTimeLabel = new JLabel("Adjust refresh time (ms)");
		refTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		sliderPanel.add(refTimeLabel);
		sliderPanel.add(refTime);
		
		// slider for sensitivity
		sensitivity = new JSlider(JSlider.HORIZONTAL, 0, 1000, f.getSensitivity());
		sensitivity.setSnapToTicks(true);
		sensitivity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				f.setSensitivity(1000-sensitivity.getValue());
			}
		});
		sensitivity.setMajorTickSpacing(200);
		sensitivity.setMinorTickSpacing(50);
		sensitivity.setPaintTicks(true);
		sensitivity.setPaintLabels(true);
		sensitivity.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel sensitivityLabel = new JLabel("Adjust sensitivity");
		sensitivityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sliderPanel.add(sensitivityLabel);
		sliderPanel.add(sensitivity);
		
		// slider for level threshold
		levelThreshold = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(f.getLevelThreshold()*100));
		levelThreshold.setSnapToTicks(true);
		levelThreshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				f.setLevelThreshold((double) levelThreshold.getValue() / 100);
			}
		});
		levelThreshold.setMajorTickSpacing(20);
		levelThreshold.setMinorTickSpacing(5);
		levelThreshold.setPaintTicks(true);
		levelThreshold.setPaintLabels(true);
		levelThreshold.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel levelThresholdLabel = new JLabel("Adjust level threshold");
		levelThresholdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sliderPanel.add(levelThresholdLabel);
		sliderPanel.add(levelThreshold);
		
		basicControlsMini = new JPanel();
		basicControlsMini.setLayout(new GridLayout(f.canOSXFullScreen() ? 5 : 4, 1));
		basicControlsMini.add(styleChooser);
		if (f.needsAttachedControls() && !f.canOSXFullScreen()) {
			basicControlsMini.add(fullScreen);
		}
		basicControlsMini.add(antialiasing);
		basicControlsMini.add(pause);
		basicControlsMini.add(close);
		basicControls = new JPanel();
		basicControls.add(basicControlsMini);
		basicControls.add(sliderPanel);
		basicControls.setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	@Override
	public JPanel[] getPanels() {
		return new JPanel[]{basicControls};
	}
	
	public void updateStyleChooserIndex(int style) {
		styleChooser.setSelectedIndex(style);
	}
	
	public void setFullScreenUnselected() {
		fullScreen.setSelected(false);
	}
}