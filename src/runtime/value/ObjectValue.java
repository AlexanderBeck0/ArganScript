package runtime.value;

import runtime.ValueType;

import java.util.HashMap;

public class ObjectValue extends RuntimeValue {
	private final static ValueType type = ValueType.Object;
	private final HashMap<String, RuntimeValue> properties;

	/**
	 * Defaults {@code properties} to an empty HashMap
	 */
	public ObjectValue() {
		this(new HashMap<>());
	}

	public ObjectValue(HashMap<String, RuntimeValue> properties) {
		super(type);
		this.properties = properties;
	}

	public HashMap<String, RuntimeValue> properties() {
		return properties;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		ObjectValue that = (ObjectValue) o;
		return properties.equals(that.properties);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + properties.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "ObjectValue[" +
				"properties=" + properties +
				']';
	}
}
