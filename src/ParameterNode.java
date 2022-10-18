
public class ParameterNode extends Node {

	Node parameter;
	boolean var;
	
	public ParameterNode(Node parameter, boolean var) {
		this.parameter = parameter;
		this.var = var;
	}
	
	@Override
	public String toString() {
		return "ParameterNode(" + parameter + ", var: " + var + ")";
	}
	
	

}
