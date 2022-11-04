
/**
 * Represents a variable.
 * @author shaimahussaini
 */
public class VariableNode extends Node {
	
	/**
	 * Name of variable.
	 */
	String name;
	
	/**
	 * Represents if variable is a constant or not.
	 * For parameters in function definition: represents if var (false) or not (true).
	 */
	boolean constant;
	
	/**
	 * Value of variable.
	 */
	Node value;
	
	/**
	 * Represents data type.
	 * @author shaimahussaini
	 */
	public enum Type {
		INTEGER, REAL, BOOLEAN, STRING, CHARACTER;
	}
	
	/**
	 * Enum representing data type.
	 */
	Type type;
	
	/**
	 * Constructor.
	 * @param name Name of variable.
	 * @param type Data type of variable.
	 * @param constant Represents if variable is constant.
	 * @param value Value of variable.
	 */
	public VariableNode(String name, Type type, boolean constant, Node value) {
		this.name = name;
		this.type = type;
		this.constant = constant;
		this.value = value;
	}
	
	@Override
	/**
	 * Returns String representation of VariableNode.
	 */
	public String toString() {
		return "VariableNode(" + name + ", " + type + ", " + constant + ", " + value + ")";
	}

	
	
}
