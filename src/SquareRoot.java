import java.util.ArrayList;
import java.util.List;

/**
 * Finds square root of a float.
 * @author shaimahussaini
 */
public class SquareRoot extends BuiltInFunctionNode {

	public SquareRoot(String name, boolean variadic) {
		super(name, variadic);
		List<VariableNode> parameters = new ArrayList<>();
		parameters.add(new VariableNode(null, VariableNode.Type.REAL, true, new FloatNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.REAL, false, new FloatNode(0)));
		this.parameters = parameters;
	}

	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 2 || !(list.get(0) instanceof FloatDataType)) {
			System.out.println(list.size());
			throw new Exception("Invalid function call to squareRoot.");
		}
		
		float num = ((FloatDataType) list.get(0)).value;
		num = (float) Math.sqrt(num);
		((FloatDataType) list.get(1)).value = num;
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
