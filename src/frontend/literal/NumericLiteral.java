package frontend.literal;

import frontend.Expression;
import frontend.NodeType;

import java.util.Objects;

public final class NumericLiteral extends Expression {
	private final static NodeType kind = NodeType.NumericLiteral;
	private final Double value;

	public NumericLiteral(Double value) {
		super(kind);
		this.value = value;
	}

	public Double value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (NumericLiteral) obj;
		return Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "NumericLiteral[" +
				"value=" + value + ']';
	}


}
