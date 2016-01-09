package styles.spinner;
import java.awt.Component;
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
import styles.Controls;

public class SpinnerControls extends Controls {
	
	private JPanel layersPanel, radiusPanel, rotSpeedPanel;
	private JSlider layers, radius, rotSpeed;
	
	public SpinnerControls(Frame f) {
		super(f);
		createPanel();
	}
	
	public void createPanel() {
		super.removeAll();
		super.basicPanel();
		
		// slider for number of layers
		layers = new JSlider(JSlider.HORIZONTAL, 2, 8, getFrame().getLights().getSpinner().getLayers());
		layers.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				getFrame().getLights().getSpinner().setLayers(layers.getValue());
			}
		});
		layers.setMajorTickSpacing(1);
		layers.setPaintTicks(true);
		layers.setPaintLabels(true);
		layers.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel layersLabel = new JLabel("Adjust number of layers");
		layersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		layersPanel = new JPanel();
		layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
		layersPanel.add(layersLabel);
		layersPanel.add(layers);
		
		// slider for the radius
		radius = new JSlider(JSlider.HORIZONTAL, 10, 40, getFrame().getLights().getSpinner().getRadius());
		radius.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				getFrame().getLights().getSpinner().setRadius(radius.getValue());
			}
		});
		radius.setMajorTickSpacing(5);
		radius.setMinorTickSpacing(1);
		radius.setPaintTicks(true);
		radius.setPaintLabels(true);
		radius.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel radiusLabel = new JLabel("Adjust the radii of the circles");
		radiusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		radiusPanel = new JPanel();
		radiusPanel.setLayout(new BoxLayout(radiusPanel, BoxLayout.Y_AXIS));
		radiusPanel.add(radiusLabel);
		radiusPanel.add(radius);
		
		// slider for the speed of rotation
		rotSpeed = new JSlider(JSlider.HORIZONTAL, 1, 10, getFrame().getLights().getSpinner().getRotationSpeed());
		rotSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				getFrame().getLights().getSpinner().setRotationSpeed(rotSpeed.getValue());
			}
		});
		rotSpeed.setMajorTickSpacing(1);
		rotSpeed.setPaintTicks(true);
		rotSpeed.setPaintLabels(true);
		rotSpeed.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel rotSpeedLabel = new JLabel("Adjust rotation speed");
		rotSpeedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		rotSpeedPanel = new JPanel();
		rotSpeedPanel.setLayout(new BoxLayout(rotSpeedPanel, BoxLayout.Y_AXIS));
		rotSpeedPanel.add(rotSpeedLabel);
		rotSpeedPanel.add(rotSpeed);
		
		addPanels(new JPanel[]{layersPanel, radiusPanel, rotSpeedPanel});
	}
}