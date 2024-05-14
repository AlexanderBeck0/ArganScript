package runtime.value;

import runtime.ValueType;

import java.util.Objects;

public class NullValue extends RuntimeValue {
	private final String value;
	private final static ValueType type = ValueType.Null;

	public NullValue() {
		super(type);
		this.value = null;
	}

	public String value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (NullValue) obj;
		return Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "NullValue[" +
				"value=" + value + ']';
	}

}
