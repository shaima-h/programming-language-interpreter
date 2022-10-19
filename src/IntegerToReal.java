import java.util.ArrayList;
import java.util.List;

/**
 * Converts an integer into a real number.
 * @author shaimahussaini
 */
public class IntegerToReal extends BuiltInFunctionNode {

	public IntegerToReal(String name, boolean variadic) {
		super(name, variadic);
		List<VariableNode> parameters = new ArrayList<>();
		parameters.add(new VariableNode(null, VariableNode.Type.INTEGER, true, new IntegerNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.REAL, false, new FloatNode(0)));
		this.parameters = parameters;
	}

	//integer, var real
	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 2 || !(list.get(0) instanceof IntDataType) || !(list.get(1) instanceof FloatDataType)) {
			throw new Exception("Invalid function call to integerToReal.");
		}
		
		((FloatDataType) list.get(1)).value = (float) ((IntDataType) list.get(0)).value;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
