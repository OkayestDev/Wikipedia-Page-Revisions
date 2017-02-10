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
        String URLEnd = "&rvprop=timestamp|comment|user&rvlimit=30&redirects";
        String search = "Soup";
        String URL = URLStart + search + URLEnd;
        Assert.assertEquals(URL, test.loadURL(search).toString());
    }

    @Test
    public void testParseRevisions() {
        test.setDoc(openXMLFile("test-assets/Soup.xml"));
        Revisions rev = new Revisions("Northamerica1000", "2016-12-23T16:25:19Z");
        String parsedRevisions = test.parseRevisions().get(0).toString();
        String testString = rev.toString();
        Assert.assertTrue(testString.equals(parsedRevisions));
    }

    @Test
    public void testDoesPageExistTrue() {
        test.setDoc(openXMLFile("test-assets/Soup.xml"));
        Assert.assertTrue(test.doesPageExist());
    }

    @Test
    public void testDoesPageExistFalse() {
        test.setDoc(openXMLFile("test-assets/pageDoesNotExist.xml"));
        Assert.assertFalse(test.doesPageExist());
    }

    @Test
    public void testDoesPageHaveRevisionsTrue() {
        test.setDoc(openXMLFile("test-assets/Soup.xml"));
        Assert.assertTrue(test.doesPageHaveRevisions());
    }

    @Test
    public void testDoesPageHaveRevisionsFalse() {
        test.setDoc(openXMLFile("test-assets/pageDoesNotExist.xml"));
        Assert.assertFalse(test.doesPageHaveRevisions());
    }

    @Test
    public void testIsRedirectionTrue(){
        test.setDoc(openXMLFile("test-assets/redirection.xml"));
        Assert.assertTrue(test.isRedirection());
    }

    @Test
    public void testIsRedirectionFalse(){
        test.setDoc(openXMLFile("test-assets/Soup.xml"));
        Assert.assertFalse(test.isRedirection());
    }

    @Test
    public void testGetRedirection()
    {
        test.setDoc(openXMLFile("test-assets/redirection.xml"));
        String check = "Redirected: Obama to Barack Obama";
        Assert.assertTrue(check.equals(test.getRedirection()));
    }
}
