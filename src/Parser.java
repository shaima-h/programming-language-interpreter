import java.util.ArrayList;
import java.util.List;

//import com.sun.tools.javac.parser.Tokens.TokenKind;

/**
 * Takes a collection of tokens from Lexer and builds them into a tree of AST nodes.
 * @author shaimahussaini
 */
public class Parser {
	
	//commented out print statements were for testing purposes.
	
	/**
	 * List of tokens from Lexer.
	 */
	List<Token> tokens = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param tokens List of tokens from Lexer.
	 */
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	/**
	 * Parses tokens from Lexer.
	 * @return List of Nodes representing each mathematical expression.
	 */
	public List<Node> parse() {
		
		List<Node> nodes = new ArrayList<>();
		
		//calls expression() for every new mathematical expression
		while(tokens.size() > 0) {
			nodes.add(expression());
			matchAndRemove(Token.Type.EndOfLine);
		}
		
		return nodes;
		
	}
	
	/**
	 * Returns node representing mathematical expression.
	 * @return Node representing mathematical expression.
	 */
	public Node expression() {
//		System.out.println("called expression");
		
		Node node;
		Node left = term();
		MathOpNode.Operator operator = null;
		
		if(matchAndRemove(Token.Type.PLUS) != null) {
			operator = MathOpNode.Operator.PLUS;
		}
		else if(matchAndRemove(Token.Type.MINUS) != null) {
			operator = MathOpNode.Operator.MINUS;
		}
		else {
			return left;
		}
		
		Node right = term();
		
		node = new MathOpNode(operator, left, right);
		
		//checks for multiple operators (ex: 1+2+3)
		while(tokens.size() > 0 && matchAndRemove(Token.Type.EndOfLine) == null) {
			if(matchAndRemove(Token.Type.PLUS) != null) {
				operator = MathOpNode.Operator.PLUS;
			}
			else if(matchAndRemove(Token.Type.MINUS) != null) {
				operator = MathOpNode.Operator.MINUS;
			}
			else {
				return node;
			}
			node = new MathOpNode(operator, node, term());
		}
		
		return node;
		
	}
	
	/**
	 * Returns node representing mathematical term.
	 * @return Node representing mathematical term.
	 */
	public Node term() {
//		System.out.println("called term");
		
		Node node;
		Node left = factor();
		MathOpNode.Operator operator = null;
		
		if(matchAndRemove(Token.Type.TIMES) != null) {
			operator = MathOpNode.Operator.TIMES;
		}
		else if(matchAndRemove(Token.Type.DIVIDE) != null) {
			operator = MathOpNode.Operator.DIVIDE;
		}
		else if(matchAndRemove(Token.Type.MODULO) != null) {
			operator = MathOpNode.Operator.MODULO;
		}
		else {
			return left;
		}
		
		Node right = factor();
		
		node = new MathOpNode(operator, left, right);
		
		//checks for multiple operators (ex: 1*2*3)
		while(tokens.size() > 0 && matchAndRemove(Token.Type.EndOfLine) == null) {
			if(matchAndRemove(Token.Type.TIMES) != null) {
				operator = MathOpNode.Operator.TIMES;
			}
			else if(matchAndRemove(Token.Type.DIVIDE) != null) {
				operator = MathOpNode.Operator.DIVIDE;
			}
			else if(matchAndRemove(Token.Type.MODULO) != null) {
				operator = MathOpNode.Operator.MODULO;
			}
			else {
				return node;
			}
			node = new MathOpNode(operator, node, factor());
		}
		
		return node;
		
	}
	
	/**
	 * Returns node representing mathematical factor.
	 * @return Node representing mathematical factor.
	 */
	public Node factor() {
//		System.out.println("called factor");

		Token matchedNum = matchAndRemove(Token.Type.NUMBER);
		Node node;
		
		if(matchedNum == null) {
			Token id = matchAndRemove(Token.Type.IDENTIFIER);
			if(id != null) {
				return new VariableReferenceNode(id.getValueStr());
			}
				
			//calls expression for the expression inside parentheses
			if(matchAndRemove(Token.Type.LPAREN) != null) {
				node = expression();
				matchAndRemove(Token.Type.RPAREN);
				return node;
			}
			else {
				return null;
			}		
		}
		else if(matchedNum != null) {
			if(matchedNum.getNumType() == 'i') {
//				System.out.println("returned factor " + new IntegerNode((int)matchedNum.getValue()));
				return new IntegerNode((int)matchedNum.getValue());
			}
			else if (matchedNum.getNumType() == 'f') {
//				System.out.println("returned factor " + new FloatNode((float)matchedNum.getValue()));
				return new FloatNode((float)matchedNum.getValue());
			}
		}
		
		return null;
	}
	
	/**
	 * Removes and returns Token if next token in line is of a specific token type.
	 * @param Token type that we are looking for.
	 * @return Token that matches parameter token type.
	 */
	public Token matchAndRemove(Token.Type type) {
//		System.out.println("called matchandremove for " + type);

		if(tokens.size() > 0 && tokens.get(0).getType() == type) {
//			System.out.println("returned MaR " + tokens.get(0).getType());
			return tokens.remove(0);
		} else {
			return null;
		}
	}
	
	public void removeEndOfLines() {
		while(matchAndRemove(Token.Type.EndOfLine) != null) {
			matchAndRemove(Token.Type.EndOfLine);
		}
	}
	
	/**
	 * Makes a function AST node.
	 * @return String representing the function.
	 * @throws Exception
	 */
	public FunctionNode functionDefinition() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.DEFINE) != null) {
			FunctionNode function;
						
			Token id = matchAndRemove(Token.Type.IDENTIFIER);
			if(id == null)
				throw new Exception("Function name required.");
			
			String name = id.getValueStr();
			if(matchAndRemove(Token.Type.LPAREN) == null)
				throw new Exception("Invalid function header. L");
			
			List<VariableNode> parameters = new ArrayList<>();
			parameters = processVariables();
//			System.out.println("parameters: " + parameters);
			
			if(matchAndRemove(Token.Type.RPAREN) == null)
				throw new Exception("Invalid function header. R");
			
			List<VariableNode> localVars = new ArrayList<>();			
			List<VariableNode> constants = constants();
			
			if(constants != null)
				localVars.addAll(constants);
			
			List<VariableNode> variables = variables();
			
			if(localVars != null && variables != null) {
					localVars.addAll(variables);
			}
			else {
				localVars = variables;
			}	
			
			List<StatementNode> statements = new ArrayList<>();
			statements = bodyFunction();
			
			function = new FunctionNode(name, parameters, localVars, statements);
			
			return function;
			
		}
//		System.out.println("returned null functionDefinition");
		return null;
		
	}
	
	/**
	 * Checks for constant declarations in function.
	 * @return List of VariableNodes representing constants.
	 * @throws Exception
	 */
	public List<VariableNode> constants() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.CONSTANTS) != null) {
			List<VariableNode> constants = new ArrayList<>();
			List<VariableNode> more_constants = new ArrayList<>();
			
			constants = processConstants();
			if(constants == null)
					throw new Exception("Invalid constant declaration.");
			
			//checking if multiple constants
			more_constants = processConstants();

			while(more_constants != null) {
				constants.addAll(more_constants);
				more_constants = processConstants();
			}
			
			return constants;
		}
		
//		System.out.println("returned null constants");
		return null;
	}
	
	/**
	 * Processes constants if constants are found.
	 * @return List of VariableNodes representing constants.
	 * @throws Exception
	 */
	public List<VariableNode> processConstants() throws Exception {
		
		List<VariableNode> constants = new ArrayList<>();
		
		removeEndOfLines();
		
		Token id = matchAndRemove(Token.Type.IDENTIFIER);
		
		if(id != null) {
			if(matchAndRemove(Token.Type.EQUALS) == null)
				throw new Exception("Invalid variable declaration.");
			
			Token matchedNum = matchAndRemove(Token.Type.NUMBER);
			Node val = null;
			VariableNode.Type type = null;
			
			if(matchedNum.getNumType() == 'i') {
				val = new IntegerNode((int)matchedNum.getValue());
				type = VariableNode.Type.INTEGER;
			}
			else if (matchedNum.getNumType() == 'f') {
				val = new FloatNode((float)matchedNum.getValue());
				type = VariableNode.Type.REAL;
			}
			
			VariableNode constant = new VariableNode(id.getValueStr(), type, true, val);
			constants.add(constant);
		}
		
		matchAndRemove(Token.Type.EndOfLine);
		
		return constants;
		
	}
	
	/**
	 * Checks for variable declarations in function.
	 * @return List of VariableNodes representing variables.
	 * @throws Exception
	 */
	public List<VariableNode> variables() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.VARIABLES) != null) {
			List<VariableNode> variables = new ArrayList<>();
			List<VariableNode> more_variables = new ArrayList<>();
			
			variables = processVariables();
			
			if(variables == null)
				throw new Exception("Invalid variable declaration.");
				
			//checking for multiple variable declarations
			more_variables = processVariables();

			while(more_variables != null) {
				variables.addAll(more_variables);
				more_variables = processVariables();
			}
			
			return variables;
		}
		
//		System.out.println("returned null variables");
		return null;
	}
	
	/**
	 * Processes variables if variables found.
	 * @return List of VariableNodes representing variables.
	 * @throws Exception
	 */
	public List<VariableNode> processVariables() throws Exception {
		
		List<VariableNode> vars = new ArrayList<>();
		
		removeEndOfLines();
		
		boolean constant = true;
		if(matchAndRemove(Token.Type.VAR) != null) //use constant value in VariableNode for parameters to show if var or not
			constant = false;
		
		Token id = matchAndRemove(Token.Type.IDENTIFIER);
		List<Token> ids = new ArrayList<>();
		ids.add(id);
		
		if(id != null) {
			
			while(matchAndRemove(Token.Type.COMMA) != null) {
				Token identifier = matchAndRemove(Token.Type.IDENTIFIER);
				ids.add(new Token(Token.Type.IDENTIFIER, identifier.getValueStr()));
			}
			
			if(matchAndRemove(Token.Type.COLON) == null)
				throw new Exception("Invalid variable declaration.");
			
			Token typeI = matchAndRemove(Token.Type.INTEGER);
			Token typeR = matchAndRemove(Token.Type.REAL);
			Node val = null;
			VariableNode.Type type = null;
			
			if(typeI != null) {
				val = new IntegerNode(0);
				type = VariableNode.Type.INTEGER;
			}
			else if(typeR != null) {
				val = new FloatNode(0);
				type = VariableNode.Type.REAL;
			}
			
			for(int i = 0; i < ids.size(); i++) {
				VariableNode var = new VariableNode(ids.get(i).getValueStr(), type, constant, val);
				vars.add(var);
			}
					
			while(matchAndRemove(Token.Type.SEMICOLON) != null) {
				List<VariableNode> processVars = processVariables();
				
				if(processVars != null)
					vars.addAll(processVars);
			}
		}
		else if(id == null) {
			return null;
		}
		
		return vars;
	}
	
	/**
	 * Checks for valid body of function.
	 * @returns List of StatementNodes representing statements in a block.
	 * @throws Exception
	 */
	public List<StatementNode> bodyFunction() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.BEGIN) == null) {
			throw new Exception("Missing begin in body of function.");
		}
		
		removeEndOfLines();
		
		List<StatementNode> statements = statements();
		
		removeEndOfLines();
				
		if(matchAndRemove(Token.Type.END) == null) {
			throw new Exception("Missing end in body of function.");
		}
		matchAndRemove(Token.Type.EndOfLine);
		
//		System.out.println("returned null bodyFunction");
		return statements;
		
	}
	
	/**
	 * Returns the list of statements inside a block.
	 * @return List of StatementNodes.
	 * @throws Exception
	 */
	public List<StatementNode> statements() throws Exception {
		
		List<StatementNode> statements = new ArrayList<>();
		StatementNode statement = statement();
		
		while(statement != null) {
			statements.add(statement);
			statement = statement();
		}
		
//		System.out.println("returned null statements");
		return statements;
	}
	
	/**
	 * Returns a statement.
	 * @return A StatementNode representing a statement.
	 * @throws Exception
	 */
	public StatementNode statement() throws Exception {
		StatementNode statement = assignment();
		if(statement != null)
			return statement;
		
		statement = whileFunction();
		if(statement != null)
			return statement;
		
		statement = repeatFunction();
		if(statement != null)
			return statement;
		
		statement = forFunction();
		if(statement != null)
			return statement;
		
		statement = ifFunction();
		if(statement != null)
			return statement;
		
		statement = functionCall();
		if(statement != null)
			return statement;
		
		return null;
		
	}
	
	/**
	 * Checks if next token is equal to the given token type.
	 * @param type The token type we are checking.
	 * @return True if next token is equal, false if it is not.
	 */
	public boolean peek(Token.Type type) {
		return (tokens.size() > 1 && tokens.get(1).getType() == type);
	}
	
	/**
	 * Returns an AssignmentNode representing an assignment statement.
	 * @return An AssignmentNode representing an assignment statement.
	 * @throws Exception
	 */
	public AssignmentNode assignment() throws Exception {
		if(peek(Token.Type.ASSIGNMENT)) {
			Token id = matchAndRemove(Token.Type.IDENTIFIER);
			if(id == null)
				throw new Exception("Invalid assignment.");
			
			matchAndRemove(Token.Type.ASSIGNMENT);
			
			Node expression = expression();
			
			if(expression == null)
				throw new Exception("Invalid assignment.");
			
			matchAndRemove(Token.Type.EndOfLine);
			
			VariableReferenceNode var = new VariableReferenceNode(id.getValueStr());
			
//			System.out.println(var.toString());
//			System.out.println(expression);
			
			return new AssignmentNode(var, expression);
			
		}
		
//		System.out.println("returned null assignment");
		return null;
	}
	
//	BooleanExpression (has a left expression and a right expression and a condition)
	/**
	 * Returns a BooleanExpression, has a left expression and a right expression and a condition.
	 * @return BooleanExpression.
	 * @throws Exception
	 */
	public BooleanExpressionNode booleanExpression() throws Exception {
		Node left = expression();
		
		Token.Type condition = null;
		
		if(matchAndRemove(Token.Type.GREATER_THAN) != null) {
			condition = Token.Type.GREATER_THAN;
		}
		else if(matchAndRemove(Token.Type.GREATER_EQUAL) != null) {
			condition = Token.Type.GREATER_EQUAL;
		}
		else if(matchAndRemove(Token.Type.LESS_THAN) != null) {
			condition = Token.Type.LESS_THAN;
		}
		else if(matchAndRemove(Token.Type.LESS_EQUAL) != null) {
			condition = Token.Type.LESS_EQUAL;
		}
		else if(matchAndRemove(Token.Type.NOT_EQUALS) != null) {
			condition = Token.Type.NOT_EQUALS;
		}
		else if(matchAndRemove(Token.Type.EQUALS) != null) {
			condition = Token.Type.EQUALS;
		}
		else {
			throw new Exception("Invalid boolean expression.");
		}
		
		Node right = expression();
		
		return new BooleanExpressionNode(left, condition, right);
	}
	
	
//	While (booleanExpression and collection of statementNodes)
	/**
	 * Returns WhileNode, has a booleanExpression and collection of statementNodes.
	 * @return WhileNode.
	 * @throws Exception
	 */
	public WhileNode whileFunction() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.WHILE) == null) return null;
		
		BooleanExpressionNode booleanExp = booleanExpression();
		if(booleanExp == null)
			throw new Exception("Invalid while block.");
		
		List<StatementNode> statements = bodyFunction();
		
//		System.out.println("returned null whileFunction");
		return new WhileNode(booleanExp, statements);
		
	}
	
//	Repeat (booleanExpression and collection of statementNodes)
	/**
	 * Returns a RepeatNode, has a booleanExpression and collection of statementNodes.
	 * @return RepeatNode.
	 * @throws Exception
	 */
	public RepeatNode repeatFunction() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.REPEAT) == null) return null;
		
		List<StatementNode> statements = bodyFunction();
		
		if(matchAndRemove(Token.Type.UNTIL) == null)
			throw new Exception("Invalid repeat block.");
		
		BooleanExpressionNode booleanExp = booleanExpression();
		if(booleanExp == null)
			throw new Exception("Invalid while block.");
		
		return new RepeatNode(booleanExp, statements);
	}
	
	
//	For (variableReference, start ASTNode, end ASTNode, collection of statementNodes)
	/**
	 * Returns a ForNode, has a variableReference, start ASTNode, end ASTNode, collection of statementNodes.
	 * @return ForNode.
	 * @throws Exception
	 */
	public ForNode forFunction() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.FOR) == null) return null;
		
		Token id = matchAndRemove(Token.Type.IDENTIFIER);
		if(id == null) {
			throw new Exception("Invalid for loop.");
		}
		
		VariableReferenceNode var = new VariableReferenceNode(id.getValueStr());
		
		if(matchAndRemove(Token.Type.FROM) == null)
			throw new Exception("Invalid for block.");
		
		Node start = expression();
		
		if(matchAndRemove(Token.Type.TO) == null)
			throw new Exception("Invalid for block.");
		Node end = expression();
		
		if(start == null || end == null) {
			throw new Exception("Invalid for block.");
		}
		
		
		List<StatementNode> statements = bodyFunction();
		
		return new ForNode(var, start, end, statements);
		
	}
	
	
//	A linked list: If (booleanExpression, collection of statementNodes, ifNode)
	/**
	 * Returns an IfNode, has a booleanExpression, collection of statementNodes, ifNode.
	 * @return IfNode.
	 * @throws Exception
	 */
	public IfNode ifFunction() throws Exception {
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.IF) == null) return null;
		
		BooleanExpressionNode booleanExp = booleanExpression();
		if(booleanExp == null)
			throw new Exception("Invalid if block.");
		
		if(matchAndRemove(Token.Type.THEN) == null)
			throw new Exception("Invalid if block.");
		
		List<StatementNode> statements = bodyFunction();
		
		IfNode ifNode = new IfNode(booleanExp, statements, null);
		IfNode tail = ifNode;
		
		IfNode elsif;
		removeEndOfLines();
		while(matchAndRemove(Token.Type.ELSIF) != null) {
			BooleanExpressionNode booleanExp_elsif = booleanExpression();
			if(booleanExp == null)
				throw new Exception("Invalid elsif block.");
			
			if(matchAndRemove(Token.Type.THEN) == null)
				throw new Exception("Invalid if block.");
			
			List<StatementNode> statements_elsif = bodyFunction();
			
			elsif = new IfNode(booleanExp_elsif, statements_elsif, null);
			
			if(ifNode.getNext() == null) {
				ifNode.setNext(elsif);
				tail = elsif;
			}
			else {
				tail.setNext(elsif);
				tail = elsif;
			}
			
		}
		
		removeEndOfLines();
		
		if(matchAndRemove(Token.Type.ELSE) != null) {
			List<StatementNode> statements_else = bodyFunction();
			tail.setNext(new ElseNode(statements_else));
		}
				
		return ifNode;
		
	}

	/**
	 * Returns FunctionCallNode, representing a call to a function.
	 * @return FunctionCallNode, representing a call to a function.
	 * @throws Exception
	 */
	public FunctionCallNode functionCall() throws Exception {
		
		Token id = matchAndRemove(Token.Type.IDENTIFIER);
		List<Node> parameters = new ArrayList<>();
		
		if(id != null) {
			do {
				Node value = expression();

				if(matchAndRemove(Token.Type.VAR) != null) { //var
					VariableReferenceNode var = 
							new VariableReferenceNode(matchAndRemove(Token.Type.IDENTIFIER).getValueStr());
					parameters.add(new ParameterNode(var, true));
				}
				else if(value != null) { //identifier or constant
					parameters.add(new ParameterNode(value, false));
				}
			} while(matchAndRemove(Token.Type.COMMA) != null);
			
			return new FunctionCallNode(id.getValueStr(), parameters);
		}
		
		return null;
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
