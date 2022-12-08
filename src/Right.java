import java.util.ArrayList;
import java.util.List;

/**
 * Gets the last length characters of a string.
 * @author shaimahussaini
 */
public class Right extends BuiltInFunctionNode {
	
	/*
	 * Right someString, length, var resultString
		ResultString = last length characters of someString
		HELLO
		01234
		str.length - length
		5 - 3
		2
	 */
	
	public Right(String name, boolean variadic) {
		super(name, variadic);
		List<VariableNode> parameters = new ArrayList<>();
		parameters.add(new VariableNode(null, VariableNode.Type.STRING, true, new FloatNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.INTEGER, true, new IntegerNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.STRING, false, new IntegerNode(0)));
		this.parameters = parameters;
	}

	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 3 || !(list.get(0) instanceof StringDataType) 
				|| !(list.get(1) instanceof IntDataType) || !(list.get(2) instanceof StringDataType)) {
			throw new Exception("Invalid function call to right.");
		}
		
		int length = ((IntDataType) list.get(1)).value;
		
		((StringDataType) list.get(2)).str
			= ((StringDataType) list.get(0)).str.substring(((StringDataType) list.get(0)).str.length() - length);
		
	}

}
