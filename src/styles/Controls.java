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
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Frame;

public class Controls extends JPanel {
	
	private Frame f;
	private JPanel basicControls, basicControlsMini, refTimePanel, currEffectControls;
	private JComboBox styleChooser;
	private JCheckBox fullScreen;
	private JSlider refTime;
	private JButton pause, close;
	
	public Controls(Frame f) {
		super();
		this.f = f;
		currEffectControls = new JPanel();
	}
	
	public void basicPanel() {
		// style switcher
		styleChooser = new JComboBox(new String[]{"Beams", "Dots", "Madness", "Spinner", "Strobe", "Cycle", "Randomize"});
		styleChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = ((JComboBox)e.getSource()).getSelectedIndex();
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
				JCheckBox jcb = (JCheckBox)e.getSource();
				f.setFullScreen(jcb.isSelected());
			}
		});
		
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
				getFrame().hideControls();
			}
		});
		add(close);
		
		// slider for refresh time -- probably not going to be included in final product
		refTime = new JSlider(JSlider.HORIZONTAL, 0, 40, f.getLights().getRefTime());
		refTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (source.equals(refTime)) {
					f.getLights().setRefTime(refTime.getValue());
				}
			}
		});
		refTime.setMajorTickSpacing(10);
		refTime.setMinorTickSpacing(2);
		refTime.setPaintTicks(true);
		refTime.setPaintLabels(true);
		refTime.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel refTimeLabel = new JLabel("Adjust refresh time (ms)");
		refTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		refTimePanel = new JPanel();
		refTimePanel.setLayout(new BoxLayout(refTimePanel, BoxLayout.Y_AXIS));
		refTimePanel.add(refTimeLabel);
		refTimePanel.add(refTime);
		
		basicControlsMini = new JPanel();
		basicControlsMini.setLayout(new GridLayout(4, 1));
		basicControlsMini.add(styleChooser);
		basicControlsMini.add(fullScreen);
		basicControlsMini.add(pause);
		basicControlsMini.add(close);
		basicControls = new JPanel();
		basicControls.add(basicControlsMini);
		basicControls.add(refTimePanel);
		add(basicControls);
		basicControls.setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void clearAndAddPanels(JPanel[] panels) {
		currEffectControls.removeAll();
		currEffectControls.revalidate();
		for (JPanel panel : panels) {
			currEffectControls.add(panel);
		}
		currEffectControls.setBorder(BorderFactory.createLoweredBevelBorder());
		add(currEffectControls);
	}
	
	public JPanel[] getPanels() {
		return null;
	}
	
	public Frame getFrame() {
		return f;
	}
	
	public void updateStyle(int style) {
		styleChooser.setSelectedIndex(style);
	}
	
	public void setFullScreenUnselected() {
		fullScreen.setSelected(false);
	}
}
