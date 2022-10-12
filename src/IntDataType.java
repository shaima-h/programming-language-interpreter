
/**
 * Represets integer data type at interpretation time.
 * @author shaimahussaini
 */
public class IntDataType extends InterpreterDataType {
	
	/**
	 * Value of data.
	 */
	int value;
	
	/**
	 * Constructor.
	 * @param value
	 */
	public IntDataType(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void fromString(String input) {
		value = Integer.parseInt(input);
	}
	
}
