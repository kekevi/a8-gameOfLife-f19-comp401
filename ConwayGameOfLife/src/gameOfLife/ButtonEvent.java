package gameOfLife;

public abstract class ButtonEvent {
	public boolean isClear() {
		return false;
	}
	public boolean isRandom() {
		return false;
	}
	public boolean isCreate() {
		return false;
	}
	public boolean isTorusClick() {
		return false;
	}
	public boolean isReset() {
		return false;
	}
	public boolean isPlayPause() {
		return false;
	}
	public boolean isNextTick() {
		return false;
	}
	public boolean isGridClick() {
		return false;
	}
	
}

class ClearButton extends ButtonEvent {
	public boolean isClear() {return true;}
}
class RandomButton extends ButtonEvent {
	public boolean isRandom() {return true;}
}
class TorusCheckBox extends ButtonEvent {
	private boolean _mode;
	public TorusCheckBox(boolean mode) {
		_mode = mode;
	}
	public boolean isTorusClick() {return true;}
	public boolean getTorusMode() {
		return _mode;
	}
}
class CreateButton extends ButtonEvent {
	private int _width;
	private int _height;
	public CreateButton(int width, int height) {
		_width = width;
		_height = height;
	}
	public boolean isCreate() {return true;}
	public int getWidth() {return _width;}
	public int getHeight() {return _height;}
}
class ResetButton extends ButtonEvent {
	public boolean isReset() {return true;}
}
class PlayPauseButton extends ButtonEvent {
	public boolean isPlayPause() {return true;}
}
class NextTickButton extends ButtonEvent {
	public boolean isNextTick() {return true;}
}
class GridCheckBox extends ButtonEvent {
	private boolean _mode;
	public GridCheckBox(boolean mode) {
		_mode = mode;
	}
	public boolean isGridClick() {return true;}
	public boolean getGridMode() {
		return _mode;
	}
}
