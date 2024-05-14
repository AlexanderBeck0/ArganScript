package frontend;

public enum NodeType {
	// Statements
	Program,
	VariableDeclaration,
	FunctionDeclaration,

	// Expressions
	AssignmentExpression,
	BinaryExpression,
	CallExpression,
	UnaryExpression,

	// Literals
	NumericLiteral,
	Identifier,
	Property,
	ObjectLiteral

}
