import java.util.ArrayList;
import java.util.List;

/**
 * Represents a call to a function.
 * @author shaimahussaini
 */
public class FunctionCallNode extends StatementNode {
	
	/**
	 * Name of function.
	 */
	String name;
	
	/**
	 * Parameters of function.
	 */
	List<ParameterNode> parameters = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param name
	 * @param parameters
	 */
	public FunctionCallNode(String name, List<ParameterNode> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public String toString() {
		return "FunctionCallNode(" + name + ", PARAMS: " + parameters + ")";
	}

}
