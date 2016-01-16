package styles.strobe;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.*;
import styles.BasicControls;
import styles.Controls;
import styles.strobe.rangeslider.RangeSlider;

public class StrobeControls implements Controls {
	
	private JPanel strobeTimePanel, waitTimePanel;
	private RangeSlider strobeTime, waitTime;
	private Strobe strobe;
	
	public StrobeControls(Strobe strobe) {
		this.strobe = strobe;
		createPanel();
	}
	
	public void createPanel() {
		/*
		// range slider for min/max strobe time
		strobeTime = new RangeSlider(10, 50);
		strobeTime.setValue(getFrame().getLights().getStrobe().getMinStrobeOn());
		strobeTime.setUpperValue(getFrame().getLights().getStrobe().getMaxStrobeOn());
		strobeTime.setMajorTickSpacing(10);
		strobeTime.setMinorTickSpacing(2);
		strobeTime.setPaintTicks(true);
		strobeTime.setPaintLabels(true);
		strobeTime.setPreferredSize(new Dimension(240, strobeTime.getPreferredSize().height));
		strobeTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				getFrame().getLights().getStrobe().setMinStrobeOn(strobeTime.getValue());
				getFrame().getLights().getStrobe().setMaxStrobeOn(slider.getUpperValue());
			}
		});
		JLabel strobeTimeLabel = new JLabel("Adjust min/max strobe ON time");
		strobeTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		strobeTimePanel = new JPanel();
		strobeTimePanel.setLayout(new BoxLayout(strobeTimePanel, BoxLayout.Y_AXIS));
		strobeTimePanel.add(strobeTimeLabel);
		strobeTimePanel.add(strobeTime);
		
		// range slider for min/max wait time
		waitTime = new RangeSlider(0, 200);
		waitTime.setValue(getFrame().getLights().getStrobe().getMinStrobeWait());
		waitTime.setUpperValue(getFrame().getLights().getStrobe().getMaxStrobeWait());
		waitTime.setMajorTickSpacing(50);
		waitTime.setMinorTickSpacing(10);
		waitTime.setPaintTicks(true);
		waitTime.setPaintLabels(true);
		waitTime.setPreferredSize(new Dimension(240, waitTime.getPreferredSize().height));
		waitTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				getFrame().getLights().getStrobe().setMinStrobeWait(waitTime.getValue());
				getFrame().getLights().getStrobe().setMaxStrobeWait(slider.getUpperValue());
			}
		});
		JLabel waitTimeLabel = new JLabel("Adjust min/max strobe WAIT time");
		waitTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		waitTimePanel = new JPanel();
		waitTimePanel.setLayout(new BoxLayout(waitTimePanel, BoxLayout.Y_AXIS));
		waitTimePanel.add(waitTimeLabel);
		waitTimePanel.add(waitTime);
		*/
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{/*strobeTimePanel, waitTimePanel*/};
	}
}