package frontend;

/**
 * A {@linkplain Token} contains a {@code value} and a {@code type}
 * @param value The value of the token
 * @param type The {@linkplain TokenType} of the token
 */
public record Token(String value, TokenType type) {

	@Override
	public String value() {
		return value;
	}

	@Override
	public TokenType type() {
		return type;
	}
}
