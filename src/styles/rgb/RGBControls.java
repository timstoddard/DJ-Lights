package styles.rgb;

import javax.swing.JPanel;
import main.Frame;
import styles.BasicControls;
import styles.Controls;

public class RGBControls extends JPanel implements Controls {
	
	private RGB rgb;
	
	public RGBControls(RGB rgb) {
		this.rgb = rgb;
		createPanel();
	}
	
	public void createPanel() {
		// no controls currently
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{};
	}
}