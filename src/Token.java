
/**
 * Represents a token.
 * @author shaimahussaini
 */
public class Token {
	
	/**
	 * Type of token that was processed.
	 */
	public enum Type {
		
		NUMBER, PLUS, MINUS, TIMES, DIVIDE, EndOfLine, 
		LPAREN, RPAREN, IDENTIFIER, DEFINE, INTEGER, 
		REAL, BEGIN, END, SEMICOLON, COLON, EQUALS, COMMA, 
		VARIABLES, CONSTANTS, ASSIGNMENT, IF, THEN, ELSE, 
		ELSIF, FOR, FROM, TO, WHILE, REPEAT, UNTIL, MODULO, 
		GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL, 
		NOT_EQUALS, VAR;
		
	}
	
	/**
	 * Type of token.
	 */
	private Type type;
	
	/**
	 * Value of token.
	 */
	private String value = null;

	/**
	 * Constructor.
	 * @param type Type of token.
	 * @param val Value of token.
	 */
	public Token(Type type, String val) {
		this.type = type;
		this.value = val;
	}
	
	/**
	 * Constructor.
	 * @param type Type of token.
	 */
	public Token(Type type) {
		this.type = type;
	}
	
	/**
	 * Returns type of token.
	 * @return Type of token.
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Returns value of token.
	 * @return Value of token.
	 */
	public String getValueStr() {
		return value;
	}
	
	/**
	 * Returns value of number either as a float or integer.
	 * @return Value of number either as a float or integer
	 */
	public Number getValue() {
		if(value.indexOf('.') != -1) {
			return Float.parseFloat(value);
		}
		else {
			return Integer.parseInt(value);
		}
	}

	/**
	 * Returns char representing number type.
	 * @return 'i' representing integer or 'f' representing float.
	 */
	public char getNumType() {
		if(value.indexOf('.') != -1) {
			return 'f';
		} else {
			return 'i';
		}
	}
	
	/**
	 * Returns token as a string.
	 */
	public String toString() {
		if(type == Type.NUMBER)
			return "NUMBER(" + value + ")";
		else if(type == Type.IDENTIFIER) {
			return "IDENTIFIER(" + value + ")";
		}
		else
			return type.toString();
	}
	
	
	
	

}
