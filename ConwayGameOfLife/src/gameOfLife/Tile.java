package gameOfLife;

import java.awt.Color;

public interface Tile {	
	public static final Color ALIVE_COLOR = Color.black;
	public static final Color DEAD_COLOR = Color.white;
	
	int getXPos();
	int getYPos();
	
	boolean isAlive();
	void setAlive(boolean status);
	default void toggleAlive() {
		if (isAlive()) {
			setAlive(false);
		} else {
			setAlive(true);
		}
	}
	
}
