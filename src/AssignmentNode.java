
/**
 * Represents an assignment.
 * @author shaimahussaini
 */
public class AssignmentNode extends StatementNode {
	
	/**
	 * The variable being assigned.
	 */
	VariableReferenceNode target;
	
	/**
	 * The expression being assigned to the variable.
	 */
	Node expression;
	
	/**
	 * Constructor
	 * @param target The variable being assigned.
	 * @param expression The expression being assigned to the variable.
	 */
	public AssignmentNode(VariableReferenceNode target, Node expression) {
		this.target = target;
		this.expression = expression;
	}
	
	/**
	 * Returns a String representation of an AssignmentNode.
	 */
	public String toString() {
		return "AssignmentNode(" + target + ", " + expression + ")";
	}

}
