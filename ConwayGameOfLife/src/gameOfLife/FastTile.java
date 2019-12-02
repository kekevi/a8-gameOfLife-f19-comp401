package gameOfLife;

public class FastTile implements Tile {
	private boolean _isAlive;
	private int _tileX;
	private int _tileY;
	
	public FastTile(int x, int y, boolean alive) {
		_tileX = x;
		_tileY = y;
		_isAlive = alive;
	}
	public FastTile(int x, int y) {
		this(x, y, false);
	}
	
	@Override
	public int getXPos() {
		return _tileX;
	}

	@Override
	public int getYPos() {
		return _tileY;
	}

	@Override
	public boolean isAlive() {
		return _isAlive;
	}

	@Override
	public void setAlive(boolean status) {
		_isAlive = status;
	}

}
