
/**
 * Represents a variable being assigned.
 * @author shaimahussaini
 *
 */
public class VariableReferenceNode extends Node {

	/**
	 * The name of the variable.
	 */
	String name;
	
	/**
	 * Constructor
	 * @param name The name of the variable.
	 */
	public VariableReferenceNode(String name) {
		this.name = name;
	}

	/**
	 * Returns a String representation of the variable.
	 */
	public String toString() {
		return "VariableReferenceNode(" + name + ")";
	}
	
}
