import java.net.HttpRetryException;
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
		List<Character> buffer = new ArrayList<Character>();
		List<Token> result = new ArrayList<Token>();

		for (int i = 0; i < input.length(); i++) {

			char currentLetter = input.charAt(i);

			// In case of bad state throw exception immediately
			switch (currentState) {

				case FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter))) {
						currentState = State.NON_FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
						buffer.add(currentLetter);
					}
					else if (currentLetter == ' ') {}
					else if (currentLetter == '0') {
						currentState = State.CURRENT_NUMBER_IS_ZERO;
						buffer.add(currentLetter);
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						throw new ExpressionException();
					}
					else if (currentLetter == '.') {
						throw new NumberException();
					}
					else {
						throw new ExpressionException();
					}
					break;

				case NON_FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {
						buffer.add(currentLetter);
					}
					else if (currentLetter == ' ') {
						result.add(new Token(convertToDouble(buffer)));
						buffer = new ArrayList<Character>();
						currentState = State.EXPECT_OPERATION;
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						result.add(new Token(convertToDouble(buffer)));
						buffer = new ArrayList<Character>();
						result.add(new Token(Token.typeOf(currentLetter)));
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == '.') {
						throw new NumberException();
					}
					else {
						throw new NumberException();
					}
					break;

				case FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {
						currentState = State.NON_FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER;
						buffer.add(currentLetter);
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter)) || currentLetter == ' ' || currentLetter == '.') {
						throw new NumberException();
					}
					else {
						throw new NumberException();
					}
					break;

				case NON_FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER:
					if (POS_INTS.contains(String.valueOf(currentLetter)) || currentLetter == '0') {
						buffer.add(currentLetter);
					}
					else if (OPERATIONS.contains(String.valueOf(currentLetter))) {
						currentState = State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER;
					}
					else if (currentLetter == ' ') {
						currentState = State.EXPECT_OPERATION;
						result.add(new Token(convertToDouble(buffer)));
						buffer = new ArrayList<Character>();
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
						currentState = State.FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER;
						buffer.add(currentLetter);
					}
					else if (currentLetter == ' ') {
						currentState = State.EXPECT_OPERATION;
						result.add(new Token(convertToDouble(buffer)));
						buffer = new ArrayList<Character>();
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
						result.add(new Token(Token.typeOf(currentLetter)));
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

		if (currentState == State.FIRST_CHAR_OF_INTEGER_PART_OF_NUMBER) {
			throw new ExpressionException();
		}
		else if (currentState == State.FIRST_CHAR_OF_DECIMAL_PART_OF_NUMBER) {
			throw new NumberException();
		}

		result.add(new Token(convertToDouble(buffer)));

		return result;
	}

	public static double convertToDouble(List<Character> charList) {
        StringBuilder sb = new StringBuilder();
        for (char c : charList) {
            sb.append(c);
        }

        return Double.parseDouble(sb.toString());
    }
}
