import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Returns a random integer.
 * @author shaimahussaini
 */
public class GetRandom extends BuiltInFunctionNode {

	public GetRandom(String name, boolean variadic) {
		super(name, variadic);
		List<VariableNode> parameters = new ArrayList<>();
		parameters.add(new VariableNode(null, VariableNode.Type.INTEGER, false, new IntegerNode(0)));
		this.parameters = parameters;
	}

	public void execute(List<InterpreterDataType> list) throws Exception {
		
		if(list.size() != 1 || !(list.get(0) instanceof IntDataType)) {
			throw new Exception("Invalid function call to getRandom.");
		}
		
		Random random = new Random();
        int rand = random.nextInt(Integer.MAX_VALUE);
        
        ((IntDataType) list.get(0)).value = rand;
        
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
