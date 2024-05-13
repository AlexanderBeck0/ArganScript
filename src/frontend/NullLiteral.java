package frontend;

import java.util.Objects;

public final class NullLiteral extends Expression {
	private final static NodeType kind = NodeType.NullLiteral;
	private final String value;

	public NullLiteral() {
		super(kind);
		this.value = "null";
	}

	public String value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (NullLiteral) obj;
		return Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "NullLiteral[" +
				"value=" + value + ']';
	}


}
