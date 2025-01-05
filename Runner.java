import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("2 + 6 * 10.690");
        strings.add("2 + 6 + 3");
        strings.add("0 + 0 + 0");
        strings.add("29 * 54");
        strings.add("3 // 3");
        strings.add("3 / 3");
        strings.add("3 /4");
        strings.add("3/ 3");
        strings.add("666*0.2      -77");
        strings.add("666*0.2");
        strings.add("666*2      -77");
        strings.add("3222+ 333 +444 + 555-666*0.2      -77");

        for (String item : strings) {
            try {
                System.out.println(LexicalAnalyser.analyse(item));
            }
            catch (NumberException e) {
                System.out.println("Number Exception");
            }
            catch (ExpressionException e) {
                System.out.println("Expression Exception");
            }
        }
    }
}
