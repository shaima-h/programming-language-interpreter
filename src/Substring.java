import java.util.ArrayList;
import java.util.List;

/**
 * Gets the length characters of a string starting at an index.
 * @author shaimahussaini
 */
public class Substring extends BuiltInFunctionNode {
	
	/*
	 * Substring someString, index, length, var resultString
		ResultString = length characters from someString, starting at index
	 */
	
	public Substring(String name, boolean variadic) {
		super(name, variadic);
		List<VariableNode> parameters = new ArrayList<>();
		parameters.add(new VariableNode(null, VariableNode.Type.STRING, true, new FloatNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.INTEGER, true, new IntegerNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.INTEGER, true, new IntegerNode(0)));
		parameters.add(new VariableNode(null, VariableNode.Type.STRING, false, new IntegerNode(0)));
		this.parameters = parameters;
	}

	public void execute(List<InterpreterDataType> list) throws Exception {
		
		System.out.println(list.get(0).getClass());
		
		if(list.size() != 4 || !(list.get(0) instanceof StringDataType) || !(list.get(1) instanceof IntDataType) 
				|| !(list.get(2) instanceof IntDataType) || !(list.get(3) instanceof StringDataType)) {
			throw new Exception("Invalid function call to substring.");
		}
		
		int start = ((IntDataType) list.get(1)).value;
		int length = ((IntDataType) list.get(2)).value;
		
		((StringDataType) list.get(3)).str
			= ((StringDataType) list.get(0)).str.substring(start, start + length);
		
	}

}
