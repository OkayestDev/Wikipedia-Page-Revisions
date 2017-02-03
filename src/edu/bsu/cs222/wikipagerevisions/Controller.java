package edu.bsu.cs222.wikipagerevisions;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.URL;

public class Controller {
    @FXML
    private TextField searchField;
    private String revisions[][] = new String[4][3]; //hold revision authors [][username, timestamp, comment], is there 4 everytime

    @FXML
    public void loadURL() {
        String URLStart = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=";
        String URLEnd = "&rvprop=timestamp|comment|user&rvlimit=4&redirects"; //handle redirects
        String search = searchField.getText();
        search = search.replace(" ", "_");
        search = URLStart + search + URLEnd;
        try {
            URL url = new URL(search);
            System.out.println(url);
            parseXMLFile(url);
        }
        catch(IOException e) { //Handle exceptions better
            throw new RuntimeException(e);
        }
    }

    public void parseXMLFile(URL url) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url.openStream());
            if (doesPageExist(doc)) {
                NodeList revisionsList = doc.getElementsByTagName("rev");
                for (int i = 0; i < revisionsList.getLength(); i++) {
                    Element tempElement = (Element) revisionsList.item(i);
                    revisions[i][0] = tempElement.getAttribute("user");
                    revisions[i][1] = tempElement.getAttribute("timestamp");
                    revisions[i][2] = tempElement.getAttribute("comment");
                }
                loadRevisionsToGUI();
            }
        }
        catch(Exception e) { //Change to actually handle correct Exceptions. Perhaps with a popup window for 'server down'?
            throw new RuntimeException(e);
        }
    }

    public boolean doesPageExist(Document doc)
    {
        Node check = doc.getElementById("page");
        Element _idx = (Element) check;
        if (_idx.getAttribute("_idx") == "-1")
        {
            return false;
        }
        return true;
    }

    public void loadRevisionsToGUI()
    {

    }
}
