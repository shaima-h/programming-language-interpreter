import java.util.List;

/**
 * Converts an integer into a real number.
 * @author shaimahussaini
 */
public class IntegerToReal extends BuiltInFunctionNode {

	//integer, var real
	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 2 || !(list.get(0) instanceof IntDataType) || !(list.get(1) instanceof FloatDataType)) {
			throw new Exception("Invalid function call to integerToReal.");
		}
		
		((FloatDataType) list.get(1)).value = (float) ((IntDataType) list.get(0)).value;
		
	}
	
}
