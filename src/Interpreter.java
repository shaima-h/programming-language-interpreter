
/**
 * Interpreter.
 * @author shaimahussaini
 */
public class Interpreter {
	
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
	    		return (float)(((IntegerNode) root).getNum());
	    	else
	    		return (float)(((FloatNode) root).getNum());
	    	
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

}
