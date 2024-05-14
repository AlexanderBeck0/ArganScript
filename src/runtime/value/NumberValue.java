package runtime.value;

import runtime.ValueType;

import java.util.Objects;

public class NumberValue extends RuntimeValue {
	private final Double value;
	private final static ValueType type = ValueType.Number;

	public NumberValue(Double value) {
		super(type);
		this.value = value;
	}

	public Double value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (NumberValue) obj;
		return Objects.equals(this.value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return "NumberValue[" +
				"value=" + value + ']';
	}

}
