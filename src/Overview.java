import java.awt.Graphics;

import javax.swing.JPanel;

public class Overview extends JPanel{
	public void paint(Graphics g) { 	//This will paint instead of JPanel's paint method
		//System.out.println("Overview's Repaint is happening");
		super.paint(g); 			//this paints the whole outside panel.
	}	
}
