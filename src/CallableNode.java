import java.util.ArrayList;
import java.util.List;

/**
 * Represents a callable function.
 * @author shaimahussaini
 */
public abstract class CallableNode extends Node {
	
	/**
	 * Name of function.
	 */
	String name;
	
	/**
	 * Parameters of function.
	 */
	List<VariableNode> parameters = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public CallableNode() {
	}
	
	/**
	 * Constructor.
	 * @param name
	 * @param parameters
	 */
	public CallableNode(String name, List<VariableNode> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

}
