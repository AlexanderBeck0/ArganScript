import frontend.Parser;
import runtime.Environment;
import runtime.Interpreter;
import runtime.value.NumberValue;


public class main {
	public static void main(String[] args) {
		// Entry point of application
		Parser parser = new Parser();
		Interpreter interpreter = new Interpreter();
		Environment root = Environment.createGlobalEnvironment();
		root.declareVariable("x", new NumberValue(20.0), false);
		System.out.println(interpreter.evaluate(parser.produceAST("let bar = 12; const biz = 65; const b = {foo: bar, biz};"), root));
	}
}