
/**
 * Represents a boolean node.
 * @author shaimahussaini
 */
public class BoolNode extends Node {

	/**
	 * Boolean value.
	 */
	boolean bool;
	
	/**
	 * Constructor.
	 * @param bool
	 */
	public BoolNode(boolean bool) {
		this.bool = bool;
	}
	
	public String toString() {
		return "BoolNode(" + bool + ")";
	}
	
	

}
