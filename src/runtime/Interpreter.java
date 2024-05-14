package runtime;

import frontend.Statement;
import frontend.expression.AssignmentExpression;
import frontend.expression.BinaryExpression;
import frontend.literal.Identifier;
import frontend.literal.NumericLiteral;
import frontend.literal.ObjectLiteral;
import frontend.statement.Program;
import frontend.statement.VariableDeclaration;
import runtime.eval.Expressions;
import runtime.eval.Statements;
import runtime.value.NumberValue;
import runtime.value.RuntimeValue;

public class Interpreter {
	private final Statements statements = new Statements(this);
	private final Expressions expressions = new Expressions(this);

	public RuntimeValue evaluate(Statement node, Environment env) {
		switch (node.kind()) {
			case Program -> {
				return statements.evaluateProgram((Program) node, env);
			}
			case NumericLiteral -> {
				return new NumberValue(((NumericLiteral) node).value());
			}
			case VariableDeclaration -> {
				return statements.evaluateVariableDeclaration((VariableDeclaration) node, env);
			}
			case BinaryExpression -> {
				return expressions.evaluateBinaryExpression((BinaryExpression) node, env);
			}
			case AssignmentExpression -> {
				return expressions.evaluateAssignment((AssignmentExpression) node, env);
			}
			case Identifier -> {
				return expressions.evaluateIdentifier((Identifier) node, env);
			}
			case ObjectLiteral -> {
				return expressions.evaluateObjectExpression((ObjectLiteral) node, env);
			}
			default -> {
				System.err.println("This AST value has not been set up in evaluate yet! AST value given: " + node);
				System.exit(1); //  1 has NO meaning in this case. Feel free to change it.
			}
		}
		return null;
	}
}
