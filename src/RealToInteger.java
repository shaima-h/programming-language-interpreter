import java.util.List;

/**
 * Converts a real number into an integer.
 */
public class RealToInteger extends BuiltInFunctionNode {

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
