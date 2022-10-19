import java.util.ArrayList;
import java.util.List;

/**
 * Represents a built-in function.
 * @author shaimahussaini
 */
public abstract class BuiltInFunctionNode extends CallableNode {
	
	/**
	 * Indicates if function takes in multiple parameters or not.
	 */
	boolean variadic;
	
	public BuiltInFunctionNode(String name, List<VariableNode> parameters, boolean variadic) {
		super(name, parameters);
		this.variadic = variadic;
	}
	
	public BuiltInFunctionNode(String name, boolean variadic) {
		this.name = name;
		this.variadic = variadic;
	}
	
	/**
	 * Executes function.
	 * @param interpreterData_list Collection of InterpreterDataType objects.
	 * @throws Exception
	 */
	public abstract void execute(List<InterpreterDataType> interpreterData_list) throws Exception;
	
}