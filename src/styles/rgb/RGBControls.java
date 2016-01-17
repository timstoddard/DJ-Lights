package styles.rgb;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import styles.Controls;

public class RGBControls extends JPanel implements Controls {
	
	private RGB rgb;
	private JPanel repeatPanel, directionPanel;
	private JSlider repeat;
	private JRadioButton vertical, horizontal;
	
	public RGBControls(RGB rgb) {
		this.rgb = rgb;
		createPanel();
	}
	
	public void createPanel() {
		// slider for repeat size
		repeat = new JSlider(JSlider.HORIZONTAL, 1, 10, rgb.getRepeat());
		repeat.setSnapToTicks(true);
		repeat.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rgb.setRepeat(repeat.getValue());
			}
		});
		repeat.setMajorTickSpacing(1);
		repeat.setPaintTicks(true);
		repeat.setPaintLabels(true);
		repeat.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel nLabel = new JLabel("Adjust number of repeats");
		nLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		repeatPanel = new JPanel();
		repeatPanel.setLayout(new BoxLayout(repeatPanel, BoxLayout.Y_AXIS));
		repeatPanel.add(nLabel);
		repeatPanel.add(repeat);
		
		// choose between vertical/horizontal
		vertical = new JRadioButton("Vertical");
		vertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rgb.setVertical(true);
			}
		});
		vertical.setSelected(true);
		horizontal = new JRadioButton("Horizontal");
		horizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rgb.setVertical(false);
			}
		});
		ButtonGroup styleGroup = new ButtonGroup();
		styleGroup.add(vertical);
		styleGroup.add(horizontal);
		directionPanel = new JPanel();
		directionPanel.setLayout(new BoxLayout(directionPanel, BoxLayout.Y_AXIS));
		directionPanel.add(vertical);
		directionPanel.add(horizontal);
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{repeatPanel, directionPanel};
	}
}