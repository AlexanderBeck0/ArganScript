package runtime.value;

import runtime.ValueType;

import java.util.Objects;

public class BooleanValue extends RuntimeValue {
	private final boolean value;
	private final static ValueType type = ValueType.Boolean;

	public BooleanValue(boolean value) {
		super(type);
		this.value = value;
	}

	public boolean value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (BooleanValue) obj;
		return Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "BooleanValue[" +
				"value=" + value + ']';
	}

}
