package gameOfLife;

public interface ButtonObservable {
	void addButtonObserver(ButtonObserver b);
	void removeButtonObserver(ButtonObserver b);
}
