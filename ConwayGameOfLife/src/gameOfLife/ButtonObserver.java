package gameOfLife;

public interface ButtonObserver {
	void buttonAction(ButtonEvent b);
	void addToButtonObservable(ButtonObservable b);
	void removeFromButtonObservable(ButtonObservable b);
}
