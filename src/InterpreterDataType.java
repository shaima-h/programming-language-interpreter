
/**
 * Represents data stored at interpretation time.
 * @author shaimahussaini
 */
public abstract class InterpreterDataType {
	
	/**
	 * Returns String representing data value.
	 */
    public abstract String toString();
    
    /**
     * Sets the value of the data type by parsing the String input.
     * @param input
     */
    public abstract void fromString(String input);
}
