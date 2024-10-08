
/**
 * Represents a boolean expression;
 * @author shaimahussaini
 */
public class BooleanExpressionNode extends Node {
	
	/**
	 * Left side of expression.
	 */
	Node left;
	
	/**
	 * Condition.
	 */
	MathOpNode.Operator condition;
	
	/**
	 * Right side of expression.
	 */
	Node right;
	
	/**
	 * Constructor.
	 * @param left Left side of expression.
	 * @param condition Condition.
	 * @param right Right side of expression.
	 */
	public BooleanExpressionNode(Node left, MathOpNode.Operator condition, Node right) {
		this.left = left;
		this.condition = condition;
		this.right = right;
	}

	public String toString() {
		return "BooleanExpressionNode(" + left + ", " + condition + ", " + right + ")";
	}

}
