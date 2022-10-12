
/**
 * Represents float data type at interpretation time.
 * @author shaimahussaini
 *
 */
public class FloatDataType extends InterpreterDataType {

	/**
	 * Value of data.
	 */
	float value;

	/**
	 * Constructor.
	 * @param value
	 */
	public FloatDataType(float value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void fromString(String input) {
		value = Float.parseFloat(input);
	}
	
	
}
