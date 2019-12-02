package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import gameOfLife.ValueWrapper.ValueType;

/**
 * A view element.
 * Holds the 
 * @author KhaMobile
 *
 */
@SuppressWarnings("serial")
public class TopBarWidget extends JPanel implements ButtonObservable, ActionListener, ValueWrappingObject{
	private static final int D_WIDTH = 100;
	private static final int D_HEIGHT = 100;
	
	private JSpinner _width;
	private JSpinner _height;
	private JButton _create;
	private JButton _clearAll;
	private JButton _randomize;
	private JCheckBox _showGrid;
	private JCheckBox _torus;
	private JSpinner _minSurvive;
	private JSpinner _maxSurvive;
	private JSpinner _minReproduce;
	private JSpinner _maxReproduce;
	private JButton _reset;
	
	private List<ButtonObserver> _listeners;
	private List<ValueWrapper> _textFields;
	
	public TopBarWidget() {
		Font defaultFont = new Font("Dialog", Font.PLAIN, 10);
		_listeners = new ArrayList<ButtonObserver>();
		_textFields = new ArrayList<ValueWrapper>();
		
		setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		_width = new JSpinner(new SpinnerNumberModel(D_WIDTH, 1, 500, 10));
		_height = new JSpinner(new SpinnerNumberModel(D_HEIGHT, 1, 500, 10)); 
		_create = new JButton("Create");
		_create.addActionListener(this);
		_clearAll = new JButton("Clear");
		_clearAll.addActionListener(this);
		_randomize = new JButton("Random");
		_randomize.addActionListener(this);	
		
		JLabel lblDim = new JLabel("Size:");
		lblDim.setFont(defaultFont);
		
		leftPanel.add(groupSpinners(lblDim, _width, _height), BorderLayout.WEST);
		leftPanel.add(_create);
		leftPanel.add(_clearAll);
		leftPanel.add(_randomize);
		add(leftPanel, BorderLayout.WEST);
		
		
		JPanel rightPanel = new JPanel();
		_showGrid = new JCheckBox("Grid");
		_showGrid.addActionListener(this);
		
		_torus = new JCheckBox("Torus");
		_torus.addActionListener(this);
		
		JLabel lblSurvive = new JLabel("Survival:");
		lblSurvive.setFont(defaultFont);
		JLabel lblRepro = new JLabel("Reproduction:");
		lblRepro.setFont(defaultFont);
		
		_minSurvive = new JSpinner(new SpinnerNumberModel((Properties.D_MIN_SURVIVE), 0, 8, 1));
		_textFields.add(new ValueWrapper(_minSurvive, ValueType.MIN_SURVIVE));
		_maxSurvive = new JSpinner(new SpinnerNumberModel((Properties.D_MAX_SURVIVE), 0, 8, 1));
		_textFields.add(new ValueWrapper(_maxSurvive, ValueType.MAX_SURVIVE));
		_minReproduce = new JSpinner(new SpinnerNumberModel((Properties.D_MIN_REPRODUCE), 0, 8, 1));
		_textFields.add(new ValueWrapper(_minReproduce, ValueType.MIN_REPRODUCE));
		_maxReproduce = new JSpinner(new SpinnerNumberModel((Properties.D_MAX_REPRODUCE), 0, 8, 1));
		_textFields.add(new ValueWrapper(_maxReproduce, ValueType.MAX_REPRODUCE));
		
		_reset = new JButton("Reset");
		_reset.addActionListener(this);
		
		JLabel lblBounds = new JLabel("Bounds:");
		lblBounds.setFont(defaultFont);
		
		rightPanel.add(_showGrid);
		rightPanel.add(_torus);
		rightPanel.add(lblBounds);
		rightPanel.add(groupSpinners(lblSurvive, _minSurvive, _maxSurvive));
		rightPanel.add(groupSpinners(lblRepro, _minReproduce, _maxReproduce));
		rightPanel.add(_reset);
		add(rightPanel, BorderLayout.EAST);
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
	public void actionPerformed(ActionEvent e) {
		ButtonEvent b = null;
		if (e.getSource() == _create) {
			b = new CreateButton( (int) _width.getValue(), (int) _height.getValue());
		} else if (e.getSource() == _clearAll) {
			b = new ClearButton();
		} else if (e.getSource() == _randomize) {
			b = new RandomButton();
		} else if (e.getSource() == _torus) {
			b = new TorusCheckBox(_torus.isSelected());
		} else if (e.getSource() == _reset) {
			_minSurvive.setValue(Properties.D_MIN_SURVIVE);
			_maxSurvive.setValue(Properties.D_MAX_SURVIVE);
			_minReproduce.setValue(Properties.D_MIN_REPRODUCE);
			_maxReproduce.setValue(Properties.D_MAX_REPRODUCE);
			return;
		} else if (e.getSource() == _showGrid) {
			b = new GridCheckBox(_showGrid.isSelected());
		}
		
		for (ButtonObserver bo : _listeners) {
			bo.buttonAction(b);
		}
	}

	@Override
	public List<ValueWrapper> getWrappers() {
		return _textFields;
	}
	
	private JPanel groupSpinners(JLabel label, JSpinner left, JSpinner right) {
		JPanel group = new JPanel();
		group.setLayout(new BorderLayout());
		group.add(label, BorderLayout.NORTH);
		group.add(left, BorderLayout.WEST);
		group.add(right, BorderLayout.EAST);
		return group;
	}
}
