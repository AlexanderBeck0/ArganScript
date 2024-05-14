package frontend.statement;

import frontend.Expression;
import frontend.NodeType;

import java.util.Objects;

public final class VariableDeclaration extends Expression {
	private final static NodeType kind = NodeType.VariableDeclaration;
	private final String identifier;
	private final Expression value;
	private final boolean constant;

	/**
	 * Defaults {@code value} to {@code null} and {@code constant} to {@code false}
	 * @param identifier The name of the variable
	 */
	public VariableDeclaration(String identifier) {
		this(identifier, null, false);
	}

	/**
	 * Defaults {@code constant} to {@code false}
	 * @implNote Does not yet support object types (i.e. {@code int x = 6})
	 * @param identifier The name of the variable
	 * @param value The value of the variable
	 */
	public VariableDeclaration(String identifier, Expression value) {
		this(identifier, value, false);
	}

	/**
	 * @implNote Does not yet support object types (i.e. {@code int x = 6})
	 * @param identifier {@inheritDoc}
	 * @param value The value of the variable
	 * @param constant A constant representing if the variable is a constant
	 */
	public VariableDeclaration(String identifier, Expression value, boolean constant) {
		super(kind);
		this.identifier = identifier;
		this.value = value;
		this.constant = constant;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Expression getValue() {
		return value;
	}

	public boolean isConstant() {
		return constant;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		VariableDeclaration that = (VariableDeclaration) o;
		return constant == that.constant && identifier.equals(that.identifier) && Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "VariableDeclaration{" +
				"identifier='" + identifier + '\'' +
				", value=" + value +
				", constant=" + constant +
				'}';
	}
}
