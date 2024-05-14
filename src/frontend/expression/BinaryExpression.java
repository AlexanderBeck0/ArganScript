package frontend.expression;

import frontend.Expression;
import frontend.NodeType;

import java.util.Objects;

public final class BinaryExpression extends Expression {
	private static final NodeType kind = NodeType.BinaryExpression;
	private final Expression left;
	private final Expression right;
	private final String operator;

	public BinaryExpression(Expression left, Expression right, String operator) {
		super(kind);
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	public Expression left() {
		return left;
	}

	public Expression right() {
		return right;
	}

	public String operator() {
		return operator;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (BinaryExpression) obj;
		return Objects.equals(this.left, that.left) &&
				Objects.equals(this.right, that.right) &&
				Objects.equals(this.operator, that.operator);
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, right, operator);
	}

	@Override
	public String toString() {
		return "BinaryExpression[" +
				"left=" + left + ", " +
				"right=" + right + ", " +
				"operator=" + operator + ']';
	}


}
