
/**
 * Represents a char node.
 * @author shaimahussaini
 */
public class CharNode extends Node {

	/**
	 * Char value.
	 */
	char chr;
	
	/**
	 * Constructor.
	 * @param chr
	 */
	public CharNode(char chr) {
		this.chr = chr;
	}
	
	public String toString() {
		return "CharNode(" + chr + ")";
	}
	
	

}
