package styles.blades;

import javax.swing.JPanel;
import styles.Controls;

public class BladesControls extends JPanel implements Controls {
	
	private Blades blades;
	
	public BladesControls(Blades blades) {
		this.blades = blades;
		createPanel();
	}
	
	public void createPanel() {
		
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{};
	}
}