package frontend;

import frontend.expression.AssignmentExpression;
import frontend.expression.BinaryExpression;
import frontend.literal.Identifier;
import frontend.literal.NumericLiteral;
import frontend.literal.ObjectLiteral;
import frontend.literal.Property;
import frontend.statement.Program;
import frontend.statement.VariableDeclaration;

import java.util.ArrayList;

/**
 * Example usage: <pre>{@code parser.produceAST("(4 + 5) * 3")}</pre>
 */
public class Parser {
	private static ArrayList<Token> tokens;

	/**
	 * @param tokenType    The token type to expect
	 * @param errorMessage The error message to show if there is no next token type or if the type is not the provdided type
	 * @return The expected token of the given type, or the system exists.
	 * @implNote Advances {@link #tokens} by using {@code tokens.removeFirst()}
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
		return parseAssignmentExpression();
	}

	private Expression parseAssignmentExpression() {
		Expression left = parseObjectExpression();

		if (tokens.getFirst().type() == TokenType.Equals) {
			tokens.removeFirst();
			Expression value = parseAssignmentExpression(); // Allows chaining like let x = y = 10;
			return new AssignmentExpression(left, value);
		}
		return left;
	}

	private Expression parseObjectExpression() {
		if (tokens.getFirst().type() != TokenType.OpenBrace) {
			return parseAdditiveExpression();
		}

		tokens.removeFirst(); // Removes the OpenBrace
		ArrayList<Property> properties = new ArrayList<>();

		while (notEOF() && tokens.getFirst().type() != TokenType.CloseBrace) {
			String key = expectType(TokenType.Identifier, "Object key expected, instead received " + tokens.getFirst()).value();
			// { key1, key2} instead of {key1: val1}
			if (tokens.getFirst().type() == TokenType.Comma) {
				tokens.removeFirst();
				properties.add(new Property(key));
				continue;
			} else if (tokens.getFirst().type() == TokenType.CloseBrace) {
				properties.add(new Property(key));
				continue;
			}

			expectType(TokenType.Colon, "Missing colon following key. Received " + tokens.getFirst());
			Expression value = parseExpression();
			properties.add(new Property(key, value));

			if (tokens.getFirst().type() != TokenType.CloseBrace) {
				expectType(TokenType.Comma, "Expected comma or closed brace following property. Received " + tokens.getFirst());
			}
		}
		expectType(TokenType.CloseBrace, "Expected a close brace. Instead received " + tokens.getFirst());
		return new ObjectLiteral(properties);
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
				System.err.println("Constant '" + identifier + "' must be contain a value!");
				System.exit(1);
			}
			// Initialize value to null
			return new VariableDeclaration(identifier);
		}

		// Get equals sign
		expectType(TokenType.Equals, "Expected equals sign (=), instead found " + tokens.getFirst());

		VariableDeclaration declaration = new VariableDeclaration(identifier, parseObjectExpression(), isConstant);
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
				System.err.println("Unexpected Token found while parsing. Token found: " + tokens.getFirst());
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
