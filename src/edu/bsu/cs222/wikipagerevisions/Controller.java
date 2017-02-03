package edu.bsu.cs222.wikipagerevisions;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Controller {
    @FXML
    private TextField searchField;
    private String revisions[][] = new String[10][3]; //hold revision authors [][username, timestamp, comment]

    @FXML
    public void loadURL() {
        String URLStart = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=";
        String URLEnd = "&rvprop=timestamp|comment|user&rvlimit=4";
        String search = searchField.getText();
        search = search.replace(" ", "_");
        search = URLStart + search + URLEnd;
        try {
            URL url = new URL(search);
            System.out.println(url);
            readXMLFile(url);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readXMLFile(URL url) {
        int count = 0;
        try {
            URLConnection connection = url.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url.openStream());
            NodeList revisions = doc.getElementsByTagName("rev");
            for (int i = 0; i < revisions.getLength(); i++) { //this actually gets the correct length
                System.out.println(revisions.item(i));
            }
            TransformerFactory factory1 = TransformerFactory.newInstance();
            Transformer xform = factory1.newTransformer();
            xform.transform(new DOMSource(doc), new StreamResult(System.out));
    }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
