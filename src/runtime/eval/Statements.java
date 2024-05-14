package runtime.eval;

import frontend.Program;
import frontend.Statement;
import frontend.VariableDeclaration;
import org.jetbrains.annotations.NotNull;
import runtime.Environment;
import runtime.Interpreter;
import runtime.NullValue;
import runtime.RuntimeValue;

public class Statements {
	private final Interpreter interpreter;

	public Statements(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * @param program The {@linkplain Program} to evaluate
	 * @return The evaluated program
	 */
	public RuntimeValue evaluateProgram(@NotNull Program program, Environment env) {
		RuntimeValue lastEvaluated = new NullValue();
		for (Statement statement : program.body()) {
			lastEvaluated = interpreter.evaluate(statement, env);
		}

		return lastEvaluated;
	}

	public RuntimeValue evaluateVariableDeclaration(VariableDeclaration node, Environment env) {
		RuntimeValue value = node.getValue() != null ? interpreter.evaluate(node.getValue(), env) : new NullValue();
		return env.declareVariable(node.getIdentifier(), value, node.isConstant());
	}
}