package frontend.literal;

import frontend.Expression;
import frontend.NodeType;

import java.util.ArrayList;
import java.util.Objects;

public final class ObjectLiteral extends Expression {
	private final static NodeType kind = NodeType.ObjectLiteral;
	private final ArrayList<Property> properties;

	/**
	 * Defaults {@code properties} to an empty ArrayList
	 */
	public ObjectLiteral() {
		this(new ArrayList<>());
	}

	public ObjectLiteral(ArrayList<Property> properties) {
		super(kind);
		this.properties = properties;
	}

	public ArrayList<Property> properties() {
		return properties;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		ObjectLiteral that = (ObjectLiteral) o;
		return Objects.equals(properties, that.properties);
	}

	@Override
	public int hashCode() {
		return Objects.hash(properties);
	}

	@Override
	public String toString() {
		return "ObjectLiteral[" +
				"properties=" + properties +
				']';
	}
}
