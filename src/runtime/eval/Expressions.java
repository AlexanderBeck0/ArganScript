package runtime.eval;

import frontend.NodeType;
import frontend.expression.AssignmentExpression;
import frontend.expression.BinaryExpression;
import frontend.literal.Identifier;
import frontend.literal.ObjectLiteral;
import frontend.literal.Property;
import org.jetbrains.annotations.NotNull;
import runtime.Environment;
import runtime.Interpreter;
import runtime.ValueType;
import runtime.value.NullValue;
import runtime.value.NumberValue;
import runtime.value.ObjectValue;
import runtime.value.RuntimeValue;

public class Expressions {
	private final Interpreter interpreter;

	public Expressions(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	public RuntimeValue evaluateBinaryExpression(BinaryExpression binaryExpression, Environment env) {
		RuntimeValue left = interpreter.evaluate(binaryExpression.left(), env);
		RuntimeValue right = interpreter.evaluate(binaryExpression.right(), env);

		if (left.type() == ValueType.Number && right.type() == ValueType.Number) {
			return evaluateNumericBinaryExpression((NumberValue) left, (NumberValue) right, binaryExpression.operator());
		} else {
			// One/Both of the values are not numbers
			// Return null
			return new NullValue();
		}
	}

	public NumberValue evaluateNumericBinaryExpression(NumberValue left, NumberValue right, @NotNull String operator) {
		// I love rewriting calculators!
		double value = 0.0;
		switch (operator) {
			case "+" -> value = left.value() + right.value();
			case "-" -> value = left.value() - right.value();
			case "*" -> value = left.value() * right.value();
			case "/" -> // TODO: Division by 0 error catching
					value = left.value() / right.value();
			case "%" -> value = left.value() % right.value();
			default -> {
				System.err.println("Unsupported operator type. Given " + operator);
				System.exit(1); // 1 has NO meaning in this case. Feel free to change it.
			}
		}
		return new NumberValue(value);
	}

	public RuntimeValue evaluateIdentifier(Identifier identifier, Environment env) {
		return env.lookupVariable(identifier.symbol());
	}

	public RuntimeValue evaluateAssignment(AssignmentExpression node, Environment env) {
		if (node.assignee().kind() != NodeType.Identifier) {
			System.err.println("Cannot assign value to this type. Given " + node.assignee() + " of type " + node.kind());
		}

		String varname = ((Identifier) node.assignee()).symbol();
		return env.assignVariable(varname, interpreter.evaluate(node.value(), env));
	}

	public RuntimeValue evaluateObjectExpression(ObjectLiteral obj, Environment env) {
		ObjectValue object = new ObjectValue();
		for (Property property : obj.properties()) {
			RuntimeValue value = property.value() == null ? env.lookupVariable(property.key()) : interpreter.evaluate(property.value(), env);
			object.properties().put(property.key(), value);
		}

		return object;
	}
}