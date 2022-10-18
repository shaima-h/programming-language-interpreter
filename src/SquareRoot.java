import java.util.List;

/**
 * Finds square root of a float.
 * @author shaimahussaini
 */
public class SquareRoot extends BuiltInFunctionNode {

	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 2 || !(list.get(0) instanceof FloatDataType)) {
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
