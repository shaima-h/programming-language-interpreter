
/**
 * Represents an integer node.
 * @author shaimahussaini
 */
public class IntegerNode extends Node {

	/**
	 * Number value the node represents.
	 */
	int number;
	
	/**
	 * Constructor.
	 * @param num Number value of node.
	 */
	public IntegerNode(int num) {
		this.number = num;
	}
	
//	/**
//	 * Returns number value.
//	 * @return Value.
//	 */
//	public int getNum() {
//		return number;
//	}
	
	@Override
	/**
	 * Returns string representing a Node.
	 */
	public String toString() {
		return Integer.toString(number);
	}
}
