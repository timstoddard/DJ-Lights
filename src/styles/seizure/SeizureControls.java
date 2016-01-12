package styles.seizure;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.*;
import styles.Controls;

public class SeizureControls extends Controls implements ChangeListener, ActionListener {
	
	private JPanel nPanel, borderPanel, cornerPanel, gridPanel, gridAndCheckPanel, gridLabelPanel;
	private JSlider nSize, borderSize, cornerRound;
	private JRadioButton leftUp, leftStraight, leftDown, upStraight, center, downStraight, rightUp, rightStraight, rightDown;
	private JCheckBox random;
	
	public SeizureControls(Frame f) {
		super(f);
		createPanel();
	}
	
	public void createPanel() {
		super.removeAll();
		super.basicPanel();
		
		// slider for n size
		nSize = new JSlider(JSlider.HORIZONTAL, 0, 100, getFrame().getLights().getSeizure().getN());
		nSize.addChangeListener(this);
		nSize.setMajorTickSpacing(50);
		nSize.setMinorTickSpacing(10);
		nSize.setPaintTicks(true);
		nSize.setPaintLabels(true);
		nSize.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel nLabel = new JLabel("Adjust number of rectangles");
		nLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nPanel = new JPanel();
		nPanel.setLayout(new BoxLayout(nPanel, BoxLayout.Y_AXIS));
		nPanel.add(nLabel);
		nPanel.add(nSize);
		
		// slider for border size
		borderSize = new JSlider(JSlider.HORIZONTAL, 0, 10, getFrame().getLights().getSeizure().getBorderSize());
		borderSize.setSnapToTicks(true);
		borderSize.addChangeListener(this);
		borderSize.setMajorTickSpacing(5);
		borderSize.setMinorTickSpacing(1);
		borderSize.setPaintTicks(true);
		borderSize.setPaintLabels(true);
		borderSize.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel borderLabel = new JLabel("Adjust border width");
		borderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		borderPanel = new JPanel();
		borderPanel.setLayout(new BoxLayout(borderPanel, BoxLayout.Y_AXIS));
		borderPanel.add(borderLabel);
		borderPanel.add(borderSize);
		
		// slider for corner rounding
		cornerRound = new JSlider(JSlider.HORIZONTAL, 0, 20, getFrame().getLights().getSeizure().getCornerSize());
		cornerRound.setSnapToTicks(true);
		cornerRound.addChangeListener(this);
		cornerRound.setMajorTickSpacing(5);
		cornerRound.setMinorTickSpacing(1);
		cornerRound.setPaintTicks(true);
		cornerRound.setPaintLabels(true);
		cornerRound.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel cornerLabel = new JLabel("Adjust corner rounding");
		cornerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cornerPanel = new JPanel();
		cornerPanel.setLayout(new BoxLayout(cornerPanel, BoxLayout.Y_AXIS));
		cornerPanel.add(cornerLabel);
		cornerPanel.add(cornerRound);
		
		// 3x3 grid with options for direction of motion, option to randomize pattern
		leftUp = new JRadioButton();
		leftUp.addActionListener(this);
		leftStraight = new JRadioButton();
		leftStraight.addActionListener(this);
        leftDown = new JRadioButton();
		leftDown.addActionListener(this);
		upStraight = new JRadioButton();
		upStraight.addActionListener(this);
        center = new JRadioButton();
		center.addActionListener(this);
		//center.setSelected(true);
        downStraight = new JRadioButton();
		downStraight.addActionListener(this);
        rightUp = new JRadioButton();
        rightUp.addActionListener(this);
		rightStraight = new JRadioButton();
		rightStraight.addActionListener(this);
        rightDown = new JRadioButton();
		rightDown.addActionListener(this);
        
		Box left = Box.createVerticalBox();
		left.add(leftUp);
		left.add(leftStraight);
		left.add(leftDown);
		Box middle = Box.createVerticalBox();
		middle.add(upStraight);
		middle.add(center);
		middle.add(downStraight);
		Box right = Box.createVerticalBox();
		right.add(rightUp);
		right.add(rightStraight);
		right.add(rightDown);

		ButtonGroup group = new ButtonGroup();
        group.add(leftUp);
        group.add(leftStraight);
        group.add(leftDown);
        group.add(upStraight);
        group.add(center);
        group.add(downStraight);
        group.add(rightUp);
        group.add(rightStraight);
        group.add(rightDown);
        
        random = new JCheckBox("Randomize");
        random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFrame().getLights().getSeizure().updateRandomize(true);
				group.clearSelection();
			}
		});
        random.setSelected(true);
        
        gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(1, 3));
        gridPanel.add(left);
        gridPanel.add(middle);
        gridPanel.add(right);
        JLabel gridLabel = new JLabel("Direction");
        gridLabel.setHorizontalAlignment(JLabel.CENTER);
        gridAndCheckPanel = new JPanel(new BorderLayout());
        gridAndCheckPanel.add(gridPanel, BorderLayout.WEST);
		gridAndCheckPanel.add(random, BorderLayout.EAST);

		gridLabelPanel = new JPanel(new BorderLayout());
		gridLabelPanel.add(gridLabel, BorderLayout.NORTH);
		gridLabelPanel.add(gridAndCheckPanel, BorderLayout.SOUTH);
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{nPanel, borderPanel, cornerPanel, gridLabelPanel};
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (source.equals(nSize)) {
			getFrame().getLights().getSeizure().updateN(Math.max(1, nSize.getValue()));
		} else if (source.equals(borderSize)) {
			getFrame().getLights().getSeizure().updateBorder(borderSize.getValue());
		} else if (source.equals(cornerRound)) {
			getFrame().getLights().getSeizure().updateCorners(cornerRound.getValue());
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if ((((JRadioButton)src).equals(leftUp))) {
			getFrame().getLights().getSeizure().setDirection(true, false, true, false);
		} else if ((((JRadioButton)src).equals(leftStraight))) {
			getFrame().getLights().getSeizure().setDirection(false, false, true, false);
		} else if ((((JRadioButton)src).equals(leftDown))) {
			getFrame().getLights().getSeizure().setDirection(false, true, true, false);
		} else if ((((JRadioButton)src).equals(upStraight))) {
			getFrame().getLights().getSeizure().setDirection(true, false, false, false);
		} else if ((((JRadioButton)src).equals(center))) {
			getFrame().getLights().getSeizure().setDirection(false, false, false, false);
		} else if ((((JRadioButton)src).equals(downStraight))) {
			getFrame().getLights().getSeizure().setDirection(false, true, false, false);
		} else if ((((JRadioButton)src).equals(rightUp))) {
			getFrame().getLights().getSeizure().setDirection(true, false, false, true);
		} else if ((((JRadioButton)src).equals(rightStraight))) {
			getFrame().getLights().getSeizure().setDirection(false, false, false, true);
		} else if ((((JRadioButton)src).equals(rightDown))) {
			getFrame().getLights().getSeizure().setDirection(false, true, false, true);
		}
		random.setSelected(false);
		getFrame().getLights().getSeizure().updateRandomize(false);
	}
}