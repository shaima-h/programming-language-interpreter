import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Analyzes the AST to ensure types assigned to variable are correct.
 * @author shaimahussaini
 */
public class SemanticAnalysis {
	
	/**
	 * Checks all assignments in a function.
	 * @param functions
	 * @throws Exception
	 */
	public static void checkAssignments(HashMap<String, CallableNode> functions) throws Exception {
		
		//FunctionNode, XBuiltInFunctionNodeX
		
		for (CallableNode func : functions.values()) {
			if(func instanceof FunctionNode) {
				List<StatementNode> statements = new ArrayList<>();
				statements = ((FunctionNode) func).statements;
				checkStatement((FunctionNode) func, statements);
			}
		}
	}
	
	/**
	 * Recursive function to check all statements in a function.
	 * @param function
	 * @param statements
	 * @throws Exception
	 */
	public static void checkStatement(FunctionNode function, List<StatementNode> statements) throws Exception {
		
		for(int i = 0; i < statements.size(); i++) {
			StatementNode statement = statements.get(i);
		
			if(statement instanceof AssignmentNode) {
				AssignmentNode assignment = (AssignmentNode) statement;
				
				//check FunctionNode localvars for type
				VariableNode.Type targetType = getTargetType(function, assignment.target.name);
				
				//now check types of everything in assignment.expression
				checkAssignmentExpression(assignment.target.name, targetType, assignment.expression);
				
			}
			else if(statement instanceof ForNode) {
				checkStatement(function, ((ForNode) statement).statements);
			}
			else if(statement instanceof WhileNode) {
				checkStatement(function, ((WhileNode) statement).statements);
			}
			else if(statement instanceof RepeatNode) {
				checkStatement(function, ((RepeatNode) statement).statements);
			}
			else if(statement instanceof IfNode) {
				checkStatement(function, ((IfNode) statement).statements);
				IfNode ifNode = ((IfNode) statement).ifNode;
				
				while(ifNode != null) {
					checkStatement(function, ((IfNode) statement).statements);
					ifNode = ifNode.ifNode;
				}
			}
		}
		
	}
	
	/**
	 * Gets the type of the target in an assignment statement.
	 * @param function
	 * @param varName
	 * @return VariableNode.Type
	 * @throws Exception
	 */
	public static VariableNode.Type getTargetType(FunctionNode function, String varName) throws Exception {
		
		List<VariableNode> localVars = function.localVars;
		for(int i = 0; i < localVars.size(); i++) {
			if(localVars.get(i).name.equals(varName)) {
				return localVars.get(i).type;
			}
		}
		
		List<VariableNode> parameters = function.parameters;
		for(int i = 0; i < parameters.size(); i++) {
			if(parameters.get(i).name.equals(varName)) {
				return parameters.get(i).type;
			}
		}
		
		throw new Exception("Error: Variable \"" + varName + "\" not declared.");
		
	}
	
	/**
	 * Checks the expression of an assignment statement.
	 * @param varName
	 * @param targetType
	 * @param root
	 * @throws Exception
	 */
	public static void checkAssignmentExpression(String varName, VariableNode.Type targetType, Node root) throws Exception {
//		System.out.println("targetType: " + targetType);
		
		//leaf node
	    if(root instanceof FloatNode) {
	    	if(targetType != VariableNode.Type.REAL) {
	    		throw new Exception("Error: " + root + ", cannot assign real number to variable \"" + varName + "\"");
	    	}
	    }
	    else if(root instanceof IntegerNode) {
	    	if(targetType != VariableNode.Type.INTEGER) {
	    		throw new Exception("Error: " + root + ", cannot assign integer number to variable \"" + varName + "\"");
	    	}
	    }
	    else if(root instanceof BoolNode) {
	    	if(targetType != VariableNode.Type.BOOLEAN) {
	    		throw new Exception("Error: " + root + ", cannot assign boolean value to variable \"" + varName + "\"");
	    	}
	    }
	    else if(root instanceof StringNode || root instanceof CharNode) {
	    	if(targetType != VariableNode.Type.STRING && targetType != VariableNode.Type.CHARACTER) {
	    		throw new Exception("Error: " + root + ", cannot assign string or char value to variable \"" + varName + "\"");
	    	}
	    }
	    
	    if(root instanceof MathOpNode) {
	    	
	    	//checking if boolean is being assigned a boolean expression so exceptions are not thrown
	    	if(targetType != VariableNode.Type.BOOLEAN || !((MathOpNode) root).booleanOperator()) {
	    		
	    		if(targetType != VariableNode.Type.BOOLEAN && ((MathOpNode) root).booleanOperator()) { //not bool and boolOp
		    		throw new Exception("Error: Cannot assign boolean expression to variable \"" + varName + "\"");
	    		}
	    		
		    	//check left subtree
		    	checkAssignmentExpression(varName, targetType, ((MathOpNode) root).getLeft());
			 
			    //check right subtree
		    	checkAssignmentExpression(varName, targetType, ((MathOpNode) root).getRight());
	    	}
		   
	    }
	    		
	}

}
