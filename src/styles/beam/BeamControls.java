package styles.beam;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import styles.Controls;

public class BeamControls extends JPanel implements Controls {
	
	private JPanel checkBoxPanel, maxBeamPanel, stylePanel, alignPanel;
	private JCheckBox flicker, fillArc, drawBorder;
	private JRadioButton style1, style2;
	private ButtonGroup styleGroup;
	private JSlider maxBeamSize;
	private JTextField align;
	private JLabel alignLabel;
	private String prevText;
	private Beam beam;
	
	public BeamControls(Beam beam) {
		this.beam = beam;
		createPanel();
	}
	
	public void createPanel() {
		// toggle flickering
		flicker = new JCheckBox("Flicker");
		flicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setFlicker(flicker.isSelected());
			}
		});
		checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		checkBoxPanel.add(flicker);
		
		// toggle fill arc and draw border
		fillArc = new JCheckBox("Fill Arc");
		fillArc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setFillArc(fillArc.isSelected());
			}
		});
		fillArc.setSelected(true);
		drawBorder = new JCheckBox("Draw Border");
		drawBorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setDrawBorder(drawBorder.isSelected());
			}
		});
		drawBorder.setSelected(true);
		checkBoxPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		checkBoxPanel.add(fillArc);
		checkBoxPanel.add(drawBorder);
		
		// choose between 2 different styles
		style1 = new JRadioButton("Style 1");
		style1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setStyle(1);
				maxBeamSize.setEnabled(true);
				align.setEnabled(false);
			}
		});
		style2 = new JRadioButton("Style 2");
		style2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beam.setStyle(2);
				maxBeamSize.setEnabled(false);
				align.setEnabled(true);
			}
		});
		styleGroup = new ButtonGroup();
		styleGroup.add(style1);
		styleGroup.add(style2);
        int currStyle = beam.getStyle();
        if (currStyle == 1) {
			style1.setSelected(true);
		} else if (currStyle == 2) {
			style2.setSelected(true);
		}
        JLabel styleLabel = new JLabel("Choose a Style", SwingConstants.CENTER);
        stylePanel = new JPanel();
        stylePanel.setLayout(new BoxLayout(stylePanel, BoxLayout.Y_AXIS));
        stylePanel.add(styleLabel);
        stylePanel.add(style1);
        stylePanel.add(style2);
		
		// slider for beam number
		maxBeamSize = new JSlider(JSlider.HORIZONTAL, 1, 8, beam.getMaxBeams());
		maxBeamSize.setSnapToTicks(true);
		maxBeamSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				beam.setMaxBeams(maxBeamSize.getValue());
			}
		});
		maxBeamSize.setMajorTickSpacing(1);
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
		
		// choose which beams to align
		align = new JTextField();
		align.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				String text = align.getText();
				if (!isCorrectFormat(text)) {
					alignLabel.setText("<html><bold><center>Uh oh! Looks like the<br/>"
							+ "grouping you entered contains invalid<br/>"
							+ "characters! Please remove them.</center></bold></html>");
					alignLabel.setForeground(Color.RED);
				} else if (!notTooManyNums(text)) {
					alignLabel.setText("<html><bold><center>Uh oh! Looks like you entered<br/>"
							+ "too many numbers! Each number<br/>"
							+ "(1-6) should be entered only once.</center></bold></html>");
					alignLabel.setForeground(Color.RED);
				} else if (!onlyOneOfEachNum(text)) {
					alignLabel.setText("<html><bold><center>Uh oh! Looks like you entered a<br/>"
							+ "number more than once! Each number<br/>"
							+ "(1-6) should be entered only once.</center></bold></html>");
					alignLabel.setForeground(Color.RED);
				} else {
					alignLabel.setText("<html><center>Adjust which beams are aligned!<br/>"
							+ "Group beams together to align them<br/>"
							+ "ex: 13 25 46</center></html>");
					alignLabel.setForeground(Color.BLACK);
					text = fillInMissing(text);
					if (!text.equals(prevText)) {
						beam.setAlignments(convertToArray(text));
						prevText = text;
					}
				}
			}
			private boolean isCorrectFormat(String s) {
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					if (!(Character.isDigit(c) || Character.isWhitespace(c))) {
						return false;
					}
				}
				return true;
			}
			private boolean notTooManyNums(String s) {
				int count = 0;
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					if (Character.isDigit(c)) {
						count++;
					}
				}
				return count <= 6;
			}
			private boolean onlyOneOfEachNum(String s) {
				for (int i = 1; i <= 6; i++) {
					if (s.indexOf(i+"") != s.lastIndexOf(i+"")) {
						return false;
					}
				}
				return true;
			}
			private String fillInMissing(String s) {
				s = s.trim();
				for (int i = 1; i <= 6; i++) {
					if (!s.contains(i + "")) {
						s += " " + i;
					}
				}
				return s.trim();
			}
			private int[][] convertToArray(String s) {
				String[] data = s.split(" ");
				int nonEmpty = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i].length() > 0) {
						nonEmpty++;
					}
				}
				int[][] newAlignment = new int[nonEmpty][];
				int count = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i].length() > 0) {
						newAlignment[count] = new int[data[i].length()];
						for (int j = 0; j < data[i].length(); j++) {
							newAlignment[count][j] = Integer.parseInt(data[i].charAt(j) + "");
						}
						count++;
					}
				}
				return newAlignment;
			}
		});
		align.setText("1 2 3 4 5 6");
		prevText = align.getText();
		alignLabel = new JLabel("<html><center>Adjust which beams are aligned!<br/>"
				+ "Group beams together to align them<br/>ex: 13 25 46</center></html>");
		alignLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		alignPanel = new JPanel();
		alignPanel.setLayout(new BoxLayout(alignPanel, BoxLayout.Y_AXIS));
		alignPanel.add(alignLabel);
		alignPanel.add(align);
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{checkBoxPanel, stylePanel, maxBeamPanel, alignPanel};
	}
}