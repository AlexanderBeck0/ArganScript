package frontend;

public enum TokenType {
	// End of File
	EOF,

	String,
	Number,
	Identifier,

	Equals,
	Semicolon,
	Comma,
	Colon,
	OpenParen,
	CloseParen,
	OpenBrace, // {
	CloseBrace, // }
	OpenBracket, // [
	CloseBracket, // ]

	BinaryOperator,
	UnaryOperator,

	// Keywords
	Let,
	Const
}
