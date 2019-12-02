package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FastGrid extends JPanel implements Grid, MouseListener {
	private final static Color ALIVE_COLOR = Color.black;
	private final static Color DEAD_COLOR = Color.white;
	private static final int D_SCREEN_WIDTH = 800;
	private static final int D_SCREEN_HEIGHT = 800;
	
	private Tile[][] _tiles;
	private boolean _gridMode;
	
	public FastGrid(int tileWidth, int tileHeight) {
		if (tileWidth < 1 || tileHeight < 1) {
			throw new IllegalArgumentException("Invalid dimensions.");
		}

		_tiles = new FastTile[tileWidth][tileHeight];
		addMouseListener(this);
		
		Dimension preferred_size = new Dimension(D_SCREEN_WIDTH, D_SCREEN_HEIGHT);
		setPreferredSize(preferred_size);
		
		for (int y = 0; y < tileHeight; y++) {
			for (int x = 0; x < tileWidth; x++) {
				_tiles[x][y] = new FastTile(x,y);
			}
		}
		
		updateGrid();
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		/*Steps I need to do:
		 * 1. Count how many pixels can fit on the screen (getWidth())
		 * 2. Find the unit pixel length integerized. (for each direction)
		 * 3. Get x and y coords of click events and see if they match a tile
		 */
		int unitXPixel = (int) getWidth()/getGridWidth();
		int unitYPixel = (int) getHeight()/getGridHeight();
		
		int tileX = e.getX()/unitXPixel;
		int tileY = e.getY()/unitYPixel;
		
		if (tileX < getGridWidth() && tileY < getGridHeight()) {
			getTile(tileX, tileY).toggleAlive();
		}
		
		updateGrid();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		//double tilePixWidth = getWidth()/getGridWidth();
		//double tilePixHeight = getHeight()/getGridHeight();
		
		setBackground(DEAD_COLOR);
		g2d.setColor(ALIVE_COLOR);
		
		int normalPixelXLength = getWidth() / getGridWidth();
		int normalPixelYLength = getHeight() / getGridHeight();
		
		if (!_gridMode) {
			for (Tile t : this) {
				if (t.isAlive()) {
					g2d.fillRect(normalPixelXLength * t.getXPos(), normalPixelYLength * t.getYPos(), 
								 normalPixelXLength, normalPixelYLength);
				} 
			}
		} else {
			for (Tile t : this) {
				if (t.isAlive()) {
					g2d.fillRect(normalPixelXLength * t.getXPos(), normalPixelYLength * t.getYPos(), 
								 normalPixelXLength, normalPixelYLength);
				} else {
					g2d.drawRect(normalPixelXLength * t.getXPos(), normalPixelYLength * t.getYPos(), 
								 normalPixelXLength, normalPixelYLength);
				}
			}
		}
	}
	
	@Override
	public void updateGrid() {
		repaint();
	}

	@Override
	public void resetGrid(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Invalid dimensions.");
		}
		
		_tiles = new Tile[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				_tiles[x][y] = new FastTile(x,y);
			}
		}
		
		updateGrid();
	}

	@Override
	public boolean getShowGrid() {
		return _gridMode;
	}

	@Override
	public void setShowGrid(boolean mode) {
		_gridMode = mode;
		updateGrid();
	}
}


