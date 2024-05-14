package frontend;

import java.util.ArrayList;

/**
 * Example usage: <pre>{@code parser.produceAST("(4 + 5) * 3")}</pre>
 */
public class Parser {
	private static ArrayList<Token> tokens;

	/**
	 * @implNote Advances {@link #tokens} by using {@code tokens.removeFirst()}
	 * @param tokenType    The token type to expect
	 * @param errorMessage The error message to show if there is no next token type or if the type is not the provdided type
	 * @return The expected token of the given type, or the system exists.
	 */
	private Token expectType(TokenType tokenType, String errorMessage) {
		final Token previous = tokens.removeFirst();
		if (previous == null || previous.type() != tokenType) {
			System.err.println("Parser error. " + errorMessage + ". Expected " + tokenType);
			System.exit(1); // 1 has NO meaning in this case. Feel free to change it.
		}
		return previous;
	}

	/**
	 * Orders of Precedence:
	 * <ol>
	 *     <li>Assignment Expression</li>
	 *     <li>Member Expression</li>
	 *     <li>Function Call</li>
	 *     <li>Logical Expression</li>
	 *     <li>Comparison Expression</li>
	 *     <li>Additive Expression</li>
	 *     <li>Multiplicative Expression</li>
	 *     <li>Unary Expression</li>
	 *     <li>Primary Expression</li>
	 * </ol>
	 *
	 * @param srcCode The source code to create an Abstract Syntax Tree from
	 * @return A {@link Program} that's body is the tokenized values from the inputted parameter.
	 */
	public Program produceAST(String srcCode) {
		tokens = Lexer.tokenize(srcCode);
		Program program = new Program();

		while (notEOF()) {
			program.body().add(parseStatement());
		}
		return program;
	}

	private Statement parseStatement() {
		switch (tokens.getFirst().type()) {
			case Let, Const -> {
				return parseVariableDeclaration();
			}
			default -> {
				return parseExpression();
			}
		}
	}

	private Expression parseExpression() {
		return parseAdditiveExpression();
	}

	private Statement parseVariableDeclaration() {
		// Gets the type of variable declaration
		// Note: This will NOT handle any data type declarations, only let and const
		// Will need to be reimplemented to allow these
		boolean isConstant = tokens.removeFirst().type() == TokenType.Const;
		String identifier = expectType(TokenType.Identifier, "Expected a valid identifier, instead got " + tokens.getFirst()).value();

		if (tokens.getFirst().type() == TokenType.Semicolon) {
			tokens.removeFirst();
			// Let x;
			if (isConstant) {
				// A constant value which is not initialized
				System.err.println("Constant" + identifier + " must be contain a value!");
				System.exit(1);
			}
			// Initialize value to null
			return new VariableDeclaration(identifier);
		}

		// Get equals sign
		expectType(TokenType.Equals, "Expected equals sign (=), instead found " + tokens.getFirst());

		VariableDeclaration declaration = new VariableDeclaration(identifier, parseAdditiveExpression(), isConstant);
		expectType(TokenType.Semicolon, "Expected semi-colon after variable declaration, received " + tokens.getFirst() + " instead.");
		return declaration;
	}

	private Expression parseAdditiveExpression() {
		Expression left = parseMultiplicativeExpression();
		while (tokens.getFirst().value().equals("+") || tokens.getFirst().value().equals("-")) {
			final String operator = tokens.removeFirst().value();
			final Expression right = parseMultiplicativeExpression();
			left = new BinaryExpression(left, right, operator);
		}
		return left;
	}

	private Expression parseMultiplicativeExpression() {
		Expression left = parsePrimaryExpression();
		while (tokens.getFirst().value().equals("*") || tokens.getFirst().value().equals("/") || tokens.getFirst().value().equals("%")) {
			final String operator = tokens.removeFirst().value();
			final Expression right = parsePrimaryExpression();
			left = new BinaryExpression(left, right, operator);
		}
		return left;
	}

	private Expression parsePrimaryExpression() {
		final TokenType token = tokens.getFirst().type();
		switch (token) {
			case Number -> {
				return new NumericLiteral(Double.parseDouble(tokens.removeFirst().value()));
			}
			case Identifier -> {
				return new Identifier(tokens.removeFirst().value());
			}
			case OpenParen -> {
				tokens.removeFirst();
				final Expression value = parseExpression();
				expectType(TokenType.CloseParen, "Unexpected token found inside expression. Expected close parenthesis.");
				return value;
			}
			default -> {
				System.err.println("Unexpected Token found while parsing. Token found: " + token);
				System.exit(1); //  1 has NO meaning in this case. Feel free to change it.
				return null; // It won't reach but this makes the compiler happy
			}
		}
	}

	/**
	 * @return True if the first element of tokens is not at the end of file
	 */
	private boolean notEOF() {
		return tokens.getFirst().type() != TokenType.EOF;
	}
}
