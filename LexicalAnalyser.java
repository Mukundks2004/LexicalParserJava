import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyser {

	final static String OPERATIONS = "+-*/";
	final static String POS_INTS = "123456789";
	final static String ALLOWABLE_CHARS = "1234567890+-*/. ";

	private enum State {
		FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER,
		NON_FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER,
		FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER,
		NON_FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER,
		CURRENT_NUMBER_IS_ZERO,
		EXPECT_OPERATION,
	}

	// Note: replace the transition part with an actual transition function that takes two arguments to better emphasize the fsa part of this code
	public static List<Token> analyse(String input) throws NumberException, ExpressionException {
		State currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;

		for (int i = 0; i < input.length(); i++){

			char currentLetter = input.charAt(i);

			// In case of bad state throw exception immediately
			switch (currentState) {

				case FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter))) {
						currentState = State.NON_FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == ' ') {}
					else if (currentLetter == '0') {
						currentState = State.CURRENT_NUMBER_IS_ZERO;
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter)) || currentLetter == '.') {
						throw new ExpressionException();
					}
					else {
						throw new ExpressionException();
					}
					break;

				case NON_FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {}
					else if (currentLetter == ' ') {
						currentState = State.EXPECT_OPERATION;
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == '.') {
						currentState = State.FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER;
					}
					else {
						throw new NumberException();
					}
					break;

				case FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {
						currentState = State.NON_FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER;
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter)) || currentLetter == ' ' || currentLetter == '.') {
						throw new NumberException();
					}
					else {
						throw new NumberException();
					}
					break;

				case NON_FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {}
					else if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == ' ') {
						currentState = State.EXPECT_OPERATION;
					}
					else if (currentLetter == '.') {
						throw new NumberException();
					}
					else {
						throw new NumberException();
					}
					break;

				case CURRENT_NUMBER_IS_ZERO:
					if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == '.') {
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == ' ') {
						currentState = State.EXPECT_OPERATION;
					}
					else if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {
						throw new NumberException();
					}
					else {
						throw new NumberException();
					}
					break;

				case EXPECT_OPERATION:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '.') {
						throw new ExpressionException();
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == ' ') {}
					else {
						throw new ExpressionException();
					}
					break;

				default:
					break;
			}

		}

		List<Token> myList = new ArrayList<Token>();

		Token t1 = new Token(2);
		Token t2 = new Token(3.14);
		Token t3 = new Token(Token.TokenType.DIVIDE);

		myList.add(t1);
		myList.add(t2);
		myList.add(t3);

		return myList;
	}

	public static boolean isValidString(String input, String allowableChars) {
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (allowableChars.indexOf(ch) == -1) {
                return false;
            }
        }
        return true;
    }
}
