package frontend.literal;

import frontend.Expression;
import frontend.NodeType;

import java.util.Objects;

public final class Property extends Expression {
	private final static NodeType kind = NodeType.Property;
	private final String key;
	private final Expression value;

	/**
	 * Defaults {@code value} to {@code null}
	 *
	 * @param key They key of the property
	 */
	public Property(String key) {
		this(key, null);
	}

	public Property(String key, Expression value) {
		super(kind);
		this.key = key;
		this.value = value;
	}

	public String key() {
		return key;
	}

	public Expression value() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Property property = (Property) o;
		return key.equals(property.key) && Objects.equals(value, property.value);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + key.hashCode();
		result = 31 * result + Objects.hashCode(value);
		return result;
	}

	@Override
	public String toString() {
		return "Property[" +
				"key='" + key + '\'' +
				", value=" + value +
				']';
	}
}
