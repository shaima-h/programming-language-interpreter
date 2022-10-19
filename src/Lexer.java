import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Takes an input string and converts to Tokens.
 * @author shaimahussaini
 */
public class Lexer {
	
	/**
	 * Accepts a single string and returns a list of Tokens.
	 * @param str The string to be read.
	 * @return An ArrayList of Tokens.
	 * @throws Exception If there is an invalid input.
	 */
	public List<Token> lex(String str) throws Exception  {
		
		List<Token> tokens = new ArrayList<>();
		
		char chr;			//current char
		String store = "";	//placeholder string that holds values
		char state = '1';		//current state
//		String state_main = "";
//
//		if(str.length() >= 1 && Character.isLetter(str.charAt(0))) {
//			state_main = "word";
//		}
//		else {
//			state_main = "number";
//		}
		
		Stack<Character> stack = new Stack<Character>(); //to check validity of parentheses
		
		HashMap<String, Token.Type> reserved = new HashMap<String, Token.Type>();
		
		//if, then, else, elsif, for, from, to, while, repeat, until, mod
		
		reserved.put("define", Token.Type.DEFINE);
		reserved.put("integer", Token.Type.INTEGER);
		reserved.put("real", Token.Type.REAL);
		reserved.put("begin", Token.Type.BEGIN);
		reserved.put("end", Token.Type.END);
		reserved.put("variables", Token.Type.VARIABLES);
		reserved.put("constants", Token.Type.CONSTANTS);
		reserved.put("if", Token.Type.IF);
		reserved.put("then", Token.Type.THEN);
		reserved.put("else", Token.Type.ELSE);
		reserved.put("elsif", Token.Type.ELSIF);
		reserved.put("for", Token.Type.FOR);
		reserved.put("from", Token.Type.FROM);
		reserved.put("to", Token.Type.TO);
		reserved.put("while", Token.Type.WHILE);
		reserved.put("repeat", Token.Type.REPEAT);
		reserved.put("until", Token.Type.UNTIL);
		reserved.put("mod", Token.Type.MODULO);
		reserved.put("var", Token.Type.VAR);

		for(int i = 0; i < str.length(); i++) {
						
			chr = str.charAt(i);
			
			char next_chr = Character.MIN_VALUE;
			if(i < str.length()-1) {
				next_chr = str.charAt(i+1);
			}
			
			switch(state) {
			case '1':
				if(Character.isDigit(chr)) {
					store += Character.toString(chr);
					state = '3';
				}
				else if(chr == '+' || chr == '-') {
					store += Character.toString(chr);
					state = '2';
				}
				else if(chr == '.') {
					store += Character.toString(chr);
					state = '6';
				}
				else if(Character.isLetter(chr)) {
					store += chr;
					state = 'w';
				}
				else if(chr == '(') {
					if(next_chr == '*') {
						state = 'c';
						break;
					}
					tokens.add(new Token(Token.Type.LPAREN));
					stack.push(')');
					state = '1';
				}
				else if(chr == ')') {
					if (stack.isEmpty() || stack.pop() != chr) {
						throw new Exception("Invalid input parentheses.");
					}
					tokens.add(new Token(Token.Type.RPAREN));
					state = '1';
				}
				else if(chr == '\s' || chr == '\t') {
					state = '1';
				}
				else if(chr == ',' || chr == ':' || chr == '=' || chr == ';') {
					if(reserved.containsKey(store)) {
	                	tokens.add(new Token(reserved.get(store)));
	                	store = "";
	                }
	                else {
	                	if(store != "") {
	                		tokens.add(new Token(Token.Type.IDENTIFIER, store));
	                		store = "";
	                	}
	                }
					
					switch(chr) {
                	case ',':
                		tokens.add(new Token(Token.Type.COMMA));
                		break;
                	case ':':
                		if(next_chr == '=') {
                			tokens.add(new Token(Token.Type.ASSIGNMENT));
                			i++;
                			state = '1';
                			break;
                		}
                		tokens.add(new Token(Token.Type.COLON));
                		break;
                	case ';':
                		tokens.add(new Token(Token.Type.SEMICOLON));
                		break;
                	case '=':
                		tokens.add(new Token(Token.Type.EQUALS));
                		break;
	                }
					
					state = '1';
				}
				else if(chr == '<' || chr == '>') {
					switch(chr) {
					case '>':
						if(next_chr == '=') {
							tokens.add(new Token(Token.Type.GREATER_EQUAL));
							i++;
						}
						else {
							tokens.add(new Token(Token.Type.GREATER_THAN));
							i++;
						}
						break;
					case '<':
						if(next_chr == '=') {
							tokens.add(new Token(Token.Type.LESS_EQUAL));
							i++;
						}
						else if(next_chr == '>') {
							tokens.add(new Token(Token.Type.NOT_EQUALS));
							i++;
						}
						else {
							tokens.add(new Token(Token.Type.LESS_THAN));
						}
						break;
					}
					state = '1';
				}
				else {
	                throw new Exception("Invalid input state 1.");
				}
				break;
			case '2':
				if(Character.isDigit(chr)) {
					store += Character.toString(chr);
					state = '3';
				}
				else if(chr == '.') {
					store += Character.toString(chr);
					state = '6';
				}
				else {
	                throw new Exception("Invalid input state 2.");
				}
				break;
			case '3':
				if(Character.isDigit(chr)) {
					store += Character.toString(chr);
					state = '3';
				}
				else if(chr == '+' || chr == '-' || chr == '*' || chr == '/') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
	                switch(chr) {
	                	case '+':
	                		tokens.add(new Token(Token.Type.PLUS));
	                		break;
	                	case '-':
	                		tokens.add(new Token(Token.Type.MINUS));
	                		break;
	                	case '*':
	                		tokens.add(new Token(Token.Type.TIMES));
	                		break;
	                	case '/':
	                		tokens.add(new Token(Token.Type.DIVIDE));
	                		break;
	                }
	                state = '1';
				}
				else if(chr == '.') {
					store += Character.toString(chr);
					state = '4';
				}
				else if(chr == '\s' || chr == '\t') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					state = '5';
				}
				else if(chr == ')') {
					if (stack.isEmpty() || stack.pop() != chr) {
						throw new Exception("Invalid input parentheses.");
					}
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					tokens.add(new Token(Token.Type.RPAREN));
					state = '3';
				}
				else if(Character.isLetter(chr)) {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					store += chr;
					state = 'w';
				}
				else if(chr == ',') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					tokens.add(new Token(Token.Type.COMMA));
					state = '1';
				}
				else {
	                throw new Exception("Invalid input state 3.");
				}
				break;
			case '4':
				if(Character.isDigit(chr)) {
					store += Character.toString(chr);
					state = '4';
				}
				else if(chr == '\s' || chr == '\t') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					state = '5';
				}
				else if(chr == ',') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					tokens.add(new Token(Token.Type.COMMA));
					state = '1';
				}
				else if(chr == ')') {
					if (stack.isEmpty() || stack.pop() != chr) {
						throw new Exception("Invalid input parentheses.");
					}
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					tokens.add(new Token(Token.Type.RPAREN));
					state = '3';
				}
				else if(chr == '+' || chr == '-' || chr == '*' || chr == '/') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
	                switch(chr) {
                	case '+':
                		tokens.add(new Token(Token.Type.PLUS));
                		break;
                	case '-':
                		tokens.add(new Token(Token.Type.MINUS));
                		break;
                	case '*':
                		tokens.add(new Token(Token.Type.TIMES));
                		break;
                	case '/':
                		tokens.add(new Token(Token.Type.DIVIDE));
                		break;
	                }
	                state = '1';
				}
				else if(Character.isLetter(chr)) {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					store += chr;
					state = 'w';
				}
				else {
	                throw new Exception("Invalid input state 4.");
				}
				break;
			case '5':
				if(chr == '\s' || chr == '\t') {
					state = '5';
				}
				else if(chr == ')') {
					if (stack.isEmpty() || stack.pop() != chr) {
						throw new Exception("Invalid input parentheses.");
					}
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					tokens.add(new Token(Token.Type.RPAREN));
					state = '3';
				}
				else if(chr == '+' || chr == '-' || chr == '*' || chr == '/') {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
	                switch(chr) {
                	case '+':
                		tokens.add(new Token(Token.Type.PLUS));
                		break;
                	case '-':
                		tokens.add(new Token(Token.Type.MINUS));
                		break;
                	case '*':
                		tokens.add(new Token(Token.Type.TIMES));
                		break;
                	case '/':
                		tokens.add(new Token(Token.Type.DIVIDE));
                		break;
	                }
	                state = '1';
				}
				else if(Character.isLetter(chr)) {
					if(store != "") {
						tokens.add(new Token(Token.Type.NUMBER, store));
		                store = "";
					}
					store += chr;
					state = 'w';
				}
				else if(chr == '<' || chr == '>') {
					switch(chr) {
					case '>':
						if(next_chr == '=') {
							tokens.add(new Token(Token.Type.GREATER_EQUAL));
							i++;
						}
						else {
							tokens.add(new Token(Token.Type.GREATER_THAN));
							i++;
						}
						break;
					case '<':
						if(next_chr == '=') {
							tokens.add(new Token(Token.Type.LESS_EQUAL));
							i++;
						}
						else if(next_chr == '>') {
							tokens.add(new Token(Token.Type.NOT_EQUALS));
							i++;
						}
						else {
							tokens.add(new Token(Token.Type.LESS_THAN));
						}
						break;
					}
					state = '1';
				}
				else {
	                throw new Exception("Invalid input state 5.");
				}
				break;
			case '6':
				if(Character.isDigit(chr)) {
					store += Character.toString(chr);
					state = '4';
				}
				else {
	                throw new Exception("Invalid input state 6.");
				}
				break;
			case 'w':
				if(Character.isLetter(chr) || Character.isDigit(chr) || chr == '_') {
					store += Character.toString(chr);
					state = 'w';
				}
				else if(chr == ',' || chr == ':' || chr == '=' || chr == ';' || chr == '\s' || chr == '\t'
						|| chr == '(' || chr == ')') {
	                if(reserved.containsKey(store)) {
	                	tokens.add(new Token(reserved.get(store)));
	                	store = "";
	                }
	                else {
	                	if(store != "") {
	                		tokens.add(new Token(Token.Type.IDENTIFIER, store));
	                		store = "";
	                	}
	                }
	                	                
	                switch(chr) {
                	case ',':
                		tokens.add(new Token(Token.Type.COMMA));
                		state = '1';
                		break;
                	case ':':
                		if(next_chr == '=') {
                			tokens.add(new Token(Token.Type.ASSIGNMENT));
                			i++;
                			state = '1';
                			break;
                		}
                		tokens.add(new Token(Token.Type.COLON));
                		state = '1';
                		break;
                	case ';':
                		tokens.add(new Token(Token.Type.SEMICOLON));
                		state = '1';
                		break;
                	case '=':
                		tokens.add(new Token(Token.Type.EQUALS));
                		state = '1';
                		break;
                	case '(':
                		if(next_chr == '*') {
                			i++;
    						state = 'c';
    						break;
    					}
                		tokens.add(new Token(Token.Type.LPAREN));
    					stack.push(')');
                		state = '1';
                		break;
                	case ')':
                		if (stack.isEmpty() || stack.pop() != chr) {
    						throw new Exception("Invalid input parentheses.");
    					}
    					tokens.add(new Token(Token.Type.RPAREN));
    					state = '1';
                		break;
	                default:
	                	if(Character.isDigit(next_chr)) { //function call, ex: add 2, 3
	                		state = '1';
	                	}
	                }
				}
				else if(chr == '+' || chr == '-' || chr == '*' || chr == '/') {
					if(reserved.containsKey(store)) {
	                	tokens.add(new Token(reserved.get(store)));
	                	store = "";
	                }
	                else {
	                	if(store != "") {
	                		tokens.add(new Token(Token.Type.IDENTIFIER, store));
	                		store = "";
	                	}
	                }
					i--;
					state = '3';
				}
				else if(chr == '<' || chr == '>') {
					switch(chr) {
					case '>':
						if(next_chr == '=') {
							tokens.add(new Token(Token.Type.GREATER_EQUAL));
							i++;
						}
						else {
							tokens.add(new Token(Token.Type.GREATER_THAN));
							i++;
						}
						break;
					case '<':
						if(next_chr == '=') {
							tokens.add(new Token(Token.Type.LESS_EQUAL));
							i++;
						}
						else if(next_chr == '>') {
							tokens.add(new Token(Token.Type.NOT_EQUALS));
							i++;
						}
						else {
							tokens.add(new Token(Token.Type.LESS_THAN));
						}
						break;
					}
					state = '1';
				}
				else {
					throw new Exception("Invalid input state w.");
				}
				break;
			case 'c':
				if(chr == '*' && next_chr == ')') {
					i++;
					state = '1';
				}
				else {
					state = 'c';
				}
				break;
			}
			
//			System.out.println(chr);
//			System.out.println("store: " + store);
//			System.out.println("state: " + state);
			
		}
		
		if(!stack.isEmpty()) {
			throw new Exception("Invalid input parentheses.");
		}
		
		if(store != "") {
			if(state == 'w') {
				if(reserved.containsKey(store)) {
	                tokens.add(new Token(reserved.get(store)));
	            	store = "";
	            }
	            else {
	            		tokens.add(new Token(Token.Type.IDENTIFIER, store));
	            		store = "";
	            }
			}
			else {
				tokens.add(new Token(Token.Type.NUMBER, store));
			}
			
		}
		
		tokens.add(new Token(Token.Type.EndOfLine, "EndOfLine"));
		
		// **** COME BACK TO THIS ****
//		if(state == '1' && tokens.get(0).getType() != Token.Type.EndOfLine
//				|| state == '2' || state == '6') { //ending with operator and is not empty line
//			throw new Exception("Invalid input.");
//		}
				
		return tokens;
	}

}
