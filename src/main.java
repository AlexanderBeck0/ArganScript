import frontend.Lexer;
import frontend.Parser;
import runtime.Interpreter;

public static void main(String[] args) {
	// Entry point of application
	System.out.println(Lexer.tokenize("let x = 45"));
	Parser parser = new Parser();
	Interpreter interpreter = new Interpreter();
	System.out.println(parser.produceAST("(4 + 5) * 3"));
	System.out.println(interpreter.evaluate(parser.produceAST("(4 + 5) * 3")));
}
