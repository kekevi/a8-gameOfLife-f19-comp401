package gameOfLife;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JGrid extends JPanel implements Grid {
	private static final int D_SCREEN_WIDTH = 700;
	private static final int D_SCREEN_HEIGHT = 700;
	
	private Tile[][] _tiles;

	public JGrid(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Invalid dimensions.");
		}
		
		setLayout(new GridLayout(height, width));
		_tiles = new JTile[width][height];
		
		Dimension preferred_size = new Dimension(D_SCREEN_WIDTH/width, D_SCREEN_HEIGHT/height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				_tiles[x][y] = new JTile(x,y);
				((JTile) _tiles[x][y]).setPreferredSize(preferred_size);
				this.add((JTile) _tiles[x][y]);
			}
		}
	}
	
	@Override
	public Iterator<Tile> iterator() {
		return new GridIterator(this);
	}

	@Override
	public Tile[][] getTiles() {
		return _tiles;
	}

	@Override
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= getGridWidth() || y < 0 || y >= getGridHeight()) {
			throw new IllegalArgumentException("Invalid coords in grid.");
		}
		return _tiles[x][y];
	}

	@Override
	public int getGridWidth() {
		return _tiles.length;
	}

	@Override
	public int getGridHeight() {
		return _tiles[0].length;
	}
	
	@Override
	public void updateGrid() {
		//no need to update as tiles are UI components too.
	}
	
	@Override
	public void resetGrid(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Invalid dimensions.");
		}
		
		this.removeAll();
		_tiles = new JTile[width][height];
		setLayout(new GridLayout(height, width));
		Dimension preferred_size = new Dimension(D_SCREEN_WIDTH/width, D_SCREEN_HEIGHT/height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				_tiles[x][y] = new JTile(x,y);
				((JTile) _tiles[x][y]).setPreferredSize(preferred_size);
				this.add((JTile) _tiles[x][y]);
			}
		}
		revalidate();
		repaint();
	}

	@Override
	public boolean getShowGrid() {
		return false;
	}

	@Override
	public void setShowGrid(boolean mode) {
		System.out.println("JGrid does not support show grid, use FastGrid instead.");
	}


}
