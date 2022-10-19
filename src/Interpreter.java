import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interpreter.
 * @author shaimahussaini
 */
public class Interpreter {
	
	/**
	 * Represents all callable functions.
	 */
	static HashMap<String, CallableNode> functions = new HashMap<String, CallableNode>();
	
//	public Interpreter() {		
//		functions.put("read", new Read());
//		functions.put("write", new Write());
//		functions.put("squareRoot", new SquareRoot());
//		functions.put("getRandom", new GetRandom());
//		functions.put("integerToReal", new IntegerToReal());
//		functions.put("realToInteger", new RealToInteger());
//	}
	
//	public void addFunction(String string, CallableNode function) {
//		functions.put(string, function);
//	}
	
	
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
	
	/**
	 * Takes a function node and list of function call parameters and interprets the function.
	 * @param function Function to be interpreted.
	 * @param parameters Function call parameters.
	 * @throws Exception
	 */
	public static void interpretFunction(FunctionNode function, List<InterpreterDataType> parameters) throws Exception {
		
		HashMap<String, InterpreterDataType> variables = new HashMap<String, InterpreterDataType>();
		
		//PARAMETERS
		for(int i = 0; i < parameters.size(); i++) {
			if(function.parameters.get(i).type == VariableNode.Type.REAL) {
				if(parameters.get(i) instanceof FloatDataType) {
					variables.put(function.parameters.get(i).name,
							new FloatDataType(((FloatDataType) parameters.get(i)).value));
					System.out.println("adding: " + ((FloatDataType) parameters.get(i)).value);
				} else {
					throw new Exception("Error in parameters in function call.");
				}
				
			}
			else if(function.parameters.get(i).type == VariableNode.Type.INTEGER) {
				if(parameters.get(i) instanceof IntDataType) {
					variables.put(function.parameters.get(i).name,
							new IntDataType(((IntDataType) parameters.get(i)).value));
				} else {
					throw new Exception("Error in parameters in function call.");
				}
			}
		}
		
		//LOCAL VARS
		if(function.localVars != null) {
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
		}
		
		interpretBlock(function.statements, variables);
		
	}
	
	/**
	 * Takes a list of the function's statements and the variables of the program and executes the function.
	 * @param statements Function's statements.
	 * @param variables Current working variables.
	 * @throws Exception
	 */
	public static void interpretBlock(List<StatementNode> statements, HashMap<String, InterpreterDataType> variables) throws Exception {
				
		//looping through all statements
		for(int i = 0; i < statements.size(); i++) {
			boolean variadic = false; //if function is variadic
			
			if(statements.get(i) instanceof FunctionCallNode) {
				FunctionCallNode functionCall = (FunctionCallNode) statements.get(i);
				
//				System.out.println("name: " + functionCall.name);

				CallableNode functionDef = functions.get(functionCall.name); //function definition
				
//				System.out.println("def:" + functionDef.parameters);
//				System.out.println(((BuiltInFunctionNode) functionDef).variadic);
				
				//checking if number of parameters matches
				if((functionDef instanceof BuiltInFunctionNode && 
					!((BuiltInFunctionNode) functionDef).variadic) || 
					!(functionDef instanceof BuiltInFunctionNode)) {
					
					if(functionCall.parameters.size() != functionDef.parameters.size()) {
						throw new Exception("Invalid function call to " + functionCall.name);
					}
				}
				else {
					variadic = true; //variadic function (read or write)
				}
				
				List<InterpreterDataType> values = new ArrayList<>();
				
				//checking all parameters of functionCall
				for(int j = 0; j < functionCall.parameters.size(); j++) {
					
//					System.out.println(functionCall.parameters.get(j));
					
					if(functionCall.parameters.get(j).var) { //var
						if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof IntDataType) {
							values.add(new IntDataType(((IntDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
						}
						else { //FloatDataType
							values.add(new FloatDataType(((FloatDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
						}
												
					}
					else { //constant, not var
						if((functionCall).parameters.get(j).parameter instanceof IntegerNode) {
							values.add(new IntDataType(
								((IntegerNode) (functionCall).parameters.get(j).parameter).number));
						}
						else if((functionCall).parameters.get(j).parameter instanceof FloatNode) {
							values.add(new FloatDataType(
									((FloatNode) (functionCall).parameters.get(j).parameter).number));
						}
						else if((functionCall).parameters.get(j).parameter instanceof VariableReferenceNode) {
							if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof IntDataType) {
								values.add(new IntDataType(((IntDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
							}
							else { //FloatDataType
								values.add(new FloatDataType(((FloatDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
							}
						}
					}
					
				}
				
				System.out.println();
				System.out.println("Function called: " + functionCall.name);
				System.out.println("Values before function call: " + values);
				
//				System.out.println(functions);
//				System.out.println("def: " + functionDef);
//				System.out.println("statements: " + statements);
				
				//calling execute for built-in function
				if(functionDef instanceof BuiltInFunctionNode builtin) {
//					((BuiltInFunctionNode) functions.get(functionDef.name)).execute(values);
//					System.out.println("***");
//					System.out.println(values);
//					System.out.println(functionDef);
					
					builtin.execute(values);
					
//					System.out.println(values);
				}
				else { //calling interpreter for user defined function
					interpretFunction((FunctionNode) functions.get(functionDef.name), values);
//					System.out.println(variables);
				}
				
//				System.out.println(functionDef);
				
				//changing var values
				for(int j = 0; j < values.size(); j++) {
					/* || called function is marked as VAR and  the invocation is marked as VAR*/
//					System.out.println(values + " * " + functionDef.parameters + " * " + functionCall.parameters);
					if(variadic || (!functionDef.parameters.get(j).constant && functionCall.parameters.get(j).var)) {
//						System.out.println(variables);
						if(variadic) {
//							if ((!functionDef.parameters.get(j).constant ^ functionCall.parameters.get(j).var)) {
//								throw new Exception("Parameter of function call to " + functionCall.name + " must be var.");
//							}
							if(functionCall.parameters.get(j).parameter instanceof VariableReferenceNode) {
								variables.replace(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name, values.get(j));
							}
//							else if(functionCall.parameters.get(j).parameter instanceof FloatNode) {
//								variables.replace(((FloatNode)(functionCall).parameters.get(j).parameter).name, values.get(j));
//							}
//							else if(functionCall.parameters.get(j).parameter instanceof IntegerNode) {
//								variables.replace(((IntegerNode)(functionCall).parameters.get(j).parameter).name, values.get(j));
//							}
						}
						else {
							variables.replace(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name, values.get(j));
						}
							//Update the working variable value with the values “passed back” from the function
					}
					else if ((!functionDef.parameters.get(j).constant ^ functionCall.parameters.get(j).var)) {
						throw new Exception("Parameter of function call to " + functionCall.name + " must be var.");
					}
//					
				}			
				
				System.out.println("Values after function call: " + values);				
				
				
			}
			else {
				continue;
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
