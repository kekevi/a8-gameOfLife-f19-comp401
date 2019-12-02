package gameOfLife;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Wraps JSpinners and ensures that they only hold numbers.
 * Has an enum ValueType that categorizes the textbox's specified values.
 * @author KhaMobile
 *
 */
public class ValueWrapper implements ChangeListener {
	public enum ValueType {MIN_SURVIVE, MAX_SURVIVE, MIN_REPRODUCE, MAX_REPRODUCE};
	
	private JSpinner _field;
	private int _value;
	private ValueType _type;
	private boolean _hasField;
	
	public ValueWrapper(JSpinner f, ValueType v) {
		if (f == null || v == null) {
			throw new IllegalArgumentException("Must have JSpinner or ValueType!");
		}
		
		_field = f;
		_field.addChangeListener(this);
		_type = v;
		_hasField = true;
		_value = (int) f.getValue();
	}
	public ValueWrapper(int value) {
		_value = value;
		_hasField = false;
	}
	
	public int getValue() {
		return _value;
	}
	
	public void setValue(int n) {
		_value = n;
	}
	
	public ValueType getValueType() {
		return _type;
	}
	
	public boolean hasField() {
		return _hasField;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		_value = (int) _field.getValue();
	}
}
