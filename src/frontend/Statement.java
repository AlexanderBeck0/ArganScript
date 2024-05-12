package frontend;

import java.util.Objects;

public class Statement {
	private final NodeType kind;

	public Statement(NodeType kind) {
		this.kind = kind;
	}

	public NodeType kind() {
		return kind;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Statement) obj;
		return Objects.equals(this.kind, that.kind);
	}

	@Override
	public int hashCode() {
		return Objects.hash(kind);
	}

	@Override
	public String toString() {
		return "Statement[" +
				"kind=" + kind + ']';
	}

}
