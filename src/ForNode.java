import java.util.ArrayList;
import java.util.List;

/**
 * Represents for loop.
 * @author shaimahussaini
 *
 */
public class ForNode extends StatementNode {

	/**
	 * Variable.
	 */
	VariableReferenceNode var;
	
	/**
	 * Start value.
	 */
	Node start;
	
	/**
	 * End value.
	 */
	Node end;
	
	/**
	 * Statements inside for loop.
	 */
	List<StatementNode> statements = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param var Variable
	 * @param start Start value.
	 * @param end End value.
	 * @param statements Statements inside for loop.
	 */
	public ForNode(VariableReferenceNode var, Node start, Node end, List<StatementNode> statements) {
		this.var = var;
		this.start = start;
		this.end = end;
		this.statements = statements;
	}
	
	public String toString() {
		return "ForNode(VAR: " + var + ", START: " + start + ", END: " + end + ", STATEMENTS: " + statements + ")";
	}

}
