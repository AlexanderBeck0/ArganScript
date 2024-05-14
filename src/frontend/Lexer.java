package frontend;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Lexer {
	public static ArrayList<Token> tokenize(String sourceCode) {
		final ArrayList<Token> tokens = new ArrayList<>();
		final List<String> src = new ArrayList<>(Arrays.asList(sourceCode.split("")));

		while (!src.isEmpty()) {
			if (src.getFirst().equals("(")) {
				tokens.add(token(src.removeFirst(), TokenType.OpenParen));
			} else if (src.getFirst().equals(")")) {
				tokens.add(token(src.removeFirst(), TokenType.CloseParen));
			} else if (src.getFirst().equals("{")) {
				tokens.add(token(src.removeFirst(), TokenType.OpenBrace));
			} else if (src.getFirst().equals("}")) {
				tokens.add(token(src.removeFirst(), TokenType.CloseBrace));
			} else if (src.getFirst().equals("[")) {
				tokens.add(token(src.removeFirst(), TokenType.OpenBracket));
			} else if (src.getFirst().equals("]")) {
				tokens.add(token(src.removeFirst(), TokenType.CloseBracket));
			} else if (src.getFirst().equals("+") || src.getFirst().equals("-") || src.getFirst().equals("/") || src.getFirst().equals("*") || src.getFirst().equals("%")) {
				tokens.add(token(src.removeFirst(), TokenType.BinaryOperator));
			} else if (src.getFirst().equals("=")) {
				tokens.add(token(src.removeFirst(), TokenType.Equals));
			} else if (src.getFirst().equals(";")) {
				tokens.add(token(src.removeFirst(), TokenType.Semicolon));
			} else if (src.getFirst().equals(":")) {
				tokens.add(token(src.removeFirst(), TokenType.Colon));
			} else if (src.getFirst().equals(",")) {
				tokens.add(token(src.removeFirst(), TokenType.Comma));
			} else {
				// Multi-character tokens
				if (Lexer.isSkippable(src.getFirst())) {
					// Remove whitespaces
					src.removeFirst();
				} else if (Lexer.isInt(src.getFirst())) {
					// Number token
					StringBuilder num = new StringBuilder();
					while (!src.isEmpty() && Lexer.isInt(src.getFirst())) {
						num.append(src.removeFirst());
					}
					tokens.add(token(num.toString(), TokenType.Number));
				} else if (Lexer.isAlpha(src.getFirst())) {
					// Identifier Token
					StringBuilder identifier = new StringBuilder();
					while (!src.isEmpty() && Lexer.isAlpha(src.getFirst())) {
						identifier.append(src.removeFirst());
					}

					if (Keywords.contains(identifier.toString())) {
						// A reserved token
						TokenType keywordType = Objects.requireNonNull(Keywords.get(identifier.toString()));
						tokens.add(token(identifier.toString(), keywordType));
					} else {
						// A non-reserved token
						tokens.add(token(identifier.toString(), TokenType.Identifier));
					}
				} else {
					System.err.println("Unrecognized character provided. Found: " + src.getFirst());
					System.exit(1); // 1 has NO meaning in this case. Feel free to change it.
				}
			}
		}

		tokens.add(token("End Of File", TokenType.EOF));
		return tokens;
	}

	private static Token token(String value, @NotNull TokenType type) {
		return new Token(value, type);
	}

	/**
	 * Determines if the given string contains alphabetical characters
	 *
	 * @param src The string to test for characters. Only pass single characters (as a String type)
	 * @return A boolean representing if the string has alphabetical characters
	 */
	private static boolean isAlpha(@NotNull String src) {
		return !src.toLowerCase().equals(src.toUpperCase());
	}

	/**
	 * Determines if the given string is an integer
	 *
	 * @param src The string to test for ints
	 * @return A boolean representing if it contains integers
	 * @implNote Not sure if the behavior of accepting "-"'s is expected or not
	 * @deprecated
	 */
	private static boolean _isInt(String src) {
		boolean isInteger = false;
		try {
			Integer.parseInt(src);
			isInteger = true;
		} catch (NumberFormatException ignored) {
		}
		return isInteger;
	}

	/**
	 * Determines if the given string is an integer
	 *
	 * @param src The string to test for ints. Only pass single characters (as a String type)
	 * @return A boolean representing if it contains integers
	 */
	private static boolean isInt(@NotNull String src) {
		// TODO: Really inefficient... it only needs to check one character, the loop is unnecessary
		for (int i = 0; i < src.length(); i++) {
			if (!Character.isDigit(src.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Skippable characters are {@code " "}, {@code \n}, and {@code \t}
	 *
	 * @param src A one character long input
	 * @return True if it is a skippable character, false if it is not. Also returns false if the input it greater than 1 character
	 */
	private static boolean isSkippable(@NotNull String src) {
		if (src.length() > 1) {
			// This should not be happening.
			// Only a single character should be getting passed
			return false;
		}
		return src.equals(" ") || src.equals("\n") || src.equals("\t") || src.equals("\r");
	}
}
