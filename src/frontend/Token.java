package frontend;

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
