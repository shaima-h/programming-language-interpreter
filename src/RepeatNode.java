import java.util.ArrayList;
import java.util.List;

/**
 * Represents repeat statement.
 * @author shaimahussaini
 */
public class RepeatNode extends StatementNode {

	/**
	 * Boolean expression.
	 */
	BooleanExpressionNode expression;
	
	/**
	 * Statements inside repeat statement.
	 */
	List<StatementNode> statements = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param expression Boolean expression.
	 * @param statements Statements inside repeat statement.
	 */
	public RepeatNode(BooleanExpressionNode expression, List<StatementNode> statements) {
		this.expression = expression;
		this.statements = statements;
	}
	
	public String toString() {
		return "RepeatNode(" + expression + ", STATEMENTS: " + statements + ")";
	}

}
