import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RecipeDataExtractor {

    public String extractData(Document doc) {
        StringBuilder outputContent = new StringBuilder();
        NodeList list = doc.getElementsByTagName("recipe");

        for (int temp = 0; temp < list.getLength(); temp++) {
            Node node = list.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String recipeName = element.getAttribute("name");
                String currency = element.getAttribute("currency");

                outputContent.append("Recipe name: ").append(recipeName).append("\n");
                outputContent.append("Currency name: ").append(currency).append("\n");

                NodeList ingredientList = element.getElementsByTagName("ingredient");

                for (int i = 0; i < ingredientList.getLength(); i++) {
                    Node ingredientNode = ingredientList.item(i);

                    if (ingredientNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element ingredientElement = (Element) ingredientNode;

                        String ingredientName = ingredientElement.getElementsByTagName("name").item(0).getTextContent();
                        String ingredientPrice = ingredientElement.getElementsByTagName("price").item(0).getTextContent().replace(",", "\n");

                        outputContent.append("Ingredient: ").append(ingredientName).append(" - ").append(ingredientPrice).append("\n");
                    }
                }

                // Calculate and append the total ingredient count and cost
                int totalIngredientCount = ingredientList.getLength();
                float totalIngredientCost = 0;

                for (int i = 0; i < ingredientList.getLength(); i++) {
                    Node ingredientNode = ingredientList.item(i);

                    if (ingredientNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element ingredientElement = (Element) ingredientNode;
                        float ingredientPrice = Float.parseFloat(ingredientElement.getElementsByTagName("price").item(0).getTextContent());
                        totalIngredientCost += ingredientPrice;
                    }
                }

                outputContent.append("Total ingredient count: ").append(totalIngredientCount).append("\n");
                outputContent.append("Total ingredient cost: ").append(String.format("%.2f", totalIngredientCost)).append(" (in ").append(currency).append(")\n");

                // Append a separator line
                outputContent.append("\n");
            }
        }

        return outputContent.toString();
    }
}
