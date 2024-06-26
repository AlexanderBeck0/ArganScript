package frontend.statement;

import frontend.NodeType;
import frontend.Statement;

import java.util.ArrayList;
import java.util.Objects;

public final class Program extends Statement {
	private static final NodeType kind = NodeType.Program;
	private final ArrayList<Statement> body;

	/**
	 * Default constructor. Initializes {@link #body} to a new ArrayList
	 */
	public Program() {
		super(kind);
		this.body = new ArrayList<>();
	}

	/**
	 * Initializes the program's {@link #body} to the given {@code body} parameter
	 *
	 * @param body The program body
	 */
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
