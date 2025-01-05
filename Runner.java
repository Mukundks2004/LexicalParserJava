public class Runner {
    public static void main(String[] args) {
        try {
            System.out.println(LexicalAnalyser.analyse("3 + 4"));
        }
        catch (NumberException e) {
            System.out.println("Number Exception");
        }
        catch (ExpressionException e) {
            System.out.println("Expression Exception");
            
        }
    }
}
