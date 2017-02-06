package edu.bsu.cs222.wikipagerevisionstests;

import edu.bsu.cs222.wikipagerevisions.*;

import javafx.fxml.FXML;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;

public class modelTest {
    @FXML
    private Model test = new Model();

    private Document openXMLFile(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            return doc;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLoadURL() {
        String URLStart = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=";
        String URLEnd = "&rvprop=timestamp|comment|user&rvlimit=4&redirects";
        String search = "Soup";
        String URL = URLStart + search + URLEnd;
        Assert.assertEquals(URL, test.loadURL(search).toString());
    }

    @Test
    public void testURLtoDoc()
    {
        try {
            URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=gasdf&rvprop=timestamp|comment|user&rvlimit=30&redirects");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url.openStream());
            Document testDoc = test.URLtoDoc(url);
            Assert.assertTrue(doc.toString().equals(testDoc.toString()));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testParseRevisions() {
        Document doc = openXMLFile("test-assets/Soup.xml");
        Revisions rev = new Revisions();
        rev.setInformation("Northamerica1000", "2016-12-23T16:25:19Z", "/* See also */ + * [[Soup and sandwich]]");
        String parsedRevisions = test.parseRevisions(doc).get(0).toString();
        String testString = rev.toString();
        Assert.assertTrue(testString.equals(parsedRevisions));
    }


    @Test
    public void testDoesPageExistTrue() {
        Document doc = openXMLFile("test-assets/Soup.xml");
        Assert.assertTrue(test.doesPageExist(doc));
    }

    @Test
    public void testDoesPageExistFalse() {
        Document doc = openXMLFile("test-assets/pageDoesNotExist.xml");
        Assert.assertFalse(test.doesPageExist(doc));
    }

    @Test
    public void testDoesPageHaveRevisionsTrue() {
        Document doc = openXMLFile("test-assets/Soup.xml");
        Assert.assertTrue(test.doesPageHaveRevisions(doc));
    }

    @Test
    public void testDoesPageHaveRevisionsFalse() {
        Document doc = openXMLFile("test-assets/pageDoesNotExist.xml");
        Assert.assertFalse(test.doesPageHaveRevisions(doc));
    }

    @Test
    public void testIsRedirectionTrue(){
        Document doc = openXMLFile("test-assets/redirection.xml");
        Assert.assertTrue(test.isRedirection(doc));
    }

    @Test
    public void testIsRedirectionFalse(){
        Document doc = openXMLFile("test-assets/Soup.xml");
        Assert.assertFalse(test.isRedirection(doc));
    }

    @Test
    public void testGetRedirection()
    {
        Document doc = openXMLFile("test-assets/redirection.xml");
        String check = "Barack Obama";
        Assert.assertTrue(check.equals(test.getRedirection(doc)));
    }
}
