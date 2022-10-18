import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interpreter.
 * @author shaimahussaini
 */
public class Interpreter {
	
	
	static HashMap<String, CallableNode> functions = new HashMap<String, CallableNode>();
	
	public Interpreter() {
		functions.put("read", new Read());
		functions.put("write", new Write());
		functions.put("squareRoot", new SquareRoot());
		functions.put("getRandom", new GetRandom());
		functions.put("integerToReal", new IntegerToReal());
		functions.put("realToInteger", new RealToInteger());
	}
	
	public void addFunction(String string, CallableNode function) {
		functions.put(string, function);
	}
	
	
	/**
	 * Resolves node.
	 * @param root Node that will be resolved.
	 * @return Float representing resolution to mathematical operation.
	 */
	public float resolve(Node root) {
			
		//empty
	    if (root == null)
	        return 0;
	 
	    //leaf node
	    if(root instanceof IntegerNode || root instanceof FloatNode) {
	    	if(root instanceof IntegerNode)
	    		return (float)(((IntegerNode) root).number);
	    	else
	    		return (float)(((FloatNode) root).number);
	    	
	    }	        
	 
	    if(root instanceof MathOpNode) {
	    	//evaluate left subtree
		    float leftEval = resolve(((MathOpNode) root).getLeft());
		 
		    //evaluate right subtree
		    float rightEval = resolve(((MathOpNode) root).getRight());
		 
		    //check operator
		    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.PLUS)
		        return leftEval + rightEval;
		 
		    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.MINUS)
		        return leftEval - rightEval;
		 
		    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.TIMES)
		        return leftEval * rightEval;
		 
		    return leftEval / rightEval;
	    }
	    
	    return 0;
		
	}
	
	
	public static void interpretFunction(FunctionNode function, List<InterpreterDataType> parameters) throws Exception {
		
		HashMap<String, InterpreterDataType> variables = new HashMap<String, InterpreterDataType>();
		
		for(int i = 0; i < function.parameters.size(); i++) {
			if(function.parameters.get(i).type == VariableNode.Type.REAL) {
				variables.put(function.parameters.get(i).name,
					new FloatDataType(((FloatNode) (function.parameters.get(i).value)).number));
			}
			else if(function.parameters.get(i).type == VariableNode.Type.INTEGER) {
				variables.put(function.parameters.get(i).name,
					new IntDataType(((IntegerNode) (function.parameters.get(i).value)).number));
			}
		}
		
		for(int i = 0; i < function.localVars.size(); i++) { //value = initial value or 0 if no value was set
			if(function.localVars.get(i).type == VariableNode.Type.REAL) {
				variables.put(function.localVars.get(i).name,
					new FloatDataType(((FloatNode) (function.localVars.get(i).value)).number));
			}
			else if(function.localVars.get(i).type == VariableNode.Type.INTEGER) {
				variables.put(function.localVars.get(i).name,
					new IntDataType(((IntegerNode) (function.localVars.get(i).value)).number));
			}
		}
		
		interpretBlock(function.statements, variables);
		
	}
	
	//static?
	public static void interpretBlock(List<StatementNode> statements, HashMap<String, InterpreterDataType> variables) throws Exception {
		
		boolean variadic = false;
		
		for(int i = 0; i < statements.size(); i++) {
			if(statements.get(i) instanceof FunctionCallNode) {
				CallableNode functionDef = 
					functions.get(((FunctionCallNode) statements.get(i)).name);
				if(functionDef instanceof BuiltInFunctionNode && 
					!((BuiltInFunctionNode) functionDef).variadic || 
					!(functionDef instanceof BuiltInFunctionNode)) {
					
					if(functionDef.parameters.size() != variables.size()) { //??? variables size??? doesnt variables include local vars and not just parameters
						throw new Exception("Invalid number of parameters");
					}
				}
				else {
					variadic = true;
				}
				
				List<InterpreterDataType> values = new ArrayList<>();
				
				for(int j = 0; j < ((FunctionCallNode) statements).parameters.size(); j++) {
					
					if(((FunctionCallNode) statements).parameters.get(j) instanceof VariableReferenceNode) { //var
						
						if(variables.get(((VariableReferenceNode) (((FunctionCallNode) statements).parameters.get(j))).name) instanceof IntDataType) {
							values.add(new IntDataType(((IntDataType) variables.get(((VariableReferenceNode) (((FunctionCallNode) statements).parameters.get(j))).name)).value));
						}
						else { //FloatDataType
							values.add(new FloatDataType(((FloatDataType) variables.get(((VariableReferenceNode) (((FunctionCallNode) statements).parameters.get(j))).name)).value));
						}
					}
					else { //constant
						if(((FunctionCallNode) statements).parameters.get(j) instanceof IntegerNode) {
							values.add(new IntDataType(
								((IntegerNode) ((FunctionCallNode) statements).parameters.get(j)).number));
						}
						else if(((FunctionCallNode) statements).parameters.get(j) instanceof FloatNode) {
							values.add(new FloatDataType(
									((FloatNode) ((FunctionCallNode) statements).parameters.get(j)).number));
							}
					}
					
				}
				
				if(functionDef instanceof BuiltInFunctionNode) {
					((BuiltInFunctionNode) functions.get(functionDef.name)).execute(values);
				}
				else {
					// interpret function????
				}
				
				for(int j = 0; j < values.size(); j++) {
					if(variadic || (/*called function is marked as VAR and  the invocation is marked as VAR*/)) {
						if(values.get(j) instanceof FloatDataType) {
							variables.put(null, null);
							//Update the working variable value with the values “passed back” from the function
						}
					}
					
					
				}
				
				
				
				
				
				
			}
			else {
				break;
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
