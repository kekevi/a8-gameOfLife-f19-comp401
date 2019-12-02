package gameOfLife;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GridIterator implements Iterator<Tile> {
	private Grid _grid;
	private int testX;
	private int testY;

	GridIterator(Grid grid) {
		_grid = grid;
	}
	@Override
	public boolean hasNext() {
		return (testY < _grid.getGridHeight());
	}

	@Override
	public Tile next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Tile t = _grid.getTile(testX, testY);
		if (testX < _grid.getGridWidth()-1) {
			testX++;
		} else {
			testX = 0;
			testY++;
		}
		return t;
	}
	
}
