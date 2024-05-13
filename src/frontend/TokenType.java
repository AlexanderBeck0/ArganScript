package frontend;

public enum TokenType {
	// End of File
	EOF,

	String,
	Number,
	Identifier,
	Equals,
	Semicolon,

	OpenParen,
	CloseParen,

	BinaryOperator,
	UnaryOperator,

	// Keywords
	Let,
	Const
}
