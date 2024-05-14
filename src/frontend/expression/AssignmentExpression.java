package frontend.expression;

import frontend.Expression;
import frontend.NodeType;

import java.util.Objects;

public final class AssignmentExpression extends Expression {
	private static final NodeType kind = NodeType.AssignmentExpression;
	private final Expression assigne;
	private final Expression value;

	public AssignmentExpression(Expression assigne, Expression value) {
		super(kind);
		this.assigne = assigne;
		this.value = value;
	}

	public Expression assignee() {
		return assigne;
	}

	public Expression value() {
		return value;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (AssignmentExpression) obj;
		return Objects.equals(this.assigne, that.assigne) &&
				Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assigne, value);
	}

	@Override
	public String toString() {
		return "AssignmentExpression[" +
				"assigne=" + assigne + ", " +
				"value=" + value + ']';
	}


}
