import java.util.List;

/**
 * Prints the inputs separated by spaces.
 * @author shaimahussaini
 *
 */
public class Write extends BuiltInFunctionNode {

	public Write(String name, List<VariableNode> parameters, boolean variadic) {
		super(name, parameters, variadic);
		// TODO Auto-generated constructor stub
	}

	boolean variadic = true;
	
	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() == 0) {
			throw new Exception("Invalid function call to write.");
		}
		
		InterpreterDataType input;
		
		for(int i = 0; i < list.size(); i++) {
			input = list.get(i);
			
			if(input instanceof FloatDataType) {
				System.out.print(((FloatDataType) input).toString() + " ");
			}
			else if(input instanceof IntDataType) {
				System.out.print(((IntDataType) input).toString() + " ");
			}
			else if(input instanceof BoolDataType) {
				System.out.print(((BoolDataType) input).toString() + " ");
			}
			else if(input instanceof StringDataType) {
				System.out.print(((StringDataType) input).toString() + " ");
			}
			else if(input instanceof CharDataType) {
				System.out.print(((CharDataType) input).toString() + " ");
			}
			
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
