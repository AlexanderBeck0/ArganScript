package frontend;

import java.util.Objects;

public final class Identifier extends Expression {
	private static final NodeType kind = NodeType.Identifier;
	private final String symbol;

	public Identifier(String symbol) {
		super(kind);
		this.symbol = symbol;
	}

	public String symbol() {
		return symbol;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Identifier) obj;
		return Objects.equals(this.symbol, that.symbol);
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public String toString() {
		return "Identifier[" +
				"symbol=" + symbol + ']';
	}

}
