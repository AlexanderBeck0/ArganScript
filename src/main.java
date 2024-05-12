import frontend.Lexer;
import frontend.Parser;

public static void main(String[] args) {
	// Entry point of application
	System.out.println(Lexer.tokenize("let x = 45"));
	Parser parser = new Parser();
	System.out.println(parser.produceAST("(4 + 5) * 3"));
}
