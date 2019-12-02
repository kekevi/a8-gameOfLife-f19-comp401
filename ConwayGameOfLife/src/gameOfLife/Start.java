package gameOfLife;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Start {
	
	
	public static void main(String[] args) {
		JFrame main_frame = new GameFrame();
		
		
		Grid grid = new FastGrid(100, 100);
		ButtonObservable topBar = new TopBarWidget();
		ButtonObservable bottomBar = new BottomBarWidget();
		LifeController controller = new LifeController(grid, topBar);
		controller.addToButtonObservable(bottomBar);
		controller.addValueWrappers(((ValueWrappingObject) topBar).getWrappers());
		
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		window.add((JPanel) topBar, BorderLayout.NORTH);
		window.add((JPanel) grid, BorderLayout.CENTER);
		window.add((JPanel) bottomBar, BorderLayout.SOUTH);
		main_frame.add(window);
		main_frame.pack();
		main_frame.setVisible(true);
	}

}
