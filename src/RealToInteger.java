import java.util.ArrayList;
import java.util.List;

/**
 * Converts a real number into an integer.
 */
public class RealToInteger extends BuiltInFunctionNode {

	public RealToInteger(String name, boolean variadic) {
		super(name, variadic);
		List<VariableNode> parameters = new ArrayList<>();
		parameters.add(new VariableNode(null, VariableNode.Type.REAL, true, new FloatNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.INTEGER, false, new IntegerNode(0)));
		this.parameters = parameters;
	}

	//real, var int
	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 2 || !(list.get(0) instanceof FloatDataType) || !(list.get(1) instanceof IntDataType)) {
			throw new Exception("Invalid function call to realToInteger.");
		}
		
		((IntDataType) list.get(1)).value = (int) ((FloatDataType) list.get(0)).value;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
