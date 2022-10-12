
/**
 * Represents a float node.
 * @author shaimahussaini
 */
public class FloatNode extends Node {
	
	/**
	 * Number value the node represents.
	 */
	private float number;
	
	/**
	 * Constructor.
	 * @param num Number value of node.
	 */
	public FloatNode(float num) {
		this.number = num;
	}

	/**
	 * Returns number value.
	 * @return Value.
	 */
	public float getNum() {
		return number;
	}
	
	@Override
	/**
	 * Returns string representing a Node.
	 */
	public String toString() {
		return Float.toString(number);
	}

}
