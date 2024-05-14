package runtime.value;

import runtime.ValueType;

import java.util.Objects;

public class RuntimeValue {
	private final ValueType type;

	public RuntimeValue(ValueType type) {
		this.type = type;
	}

	public ValueType type() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (RuntimeValue) obj;
		return Objects.equals(this.type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	@Override
	public String toString() {
		return "RuntimeValue[" +
				"type=" + type + ']';
	}

}
