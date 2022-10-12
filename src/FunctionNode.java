import java.util.ArrayList;
import java.util.List;

/**
 * Represents function AST node.
 * @author shaimahussaini
 */
public class FunctionNode extends CallableNode  {
	
//	/**
//	 * Name of function.
//	 */
//	String name;
//	
//	/**
//	 * Parameters of function.
//	 */
//	List<VariableNode> parameters = new ArrayList<>();
	
	/**
	 * Local variables of function.
	 */
	List<VariableNode> localVars = new ArrayList<>();
	
	/**
	 * Statements in function.
	 */
	List<StatementNode> statements = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param name Name of function.
	 * @param parameters Parameters of function.
	 * @param localVars Local variables of function.
	 */
	public FunctionNode(String name, List<VariableNode> parameters, List<VariableNode> localVars, 
			List<StatementNode> statements) {
		this.name = name;
		this.parameters = parameters;
		this.localVars = localVars;
		this.statements = statements;
	}
	
	/**
	 * Returns String representation of FunctionNode.
	 */
	public String toString() {
		return "FunctionNode(" + name + ", PARAMS: " + parameters + ", LOCAL VARS: " + localVars
				+ ", STATEMENTS: " + statements + ")";
	}


}
