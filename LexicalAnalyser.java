import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyser {

	public static List<Token> analyse(String input) throws NumberException, ExpressionException {
		List<Token> myList = new ArrayList<Token>();

		Token t1 = new Token(2);
		Token t2 = new Token(3.14);
		Token t3 = new Token(Token.TokenType.DIVIDE);

		myList.add(t1);
		myList.add(t2);
		myList.add(t3);

		return myList;
	}
}
