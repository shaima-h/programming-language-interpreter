import java.util.List;

/**
 * Reads inputs.
 * @author shaimahussaini
 */
public class Read extends BuiltInFunctionNode {
	
	boolean variadic = true;
	
	//var a, var b, var c...
	//takes in Strings --> numeric value
	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() == 0) {
			throw new Exception("Invalid function call to read.");
		}
		
		for(int i = 0; i < list.size(); i++) {
			String str = list.get(i).toString();
			InterpreterDataType curr = list.get(i);
			
			if(curr instanceof FloatDataType) {
				((FloatDataType) curr).fromString(str);
			}
			else if(curr instanceof IntDataType) {
				((IntDataType) curr).fromString(str);
			}
						
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
