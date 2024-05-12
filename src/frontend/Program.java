package frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class Program extends Statement {
	private static final NodeType kind = NodeType.Program;
	private final ArrayList<Statement> body;

	public Program(ArrayList<Statement> body) {
		super(kind);
		this.body = body;
	}

	public ArrayList<Statement> body() {
		return body;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Program) obj;
		return Objects.equals(this.body, that.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(body);
	}

	@Override
	public String toString() {
		return "Program[" +
				"body=" + body.toString() + ']';
	}


}
