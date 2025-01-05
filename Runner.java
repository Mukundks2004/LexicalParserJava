public class Runner {
    public static void main(String[] args) {
        try {
            // System.out.println(LexicalAnalyser.analyse("2 + 6 * 6.690"));
            System.out.println(LexicalAnalyser.analyse("25 + 68 * 6.690 -"));
            // System.out.println(LexicalAnalyser.analyse("0 + 0"));
        }
        catch (NumberException e) {
            System.out.println("Number Exception");
        }
        catch (ExpressionException e) {
            System.out.println("Expression Exception");
        }
    }
}
