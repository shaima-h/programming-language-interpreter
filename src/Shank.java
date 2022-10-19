import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads lines of a single file and converts to tokens.
 * @author shaimahussaini
 */
public class Shank {

	/**
	 * Reads lines of a single file and converts to and prints tokens.
	 * @param args The filename.
	 * @throws Exception if an exception of some sort has occurred.
	 */
	public static void main(String[] args) throws Exception {
	
		//TEST CODE
//		String test = "(* comment ";
//		System.out.println(test);
		
		
		
//		BufferedReader br = new BufferedReader(new FileReader("src/test.txt"));
//		String line = br.readLine();
//		
//		Lexer lexer = new Lexer();
//		List<Token> tokens = new ArrayList<>();
//		
//	    while (line != null) {
//	    	tokens.addAll(tokens.size(), lexer.lex(line));
//	    	line = br.readLine();
//	    }
//	    br.close();
//		
//		System.out.println(tokens);
//		
//		Parser parser = new Parser(tokens);
//		        
//        FunctionNode function = parser.functionDefinition();
//        
//        Interpreter.functions.put("read", new Read("read", null, true));
//        Interpreter.functions.put("write", new Write("write", null, true));
//        Interpreter.functions.put("squareRoot", new SquareRoot("squareRoot", false));
//        Interpreter.functions.put("getRandom", new GetRandom("getRandom", false));
//        Interpreter.functions.put("integerToReal", new IntegerToReal("integerToReal", false));
//        Interpreter.functions.put("realToInteger", new RealToInteger("realToInteger", false));
//        
//        while(function != null) {
//        	System.out.println(function.toString());
//        	
//            Interpreter.functions.put(function.name, function);
//        	function = parser.functionDefinition();
//        }
//        
//        List<InterpreterDataType> startParams = new ArrayList<>();
//        
//        if(Interpreter.functions.get("start") == null) {
//        	throw new Exception("Start function required.");
//        }
//        
//        
//        Interpreter.interpretFunction((FunctionNode) Interpreter.functions.get("start"), startParams); //no parameters?
				

//		List<Node> nodes = new ArrayList<>();
//		nodes = parser.parse();
//				
//		System.out.println();
//		System.out.println(nodes);
//		
//		System.out.println();
//		
//		Interpreter interpreter = new Interpreter();
//		System.out.println("RESOLVE: ");
//	    for(int i = 0; i < nodes.size(); i ++) { //resolving each node
//	    	System.out.println(interpreter.resolve(nodes.get(i)));
//	    }
		
		
		
		if(args.length > 1) throw new Exception ("Only one argument allowed.");
		
		try {
			Lexer lexer = new Lexer();
			List<String> fileLines = new ArrayList<>();
    		List<Token> tokens = new ArrayList<>();

            fileLines = Files.readAllLines(Paths.get(args[0]));
            
            for(int i = 0; i < fileLines.size(); i++) {
            	tokens.addAll(tokens.size(), lexer.lex(fileLines.get(i)));
            }
            
            System.out.println("TOKENS: \n" + tokens);
            System.out.println();
            
            Parser parser = new Parser(tokens);
                        
            FunctionNode function = parser.functionDefinition();

            Interpreter.functions.put("read", new Read("read", null, true));
            Interpreter.functions.put("write", new Write("write", null, true));
            Interpreter.functions.put("squareRoot", new SquareRoot("squareRoot", false));
            Interpreter.functions.put("getRandom", new GetRandom("getRandom", false));
            Interpreter.functions.put("integerToReal", new IntegerToReal("integerToReal", false));
            Interpreter.functions.put("realToInteger", new RealToInteger("realToInteger", false));
            
            while(function != null) {
            	System.out.println(function.toString());
            	
                Interpreter.functions.put(function.name, function);
            	function = parser.functionDefinition();
            }
            
            List<InterpreterDataType> startParams = new ArrayList<>();
            
            if(Interpreter.functions.get("start") == null) {
            	throw new Exception("Start function required.");
            }         
		
            Interpreter.interpretFunction((FunctionNode) Interpreter.functions.get("start"), startParams);
		
		
//            List<Node> nodes = new ArrayList<>();
//            nodes = parser.parse();
//            
//    		System.out.println("NODES: \n" + nodes);
//    		System.out.println();
//    		                        
//            Interpreter interpreter = new Interpreter();
//            
//            System.out.println("RESOLVE: ");
//            for(int i = 0; i < nodes.size(); i ++) { //resolving each node
//                System.out.println(interpreter.resolve(nodes.get(i)));
//            }
//                        
//            
		} catch (IOException ex) {
            System.out.println("I/O error: " + ex);
        } catch (Exception ex) {
        	System.out.println("Error: " + ex);
        }

	}

}
