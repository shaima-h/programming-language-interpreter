
public class BoolDataType extends InterpreterDataType {

	/**
	 * Value of data.
	 */
	boolean bool;

	/**
	 * Constructor.
	 * @param value
	 */
	public BoolDataType(boolean bool) {
		this.bool = bool;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(bool);
	}

	@Override
	public void fromString(String input) {
	}
	
	
}
