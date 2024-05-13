package runtime;

import frontend.BinaryExpression;
import frontend.NumericLiteral;
import frontend.Program;
import frontend.Statement;
import org.jetbrains.annotations.NotNull;

public class Interpreter {
	public RuntimeValue evaluate(Statement node) {
		switch (node.kind()) {
			case Program -> {
				return evaluateProgram((Program) node);
			}
			case NumericLiteral -> {
				return new NumberValue(((NumericLiteral) node).value());
			}
			case NullLiteral -> {
				return new NullValue();
			}
			case BinaryExpression -> {
				return evaluateBinaryExpression((BinaryExpression) node);
			}
			default -> {
				System.err.println("This AST value has not been set up yet! AST value given: " + node.kind());
				System.exit(1); //  1 has NO meaning in this case. Feel free to change it.
			}
		}
		return null;
	}

	private RuntimeValue evaluateProgram(Program program) {
		RuntimeValue lastEvaluated = new NullValue();
		for (Statement statement : program.body()) {
			lastEvaluated = evaluate(statement);
		}

		return lastEvaluated;
	}

	private RuntimeValue evaluateBinaryExpression(BinaryExpression binaryExpression) {
		RuntimeValue left = evaluate(binaryExpression.left());
		RuntimeValue right = evaluate(binaryExpression.right());

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

}
