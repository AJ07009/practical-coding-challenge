import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.XMLConstants;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RecipeProcessor {

    public void processRecipe(String inputArgument) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc;

            if (inputArgument.startsWith("http")) {
                URL url = new URL(inputArgument);
                try (InputStream inputStream = url.openStream()) {
                    doc = db.parse(inputStream);
                }
            } else {
                doc = db.parse(new File(inputArgument));
            }

            doc.getDocumentElement().normalize();
            String outputFileName = getOutputFileName(inputArgument);

            RecipeDataExtractor extractor = new RecipeDataExtractor();
            String outputContent = extractor.extractData(doc);

            try {
                writeOutputToFile(outputFileName, outputContent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private String getOutputFileName(String inputArgument) {
        String fileName = new File(inputArgument).getName();
        return fileName.replaceFirst("\\.xml$", "") + ".out";
    }

    private void writeOutputToFile(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }
}
