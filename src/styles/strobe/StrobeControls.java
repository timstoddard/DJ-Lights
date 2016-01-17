package styles.strobe;
import javax.swing.JPanel;
import styles.Controls;

public class StrobeControls implements Controls {
	
	private Strobe strobe;
	
	public StrobeControls(Strobe strobe) {
		this.strobe = strobe;
		createPanel();
	}
	
	public void createPanel() {
		
	}
	
	public JPanel[] getPanels() {
		return new JPanel[]{};
	}
}