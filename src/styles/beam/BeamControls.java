package styles.beam;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Frame;
import styles.BasicControls;
import styles.Controls;

public class BeamControls extends JPanel implements Controls {
	
	private JPanel maxBeamPanel, stylePanel;
	private JSlider maxBeamSize;
	private JRadioButton style1, style2;
	ButtonGroup styleGroup;
	private Beam beam;
	
	public BeamControls(Beam beam) {
		this.beam = beam;
		createPanel();
	}
	
	public void createPanel() {
		// choose between 2 different styles
		style1 = new JRadioButton("Style 1");
		style1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setStyle(1);
				maxBeamSize.setEnabled(true);
			}
		});
		style2 = new JRadioButton("Style 2");
		style2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setStyle(2);
				maxBeamSize.setEnabled(false);
			}
		});
		ButtonGroup group = new ButtonGroup();
        group.add(style1);
        group.add(style2);
        int currStyle = beam.getStyle();
        if (currStyle == 1) {
			style1.setSelected(true);
		} else if (currStyle == 2) {
			style2.setSelected(true);
		}
        JLabel styleLabel = new JLabel("Choose a Style", SwingConstants.CENTER);
        stylePanel = new JPanel();
        stylePanel.setLayout(new BorderLayout());
        stylePanel.add(styleLabel, BorderLayout.NORTH);
        stylePanel.add(style1, BorderLayout.WEST);
        stylePanel.add(style2, BorderLayout.EAST);
		
		// slider for beam number
		maxBeamSize = new JSlider(JSlider.HORIZONTAL, 0, 10, beam.getMaxBeams());
		maxBeamSize.setSnapToTicks(true);
		maxBeamSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				beam.setMaxBeams(maxBeamSize.getValue());
			}
		});
		maxBeamSize.setMajorTickSpacing(5);
		maxBeamSize.setMinorTickSpacing(1);
		maxBeamSize.setPaintTicks(true);
		maxBeamSize.setPaintLabels(true);
		maxBeamSize.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel nLabel = new JLabel("Adjust max number of beams");
		nLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		maxBeamPanel = new JPanel();
		maxBeamPanel.setLayout(new BoxLayout(maxBeamPanel, BoxLayout.Y_AXIS));
		maxBeamPanel.add(nLabel);
		maxBeamPanel.add(maxBeamSize);
		if (style2.isSelected()) {
			maxBeamSize.setEnabled(false);
		}
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{stylePanel, maxBeamPanel};
	}
}