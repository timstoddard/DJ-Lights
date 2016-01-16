package styles.dots;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Frame;
import styles.BasicControls;
import styles.Controls;

public class DotsControls extends JPanel implements Controls {
	
	private JPanel sideSizePanel, borderSizePanel, chaseSpeedPanel, buttonPanel;
	private JSlider sideSize, borderSize;
	private JComboBox<?> chaseSpeed;
	private JLabel chaseSpeedLabel;
	private JCheckBox chase, rainbow;
	private Dots dots;
	
	public DotsControls(Dots dots) {
		this.dots = dots;
		createPanel();
	}
	
	public void createPanel() {
		// slider for side size
		sideSize = new JSlider(JSlider.HORIZONTAL, 10, 200, dots.getSide());
		sideSize.setSnapToTicks(true);
		sideSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				dots.setSide(sideSize.getValue());
			}
		});
		sideSize.setMajorTickSpacing(40);
		sideSize.setMinorTickSpacing(10);
		sideSize.setPaintTicks(true);
		sideSize.setPaintLabels(true);
		sideSize.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel sideSizeLabel = new JLabel("Adjust size of each dot");
		sideSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		sideSizePanel = new JPanel();
		sideSizePanel.setLayout(new BoxLayout(sideSizePanel, BoxLayout.Y_AXIS));
		sideSizePanel.add(sideSizeLabel);
		sideSizePanel.add(sideSize);
		
		// slider for border size
		borderSize = new JSlider(JSlider.HORIZONTAL, 0, 30, dots.getBorder());
		borderSize.setSnapToTicks(true);
		borderSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				dots.setBorder(borderSize.getValue());
			}
		});
		borderSize.setMajorTickSpacing(10);
		borderSize.setMinorTickSpacing(2);
		borderSize.setPaintTicks(true);
		borderSize.setPaintLabels(true);
		borderSize.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel borderSizeLabel = new JLabel("Adjust border around dots");
		borderSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		borderSizePanel = new JPanel();
		borderSizePanel.setLayout(new BoxLayout(borderSizePanel, BoxLayout.Y_AXIS));
		borderSizePanel.add(borderSizeLabel);
		borderSizePanel.add(borderSize);
		
		// rainbow button
		rainbow = new JCheckBox("Rainbow Mode");
		rainbow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dots.toggleRainbow();
				if (rainbow.isSelected()) {
					chase.setSelected(false);
					chaseSpeed.setEnabled(false);
					chaseSpeedLabel.setEnabled(false);
				}
			}
		});
		
		// chase button
		chase = new JCheckBox("Chase Mode");
		chase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dots.toggleChase();
				if (chase.isSelected()) {
					rainbow.setSelected(false);
					chaseSpeed.setEnabled(true);
					chaseSpeedLabel.setEnabled(true);
				}
			}
		});

		// mode buttons
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));
		buttonPanel.add(rainbow);
		buttonPanel.add(chase);
		
		// chase speed chooser
		chaseSpeed = new JComboBox<String>(new String[]{"1", "2", "3", "4", "5"});
		chaseSpeed.setSelectedIndex(dots.getChaseSpeed() - 1);
		chaseSpeed.setEnabled(false);
		chaseSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = ((JComboBox<?>)e.getSource()).getSelectedIndex();
				dots.setChaseSpeed(index + 1);
			}
		});
		chaseSpeedLabel = new JLabel("Adjust chase speed");
		chaseSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		chaseSpeedLabel.setEnabled(false);
		chaseSpeedPanel = new JPanel();
		chaseSpeedPanel.setLayout(new GridLayout(2, 1));
		chaseSpeedPanel.add(chaseSpeedLabel);
		chaseSpeedPanel.add(chaseSpeed);
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{sideSizePanel, borderSizePanel, buttonPanel, chaseSpeedPanel};
	}
}