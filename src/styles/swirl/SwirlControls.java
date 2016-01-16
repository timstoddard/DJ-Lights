package styles.swirl;

import javax.swing.JPanel;
import styles.Controls;

public class SwirlControls implements Controls {
	
	private Swirl swirl;
	
	public SwirlControls(Swirl swirl) {
		this.swirl = swirl;
		createPanel();
	}
	
	public void createPanel() {
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{};
	}
}