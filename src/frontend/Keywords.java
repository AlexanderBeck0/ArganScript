package frontend;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Dictionary;
import java.util.Hashtable;

public class Keywords {
	private static final Dictionary<String, TokenType> KEYWORDS = new Hashtable<>();

	static {
		KEYWORDS.put("let", TokenType.Let);
		KEYWORDS.put("const", TokenType.Const);
		KEYWORDS.put("null", TokenType.Null);
	}

	/**
	 * Checks if the provided keyword is defined in the list of keywords
	 *
	 * @param keyword The keyword to check
	 * @return A boolean representing if the keyword is in the list of keywords
	 */
	public static boolean contains(@NotNull String keyword) {
		return Keywords.KEYWORDS.get(keyword) != null;
	}


	/**
	 * Gets the token type from the defined TokenTypes
	 *
	 * @param keyword The keyword to get the {@link TokenType} from
	 * @return The given keyword's {@link TokenType}, or {@code null} if it is not found
	 */
	@Nullable
	public static TokenType get(@NotNull String keyword) {
		return Keywords.KEYWORDS.get(keyword);
	}
}
