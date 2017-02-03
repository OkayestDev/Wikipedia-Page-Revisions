package edu.bsu.cs222.wikipagerevisions;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

public class Controller {
    @FXML
    private TextField searchField;
    private String revisions[][] = new String[4][3]; //hold revision authors [][username, timestamp, comment], is there 4 everytime

    public void handleButtonPress()
    {
        loadURL(searchField.getText());
    }

    public URL loadURL(String search) {
        String URLStart = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=";
        String URLEnd = "&rvprop=timestamp|comment|user&rvlimit=4&redirects";
        search = search.replace(" ", "_");
        search = URLStart + search + URLEnd;
        try {
            return new URL(search);
        }
        catch(IOException e) { //Handle exceptions better
            throw new RuntimeException(e);
        }
    }

    public Document URLtoDoc(URL url)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url.openStream());
            return doc;
        }
        catch(SAXException e) {
            System.out.println("Check internet connection or URL does not exist");
        }
        catch(IOException e) {
            System.out.println("A problem occured when retrieving URL");
        }
        catch(ParserConfigurationException e){
            System.out.println("URL is not in XML format");
        }
        return null;
    }

    public void parseXMLFile(Document doc) {
        try {
            if (doesPageExist(doc)) {
                if (doesPageHaveRevisions(doc)) {
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
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doesPageExist(Document doc)
    {
        NodeList check = doc.getElementsByTagName("page");
        Element idx = (Element) check.item(0);
        return !(idx.getAttribute("_idx").equals("-1"));
    }

    public boolean doesPageHaveRevisions(Document doc)
    {
        NodeList check = doc.getElementsByTagName("rev");
        return check.getLength() >= 1;
    }

    public boolean isRedirection(Document doc)
    {
        NodeList check = doc.getElementsByTagName("redirects");
        return check.getLength() >= 1;
    }

    public void loadRevisionsToGUI()
    {

    }
}
