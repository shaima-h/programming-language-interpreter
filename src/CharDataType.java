
public class CharDataType extends InterpreterDataType {

	/**
	 * Value of data.
	 */
	char chr;

	/**
	 * Constructor.
	 * @param value
	 */
	public CharDataType(char chr) {
		this.chr = chr;
	}
	
	@Override
	public String toString() {
		return Character.toString(chr);
	}

	@Override
	public void fromString(String input) {
		chr = input.charAt(0);
	}
	
	
}
