
/**
 * Represents string data type at interpretation time.
 * @author shaimahussaini
 */
public class StringDataType extends InterpreterDataType {

	/**
	 * Value of data.
	 */
	String str;

	/**
	 * Constructor.
	 * @param value
	 */
	public StringDataType(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}

	@Override
	public void fromString(String input) {
		str = input;
	}
	
	
}
