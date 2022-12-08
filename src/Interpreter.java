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
	public static float resolve(Node root, HashMap<String, InterpreterDataType> variables) {
			
		//empty
	    if (root == null)
	        return 0;
	 
	    //leaf node
	    if(root instanceof IntegerNode || root instanceof FloatNode || root instanceof VariableReferenceNode) {
	    	if(root instanceof IntegerNode)
	    		return (float)(((IntegerNode) root).number);
	    	else if(root instanceof FloatNode)
	    		return (float)(((FloatNode) root).number);
	    	else if(root instanceof VariableReferenceNode) {
	    		if(variables.get(((VariableReferenceNode) root).name) instanceof FloatDataType) {
		    		return (float) ((FloatDataType) (variables.get(((VariableReferenceNode) root).name))).value;
	    		}
	    		else {
		    		return (float) ((IntDataType) (variables.get(((VariableReferenceNode) root).name))).value;
	    		}
	    	}
	    }
	 
	    if(root instanceof MathOpNode) {
	    	//evaluate left subtree
		    float leftEval = resolve(((MathOpNode) root).getLeft(), variables);
		 
		    //evaluate right subtree
		    float rightEval = resolve(((MathOpNode) root).getRight(), variables);
		 
		    //check operator
		    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.PLUS)
		        return leftEval + rightEval;
		 
		    else if (((MathOpNode) root).getOperator() == MathOpNode.Operator.MINUS)
		        return leftEval - rightEval;
		 
		    else if (((MathOpNode) root).getOperator() == MathOpNode.Operator.TIMES)
		        return leftEval * rightEval;
		 
		    else if (((MathOpNode) root).getOperator() == MathOpNode.Operator.DIVIDE)
		        return leftEval / rightEval;
		    
		    	
	    }
	    return 0;
		
	}
	
	/**
	 * Resolves a string expression.
	 * @param root
	 * @param variables
	 * @return String.
	 */
	public static String resolveString(Node root, HashMap<String, InterpreterDataType> variables) {
				
		//empty
	    if (root == null)
	        return "";
	 
	    //leaf node
	    if(root instanceof StringNode) {
	    		return ((StringNode) root).str;
	    }
	    else if(root instanceof CharNode) {
    		return Character.toString(((CharNode) root).chr);
	    }
	    else if(root instanceof VariableReferenceNode) {
    		if(variables.get(((VariableReferenceNode) root).name) instanceof StringDataType) {
	    		return ((StringDataType) (variables.get(((VariableReferenceNode) root).name))).str;
    		}
    		else if(variables.get(((VariableReferenceNode) root).name) instanceof CharDataType) {
	    		return Character.toString(((CharDataType) (variables.get(((VariableReferenceNode) root).name))).chr);
    		}
    	}
	    
	    if(root instanceof MathOpNode) {
	    	//evaluate left subtree
		    String leftEval = resolveString(((MathOpNode) root).getLeft(), variables);
		 
		    //evaluate right subtree
		    String rightEval = resolveString(((MathOpNode) root).getRight(), variables);
		 
		    //check operator
		    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.PLUS)
		        return leftEval + rightEval;
	    }
	    
	    return "";
	}
	
	/**
	 * Resolves a boolean expression.
	 * @param root
	 * @param variables
	 * @return boolean.
	 */
	public static boolean resolveBoolean(Node root, HashMap<String, InterpreterDataType> variables) {
		
//		System.out.println("resolveBoolean: " + root);
		
		if(root instanceof BoolNode) {
    		return ((BoolNode) root).bool;
	    }
	    else if(root instanceof VariableReferenceNode) {
    		if(variables.get(((VariableReferenceNode) root).name) instanceof BoolDataType) {
	    		return ((BoolDataType) (variables.get(((VariableReferenceNode) root).name))).bool;
    		}
    	}
		
		 if(root instanceof MathOpNode) {
		    	//evaluate left subtree
			    float leftEval = resolve(((MathOpNode) root).getLeft(), variables);
			 
			    //evaluate right subtree
			    float rightEval = resolve(((MathOpNode) root).getRight(), variables);
			 
			    //check operator
			    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.GREATER_THAN)
			        return leftEval > rightEval;
			 
			    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.LESS_THAN)
			        return leftEval < rightEval;
			 
			    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.GREATER_EQUAL)
			        return leftEval >= rightEval;
			 
			    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.LESS_EQUAL)
			        return leftEval <= rightEval;
			    
			    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.NOT_EQUALS)
			        return leftEval != rightEval;
			    
			    if (((MathOpNode) root).getOperator() == MathOpNode.Operator.EQUALS)
			        return leftEval == rightEval;
		    }
		
		return false;
		
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
			else if(function.parameters.get(i).type == VariableNode.Type.BOOLEAN) {
				if(parameters.get(i) instanceof BoolDataType) {
					variables.put(function.parameters.get(i).name,
							new BoolDataType(((BoolDataType) parameters.get(i)).bool));
				} else {
					throw new Exception("Error in parameters in function call.");
				}
			}
			else if(function.parameters.get(i).type == VariableNode.Type.STRING) {
				if(parameters.get(i) instanceof StringDataType) {
					variables.put(function.parameters.get(i).name,
							new StringDataType(((StringDataType) parameters.get(i)).str));
				} else {
					throw new Exception("Error in parameters in function call.");
				}
			}
			else if(function.parameters.get(i).type == VariableNode.Type.CHARACTER) {
				if(parameters.get(i) instanceof CharDataType) {
					variables.put(function.parameters.get(i).name,
							new CharDataType(((CharDataType) parameters.get(i)).chr));
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
				else if(function.localVars.get(i).type == VariableNode.Type.BOOLEAN) {
					variables.put(function.localVars.get(i).name,
						new BoolDataType(((BoolNode) (function.localVars.get(i).value)).bool));
				}
				else if(function.localVars.get(i).type == VariableNode.Type.STRING) {
					variables.put(function.localVars.get(i).name,
						new StringDataType(((StringNode) (function.localVars.get(i).value)).str));
				}
				else if(function.localVars.get(i).type == VariableNode.Type.CHARACTER) {
					variables.put(function.localVars.get(i).name,
						new CharDataType(((CharNode) (function.localVars.get(i).value)).chr));
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
			
			if(statements.get(i) instanceof FunctionCallNode) {
				boolean variadic = false; //if function is variadic
				
				FunctionCallNode functionCall = (FunctionCallNode) statements.get(i);
				
//				System.out.println("name: " + functionCall.name);

				CallableNode functionDef = functions.get(functionCall.name); //function definition
				
//				System.out.println("def:" + functionDef);
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
						else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof FloatDataType) {
							values.add(new FloatDataType(((FloatDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
						}
						else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof BoolDataType) {
							values.add(new BoolDataType(((BoolDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).bool));
						}
						else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof StringDataType) {
							values.add(new StringDataType(((StringDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).str));
						}
						else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof CharDataType) {
							values.add(new CharDataType(((CharDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).chr));
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
						else if((functionCall).parameters.get(j).parameter instanceof BoolNode) {
							values.add(new BoolDataType(
									((BoolNode) (functionCall).parameters.get(j).parameter).bool));
						}
						else if((functionCall).parameters.get(j).parameter instanceof StringNode) {
							values.add(new StringDataType(
									((StringNode) (functionCall).parameters.get(j).parameter).str));
						}
						else if((functionCall).parameters.get(j).parameter instanceof CharNode) {
							values.add(new CharDataType(
									((CharNode) (functionCall).parameters.get(j).parameter).chr));
						}
						else if((functionCall).parameters.get(j).parameter instanceof VariableReferenceNode) {
							if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof IntDataType) {
								values.add(new IntDataType(((IntDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
							}
							else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof FloatDataType) {
								values.add(new FloatDataType(((FloatDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).value));
							}
							else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof BoolDataType) {
								values.add(new BoolDataType(((BoolDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).bool));
							}
							else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof StringDataType) {
								values.add(new StringDataType(((StringDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).str));
							}
							else if(variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name) instanceof CharDataType) {
								values.add(new CharDataType(((CharDataType) variables.get(((VariableReferenceNode)(functionCall).parameters.get(j).parameter).name)).chr));
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
			else if(statements.get(i) instanceof AssignmentNode) {
				
				System.out.println("Before assignment:" + variables);
				
				AssignmentNode assignment = (AssignmentNode) statements.get(i);
				
				if(variables.get(assignment.target.name) instanceof FloatDataType) {
					float result = resolve(assignment.expression, variables);
					variables.replace(assignment.target.name, new FloatDataType(result));
				}
				else if(variables.get(assignment.target.name) instanceof IntDataType) {
					int result = (int) resolve(assignment.expression, variables);
					variables.replace(assignment.target.name, new IntDataType(result));
				}
				else if(variables.get(assignment.target.name) instanceof BoolDataType) {
					boolean result = resolveBoolean(assignment.expression, variables);
					variables.replace(assignment.target.name, new BoolDataType(result));
				}
				else if(variables.get(assignment.target.name) instanceof CharDataType 
						&& assignment.expression instanceof CharNode) {
					char result = ((CharNode) assignment.expression).chr;
					variables.replace(assignment.target.name, new CharDataType(result));
				}
				else if(variables.get(assignment.target.name) instanceof StringDataType 
						|| variables.get(assignment.target.name) instanceof CharDataType) {
					String result = resolveString(assignment.expression, variables);
					variables.replace(assignment.target.name, new StringDataType(result));
				}
				
				System.out.println("After assignment:" + variables);
				System.out.println();
				
			}
			else if(statements.get(i) instanceof WhileNode) {
				WhileNode whileNode = (WhileNode) statements.get(i);
				boolean condition = evaluateBooleanExpression((BooleanExpressionNode) whileNode.expression, variables);
				
				while(condition) {
					interpretBlock(whileNode.statements, variables);
					condition = evaluateBooleanExpression(whileNode.expression, variables);
				}
			}
			else if(statements.get(i) instanceof RepeatNode) {
				RepeatNode repeatNode = (RepeatNode) statements.get(i);
				boolean condition;

				do {
					interpretBlock(repeatNode.statements, variables);
					condition = evaluateBooleanExpression(repeatNode.expression, variables);
				} while(!condition); //until condition is true
			}
			else if(statements.get(i) instanceof IfNode) {
				IfNode ifNode = (IfNode) statements.get(i);
				
				if(evaluateBooleanExpression(ifNode.expression, variables)) {
					interpretBlock(ifNode.statements, variables);
				}
				else {
					ifNode = ifNode.ifNode;
					while(ifNode != null) {
						if(ifNode instanceof ElseNode) {
							interpretBlock(((ElseNode) ifNode).statements, variables);
							break;
						}
						
						if(evaluateBooleanExpression(ifNode.expression, variables)) {
							interpretBlock(ifNode.statements, variables);
							break;
						}
						
						ifNode = ifNode.ifNode;
					}
				}
			}
			else if(statements.get(i) instanceof ForNode) {
				ForNode forNode = (ForNode) statements.get(i);
				int start = 0;
				int end = 0;
				
				if(forNode.start instanceof IntegerNode) {
					start = ((IntegerNode) forNode.start).number;
				}
				else {
					throw new Exception("For node start value must be an integer.");
				}
				
				if(forNode.end instanceof IntegerNode) {
					end = ((IntegerNode) forNode.end).number;
				}
				else {
					throw new Exception("For node end value must be an integer.");
				}
				
				if(start < end) {
					for(int f = start; f < end; f++) {
						variables.replace(forNode.var.name, new IntDataType(f));
						interpretBlock(forNode.statements, variables);
					}
				}
				else {
					for(int f = start; f < end; f++) {
						variables.replace(forNode.var.name, new IntDataType(f));
						interpretBlock(forNode.statements, variables);
					}
				}				
			}
			
			else {
				continue;
			}
		}
		
	}
	
	/**
	 * Returns boolean representing evaluation of a boolean expression.
	 * @param expression Boolean expression.
	 * @param variables List of variables.
	 * @return True or false.
	 */
	public static boolean evaluateBooleanExpression(BooleanExpressionNode expression, HashMap<String, InterpreterDataType> variables) {
		
		if(expression.condition == null && expression.right == null) {
//			System.out.println(resolveBoolean(expression.left, variables));
//			System.out.println(expression);
			return resolveBoolean(expression.left, variables);
		}
		
		
		//FloatNode, IntegerNode, MathOpNode, VariableReferenceNode
		float left = 0;
		
		if(expression.left instanceof IntegerNode) {
			left = (int) resolve(expression.left, variables);
		}
		else if(expression.left instanceof FloatNode || expression.left instanceof MathOpNode) {
			left = (float) resolve(expression.left, variables);
		}
		else if(expression.left instanceof VariableReferenceNode) {
			if(variables.get(((VariableReferenceNode) expression.left).name) instanceof FloatDataType) {
				left = (float) resolve(expression.left, variables);
			}
			else if(variables.get(((VariableReferenceNode) expression.left).name) instanceof IntDataType) {
				left = (int) resolve(expression.left, variables);
			}
			
		}
		
		float right = 0;
		
		if(expression.right instanceof IntegerNode) {
			right = (int) resolve(expression.right, variables);
		}
		else if(expression.right instanceof FloatNode || expression.right instanceof MathOpNode) {
			right = (float) resolve(expression.right, variables);
		}
		else if(expression.right instanceof VariableReferenceNode) {
			if(variables.get(((VariableReferenceNode) expression.right).name) instanceof FloatDataType) {
				right = (float) resolve(expression.right, variables);
			}
			else if(variables.get(((VariableReferenceNode) expression.right).name) instanceof IntDataType) {
				right = (int) resolve(expression.right, variables);
			}
		}
		
//		System.out.println(left + ", " + expression.condition + ", " + right);
				
		switch(expression.condition) {
		case GREATER_THAN:
			return (left > right);
		case GREATER_EQUAL:
			return (left >= right);
		case LESS_THAN:
			return (left < right);
		case LESS_EQUAL:
			return (left <= right);
		case NOT_EQUALS:
			return (left != right);
		case EQUALS:
			return (left == right);
		default:
			return false;
			
		}		
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
