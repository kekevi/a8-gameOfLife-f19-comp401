package gameOfLife;


import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	public GameFrame() {
		setTitle("Conway's Game of Life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			URL iconURL = getClass().getResource("/gameOfLife/icons/conwayIcon32x.png");
			// iconURL is null when not found
			ImageIcon icon = new ImageIcon(iconURL);
			setIconImage(icon.getImage());
		} catch (RuntimeException e) {
			System.out.println("Missing icon.");
		}
	}
}
