import frontend.Parser;
import runtime.*;

public static void main(String[] args) {
	// Entry point of application
	Parser parser = new Parser();
	Interpreter interpreter = new Interpreter();
	Environment root = new Environment();
	root.declareVariable("x", new NumberValue(20.0));
	root.declareVariable("null", new NullValue());
	root.declareVariable("true", new BooleanValue(true));
	root.declareVariable("false", new BooleanValue(false));
	System.out.println(interpreter.evaluate(parser.produceAST("1 + true"), root));
}
