import java.util.ArrayList;
import java.util.List;

/**
 * Represents if statement.
 * @author shaimahussaini
 */
public class IfNode extends StatementNode {

	/**
	 * Boolean expression.
	 */
	BooleanExpressionNode expression;
	
	/**
	 * Statements inside if statement.
	 */
	List<StatementNode> statements = new ArrayList<>();
	
	/**
	 * Next elsif or else node.
	 */
	IfNode ifNode;
	
	/**
	 * Constructor.
	 */
	public IfNode() {
	}
	
	/**
	 * Constructor.
	 * @param expression Boolean expression.
	 * @param statements Statements inside if statement.
	 * @param ifNode Next elsif else node.
	 */
	public IfNode(BooleanExpressionNode expression, List<StatementNode> statements, IfNode ifNode) {
		this.expression = expression;
		this.statements = statements;
		this.ifNode = ifNode;
	}
	
	/**
	 * Sets next elsif or else node.
	 * @param ifNode
	 */
	public void setNext(IfNode ifNode) {
		this.ifNode = ifNode;
	}
	
	/**
	 * Returns next elsif or else node.
	 * @return
	 */
	public IfNode getNext() {
		return this.ifNode;
	}
	
	public String toString() {
		return "IfNode(" + expression + ", STATEMENTS: " + statements + ", " + ifNode + ")";
	}

}
