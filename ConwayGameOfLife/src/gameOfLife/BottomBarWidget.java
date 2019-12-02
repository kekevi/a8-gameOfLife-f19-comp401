package gameOfLife;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A view element.
 * Holds the controls for each tick in the Game of Life.
 * @author KhaMobile
 *
 */
@SuppressWarnings("serial")
public class BottomBarWidget extends JPanel implements ButtonObservable, ActionListener, ChangeListener{
	private JLabel _message;
	private JSlider _slider;
	private int _rate;
	private boolean _isPlaying;
	private JButton _playPause;
	private JButton _nextTick;
	private Timer _timer;
	
	private List<ButtonObserver> _listeners;
	
	public BottomBarWidget() {

		_listeners = new ArrayList<ButtonObserver>();
		
		setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		_message = new JLabel("Hit [> || for autotick. Hit >> for next tick. Use the slider to set tick speed.");
		leftPanel.add(_message);
		add(leftPanel, BorderLayout.WEST);
		
		_slider = new JSlider(10, 1000, 10);
		_slider.addChangeListener(this);
		_playPause = new JButton("[> ||");
		_playPause.addActionListener(this);
		_nextTick = new JButton(" >> ");
		_nextTick.addActionListener(this);
		
		JPanel rightPanel = new JPanel();
		rightPanel.add(_slider);
		rightPanel.add(_playPause);
		rightPanel.add(_nextTick);
		add(rightPanel, BorderLayout.EAST);
		
		_rate = _slider.getValue();
		_isPlaying = false;
		
		_timer = new Timer(_rate, this);
		_timer.setRepeats(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ButtonEvent b = null;
		if (e.getSource() == _playPause) {
			if (_isPlaying) {
				_isPlaying = false;
				pause();
			} else {
				_isPlaying = true;
				play();
			}
			return;
		} else if (e.getSource() == _nextTick || e.getSource() == _timer) {
			b = new NextTickButton();
		}
		
		for (ButtonObserver bo : _listeners) {
			bo.buttonAction(b);
		}
	}

	private void pause() {
		_timer.stop();
		_message.setText("Autotick paused.");
	}

	private void play() {
		_timer.setDelay(_rate);
		_timer.start();
		_message.setText("Autotick playing. Ticking every " + _rate + "ms.");
	}

	@Override
	public void addButtonObserver(ButtonObserver b) {
		_listeners.add(b);
	}

	@Override
	public void removeButtonObserver(ButtonObserver b) {
		_listeners.remove(b);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!_slider.getValueIsAdjusting()) {
			_rate = _slider.getValue();
		}
	}

}
