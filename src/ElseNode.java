import java.util.ArrayList;
import java.util.List;

/**
 * Represents else statement.
 * @author shaimahussaini
 *
 */
public class ElseNode  extends IfNode {

	/**
	 * Statements inside else node.
	 */
	List<StatementNode> statements = new ArrayList<>();

	/**
	 * Constructor.
	 * @param statements Statements inside else node.
	 */
	public ElseNode(List<StatementNode> statements) {
		this.statements = statements;
	}
	
	public String toString() {
		return "ElseNode(" + statements + ")";
	}

}
