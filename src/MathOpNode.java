
/**
 * Represents a mathematical operation.
 * @author shaimahussaini
 */
public class MathOpNode extends Node {

	/**
	 * Operator type.
	 */
	public enum Operator {
		PLUS, MINUS, TIMES, DIVIDE, MODULO;
	}
	
	/**
	 * Operator type.
	 */
	Operator operator;
	
	/**
	 * Left child.
	 */
	private Node left;
	
	/**
	 * Right child.
	 */
	private Node right;
	
	/**
	 * Constructor
	 * @param op Operator type.
	 * @param num1 Left child.
	 * @param num2 Right child.
	 */
	public MathOpNode(Operator op, Node num1, Node num2) {
		this.operator = op;
		this.left = num1;
		this.right = num2;
	}
	
	/**
	 * Returns operator type.
	 * @return Operator type.
	 */
	public Operator getOperator() {
		return operator;
	}
	
	/**
	 * Returns left child.
	 * @return Left child.
	 */
	public Node getLeft() {
		return this.left;
	}
	
	/**
	 * Returns right child.
	 * @return Right child.
	 */
	public Node getRight() {
		return this.right;
	}
	
	/**
	 * Returns operator as a string.
	 * @return String representing operator.
	 */
	public String opToString() {
		switch(operator) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case TIMES:
				return "*";
			case DIVIDE:
				return "/";	
			case MODULO:
				return "mod";
			default:
				return "";
		}
	}
	
	@Override
	/**
	 * Returns string representing a MathOpNode.
	 */
	public String toString() {
		return ("MathOpNode(" 
				+ this.opToString() + ", " + this.left.toString()+ ", " + this.right.toString() + ")");
	}
	
}
