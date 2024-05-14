import frontend.Parser;
import runtime.*;

public static void main(String[] args) {
	// Entry point of application
	Parser parser = new Parser();
	Interpreter interpreter = new Interpreter();
	Environment root = new Environment();
	root.declareVariable("x", new NumberValue(20.0), false);
	root.declareVariable("null", new NullValue(), true);
	root.declareVariable("true", new BooleanValue(true), true);
	root.declareVariable("false", new BooleanValue(false), true);
	System.out.println(interpreter.evaluate(parser.produceAST("let y; y"), root));
}
