package frontend;

public enum TokenType {
	// End of File
	EOF,

	Null,
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
