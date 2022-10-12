import java.util.ArrayList;
import java.util.List;

/**
 * Represents while statement.
 * @author shaimahussaini
 */
public class WhileNode extends StatementNode {

	/**
	 * Boolean expression.
	 */
	BooleanExpressionNode expression;
	
	/**
	 * Statements inside while statement.
	 */
	List<StatementNode> statements = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param expression Boolean expression.
	 * @param statements Statements inside while statement.
	 */
	public WhileNode(BooleanExpressionNode expression, List<StatementNode> statements) {
		this.expression = expression;
		this.statements = statements;
	}
	
	public String toString() {
		return "WhileNode(" + expression + ", STATEMENTS: " + statements + ")";
	}

}
