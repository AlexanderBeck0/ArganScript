package runtime;

import frontend.*;
import org.jetbrains.annotations.NotNull;

public class Interpreter {
	public RuntimeValue evaluate(Statement node, Environment env) {
		switch (node.kind()) {
			case Program -> {
				return evaluateProgram((Program) node, env);
			}
			case NumericLiteral -> {
				return new NumberValue(((NumericLiteral) node).value());
			}
			case NullLiteral -> {
				return new NullValue();
			}
			case BinaryExpression -> {
				return evaluateBinaryExpression((BinaryExpression) node, env);
			}
			case Identifier -> {
				return evaluateIdentifier((Identifier) node, env);
			}
			default -> {
				System.err.println("This AST value has not been set up yet! AST value given: " + node.kind());
				System.exit(1); //  1 has NO meaning in this case. Feel free to change it.
			}
		}
		return null;
	}

	/**
	 * @param program The {@linkplain Program} to evaluate
	 * @return The evaluated program
	 */
	private RuntimeValue evaluateProgram(@NotNull Program program, Environment env) {
		RuntimeValue lastEvaluated = new NullValue();
		for (Statement statement : program.body()) {
			lastEvaluated = evaluate(statement, env);
		}

		return lastEvaluated;
	}

	private RuntimeValue evaluateBinaryExpression(BinaryExpression binaryExpression, Environment env) {
		RuntimeValue left = evaluate(binaryExpression.left(), env);
		RuntimeValue right = evaluate(binaryExpression.right(), env);

		if (left.type() == ValueType.Number && right.type() == ValueType.Number) {
			return evaluateNumericBinaryExpression((NumberValue) left, (NumberValue) right, binaryExpression.operator());
		} else {
			// One/Both of the values are not numbers
			// Return null
			return new NullValue();
		}
	}

	private NumberValue evaluateNumericBinaryExpression(NumberValue left, NumberValue right, @NotNull String operator) {
		// I love rewriting calculators!
		double value = 0.0;
		switch (operator) {
			case "+" -> {
				value = left.value() + right.value();
			}
			case "-" -> {
				value = left.value() - right.value();
			}
			case "*" -> {
				value = left.value() * right.value();
			}
			case "/" -> {
				// TODO: Division by 0 error catching
				value = left.value() / right.value();
			}
			case "%" -> {
				value = left.value() % right.value();
			}
			default -> {
				System.err.println("Unsupported operator type. Given " + operator);
				System.exit(1); // 1 has NO meaning in this case. Feel free to change it.
			}
		}
		return new NumberValue(value);
	}

	private RuntimeValue evaluateIdentifier(Identifier identifier, Environment env) {
		return env.lookupVariable(identifier.symbol());
	}
}
