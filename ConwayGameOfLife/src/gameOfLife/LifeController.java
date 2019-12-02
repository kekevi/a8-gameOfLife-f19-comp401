package gameOfLife;

import java.util.ArrayList;
import java.util.List;

import gameOfLife.ValueWrapper.ValueType;

//import java.util.ArrayList;

public class LifeController implements ButtonObserver {
	private final static double D_RANDOM_THRESHOLD = .5;
	
	private Grid _grid;
	private double _randomThreshold;
	private ValueWrapper _minSurvive;
	private ValueWrapper _maxSurvive;
	private ValueWrapper _minReproduce;
	private ValueWrapper _maxReproduce;
	private boolean _torusMode;
	
	public LifeController(Grid model, ButtonObservable view, double randomThreshold) {
		if (model == null || view == null) {
			throw new IllegalArgumentException("Null grid and/or UI");
		}
		if (randomThreshold < 0 || randomThreshold > 1) {
			throw new IllegalArgumentException("Invalid percent dead.");
		}
		_grid = model;
		_randomThreshold = randomThreshold;
		_torusMode = false;
		_minSurvive = new ValueWrapper(Properties.D_MIN_SURVIVE);
		_maxSurvive = new ValueWrapper(Properties.D_MAX_SURVIVE);
		_minReproduce = new ValueWrapper(Properties.D_MIN_REPRODUCE);
		_maxReproduce = new ValueWrapper(Properties.D_MAX_REPRODUCE);

		view.addButtonObserver(this);
	}
	public LifeController(Grid model, ButtonObservable view) {
		this(model, view, D_RANDOM_THRESHOLD);
	}
	
	/**
	 * Controller gets the first few threshold JTextFields in order.
	 * @param list
	 */
	public void addValueWrappers(List<ValueWrapper> list) {
		for (ValueWrapper v : list) {
			ValueType type = v.getValueType();
			if (type == ValueType.MIN_SURVIVE && !_minSurvive.hasField()) {
				_minSurvive = v;
			} else if (type == ValueType.MAX_SURVIVE && !_maxSurvive.hasField()) {
				_maxSurvive = v;
			} else if (type == ValueType.MIN_REPRODUCE && !_minReproduce.hasField()) {
				_minReproduce = v;
			} else if (type == ValueType.MAX_REPRODUCE && !_maxReproduce.hasField()) {
				_maxReproduce = v;
			}
		}
	}
	
	@Override
	public void buttonAction(ButtonEvent b) {
		if (b.isNextTick()) {
			nextTick();
		} else if (b.isClear()) {
			clearGrid();
		} else if (b.isRandom()) {
			clearGrid();
			for (Tile t : _grid) {
				if (Math.random() > _randomThreshold) {
					t.setAlive(true);
				}
			}
		} else if (b.isCreate()) {
			_grid.resetGrid(((CreateButton) b).getWidth(), ((CreateButton) b).getHeight());
		} else if (b.isTorusClick()) {
			_torusMode = ((TorusCheckBox) b).getTorusMode();
		} else if (b.isGridClick()) {
			_grid.setShowGrid(((GridCheckBox) b).getGridMode());
		}
		_grid.updateGrid();
	}

	private void clearGrid() {
		for (Tile t : _grid) {
			t.setAlive(false);
		}
	}
	
	private void nextTick() {
		List<Tile> markedToToggle = new ArrayList<Tile>();
		for (Tile t : _grid) {
			int surrounding = countSurroundingAlive(t);
			if (t.isAlive()) {
				if (surrounding < _minSurvive.getValue() || surrounding > _maxSurvive.getValue()) {
					//t.setAlive(false);
					markedToToggle.add(t);
				}
			} else {
				if (surrounding >= _minReproduce.getValue() && surrounding <= _maxReproduce.getValue()) {
					//t.setAlive(true);
					markedToToggle.add(t);
				}
			}
		}
		for (Tile t: markedToToggle) {
			t.toggleAlive();
		}
	}
	
	@Override
	public void addToButtonObservable(ButtonObservable b) {
		b.addButtonObserver(this);
	}

	@Override
	public void removeFromButtonObservable(ButtonObservable b) {
		b.removeButtonObserver(this);
	}
	
	private int countSurroundingAlive(Tile t) {
		if (_torusMode) {
			return torusAliveCounter(t);
		}
		
		int count = 0;
		int width = _grid.getGridWidth();
		int height = _grid.getGridHeight();
		for (int x = t.getXPos()-1 ; x <= t.getXPos()+1 ; x++) {
			for (int y = t.getYPos()-1; y <= t.getYPos()+1 ; y++) {
				if (x >= 0 && x < width && y >= 0 && y < height) {
					if (_grid.getTile(x,y).isAlive()) {
						count++;
					}
				}
			}
		}
		if (t.isAlive()) {
			count--;
		}
		return count;
	}
	
	private int torusAliveCounter(Tile t) {
		int count = 0;
		int width = _grid.getGridWidth();
		int height = _grid.getGridHeight();
		for (int x = t.getXPos()-1 ; x <= t.getXPos()+1 ; x++) {
			for (int y = t.getYPos()-1; y <= t.getYPos()+1 ; y++) {
				int testX = x;
				int testY = y;
				
				if (testX < 0) {
					testX = width - 1;
				} else if (testX > width - 1) {
					testX = 0;
				}
				
				if (testY < 0) {
					testY = height - 1;
				} else if (testY > height - 1) {
					testY = 0;
				}
				
				if (_grid.getTile(testX, testY).isAlive()) {
					count++;
				}
			}
		}
		if (t.isAlive()) {
			count--;
		}
		return count;
	}
}
