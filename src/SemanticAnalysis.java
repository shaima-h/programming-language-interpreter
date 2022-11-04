import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SemanticAnalysis {
	
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
	
	
	public static VariableNode.Type getTargetType(FunctionNode function, String varName) {
		
		List<VariableNode> localVars = function.localVars;
		
		for(int i = 0; i < localVars.size(); i++) {
			if(localVars.get(i).name.equals(varName)) {
				return localVars.get(i).type;
			}
		}
		
		return null;
	}
	
	
	public static void checkAssignmentExpression(String varName, VariableNode.Type targetType, Node root) throws Exception {
//		System.out.println("targetType: " + targetType);
		
		//leaf node
	    if(root instanceof FloatNode) {
	    	if(targetType != VariableNode.Type.REAL) {
	    		throw new Exception("Error: " + root + ", cannot assign real number to variable " + varName);
	    	}
	    }
	    else if(root instanceof IntegerNode) {
	    	if(targetType != VariableNode.Type.INTEGER) {
	    		throw new Exception("Error: " + root + ", cannot assign integer number to variable " + varName);
	    	}
	    }
	    else if(root instanceof BoolNode) {
	    	if(targetType != VariableNode.Type.BOOLEAN) {
	    		throw new Exception("Error: " + root + ", cannot assign boolean value to variable " + varName);
	    	}
	    }
	    else if(root instanceof StringNode || root instanceof CharNode) {
	    	if(targetType != VariableNode.Type.STRING && targetType != VariableNode.Type.CHARACTER) {
	    		throw new Exception("Error: " + root + ", cannot assign string or char value to variable " + varName);
	    	}
	    }
	    
	    if(root instanceof MathOpNode) {
	    	if(targetType != VariableNode.Type.BOOLEAN) {
		    	//check left subtree
		    	checkAssignmentExpression(varName, targetType, ((MathOpNode) root).getLeft());
			 
			    //check right subtree
		    	checkAssignmentExpression(varName, targetType, ((MathOpNode) root).getRight());
	    	}
		   
	    }
	    		
	}

}
