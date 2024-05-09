import frontend.Lexer;

public class main {
	public static void main(String[] args) {
		// Entry point of application
		System.out.println(Lexer.tokenize("let x = 45"));
	}
}
