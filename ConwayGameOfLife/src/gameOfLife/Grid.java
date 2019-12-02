package gameOfLife;

public interface Grid extends Iterable<Tile> {
	
	Tile[][] getTiles();
	Tile getTile(int x, int y);
	int getGridWidth();
	int getGridHeight();
	void updateGrid();
	boolean getShowGrid();
	void setShowGrid(boolean mode);
	
	void resetGrid(int width, int height);
	
	/*void addTileListener();
	void removeTileListener();*/
	
	//Iterator<Tile> iterator();
}
