package edu.bsu.cs222.wikipagerevisions;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Controller {
    @FXML
    private TextField searchField;
    private String revisions[][] = new String[4][3]; //hold revision authors [][username, timestamp, comment], is there 4 everytime

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
            NodeList revisionsList = doc.getElementsByTagName("rev");
            for (int i = 0; i < revisionsList.getLength(); i++) { //this actually gets the correct length
                Element tempElement = (Element) revisionsList.item(i);
                revisions[i][0] = tempElement.getAttribute("user");
                revisions[i][1] = tempElement.getAttribute("timestamp");
                revisions[i][2] = tempElement.getAttribute("comment");
            }
//            for (int i = 0; i < 4; i++)
//            {
//                for (int j = 0; j < 3; j++)
//                {
//                    System.out.print(revisions[i][j]);
//                }
//                System.out.println("");
//            }
    }
        catch(Exception e) { //Change to actually handle correct Exceptions. Perhaps with a popup window for 'server down'?
            throw new RuntimeException(e);
        }
    }
}
