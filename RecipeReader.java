public class RecipeReader {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java RecipeReader <xmlFilePathOrURL>");
            return;
        }

        String inputArgument = args[0];
        RecipeProcessor processor = new RecipeProcessor();
        processor.processRecipe(inputArgument);
    }
}
