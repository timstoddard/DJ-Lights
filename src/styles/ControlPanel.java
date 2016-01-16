package styles;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.Frame;
import styles.beam.BeamControls;
import styles.blades.BladesControls;
import styles.dots.DotsControls;
import styles.madness.MadnessControls;
import styles.rgb.RGBControls;
import styles.spinner.SpinnerControls;
import styles.strobe.StrobeControls;

public class ControlPanel extends JPanel {
	
	private JPanel basicControlsPanel, currEffectControlsPanel;
	private BasicControls basicControls;
	private Controls[] controls;
	
	public ControlPanel(Frame f) {
		basicControlsPanel = new JPanel();
		currEffectControlsPanel = new JPanel();
		
		basicControls = new BasicControls(f);
		for (JPanel panel : basicControls.getPanels()) {
			basicControlsPanel.add(panel);
		}
		add(basicControlsPanel);
		
		controls = new Controls[]{
				new BeamControls(f.getLights().getBeam()),
				new BladesControls(f.getLights().getBlades()),
				new DotsControls(f.getLights().getDots()),
				new MadnessControls(f.getLights().getMadness()),
				new RGBControls(f.getLights().getRGB()),
				new SpinnerControls(f.getLights().getSpinner()),
				new StrobeControls(f.getLights().getStrobe())};
		updateCurrEffectPanel(f.getStyle());
		
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void update(int style) {
		updateCurrEffectPanel(style);
		updateStyleChooserIndex(style);
	}
	
	public void updateCurrEffectPanel(int style) {
		if (controls[style].getPanels().length == 0) {
			remove(currEffectControlsPanel);
		} else {
			currEffectControlsPanel.removeAll();
			currEffectControlsPanel.revalidate();
			for (JPanel panel : controls[style].getPanels()) {
				currEffectControlsPanel.add(panel);
			}
			currEffectControlsPanel.setBorder(BorderFactory.createLoweredBevelBorder());
			add(currEffectControlsPanel);
		}
		revalidate();
		repaint();
	}
	
	public void setFullScreenUnselected() {
		basicControls.setFullScreenUnselected();
	}
	
	public void updateStyleChooserIndex(int style) {
		basicControls.updateStyleChooserIndex(style);
	}
	
	public int length() {
		return controls.length;
	}
}