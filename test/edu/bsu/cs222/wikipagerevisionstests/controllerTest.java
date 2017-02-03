package edu.bsu.cs222.wikipagerevisionstests;

import edu.bsu.cs222.wikipagerevisions.*;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class controllerTest {

    @FXML
    private Controller test = new Controller();

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

    }

    @Test
    public void testParseXMLFile() {

    }

    public Document openXMLFile(String fileName) {
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
    public void testDoesPageExistTrue() {
        Document doc = openXMLFile("test-assests/Soup.xml");
        Assert.assertTrue(test.doesPageExist(doc));
    }

    @Test
    public void testDoesPageExistFalse() {
        Document doc = openXMLFile("test-assests/pageDoesNotExist.xml");
        Assert.assertFalse(test.doesPageExist(doc));
    }

    @Test
    public void testDoesPageHaveRevisionsTrue() {
        Document doc = openXMLFile("test-assests/Soup.xml");
        Assert.assertTrue(test.doesPageHaveRevisions(doc));
    }

    @Test
    public void testDoesPageHaveRevisionsFalse() {
        Document doc = openXMLFile("test-assests/pageDoesNotExist.xml");
        Assert.assertFalse(test.doesPageHaveRevisions(doc));
    }

    @Test
    public void testIsRedirectionTrue(){
        Document doc = openXMLFile("test-assests/redirection.xml");
        Assert.assertTrue(test.isRedirection(doc));
    }

    @Test
    public void testIsRedirectionFalse(){
        Document doc = openXMLFile("test-assests/Soup.xml");
        Assert.assertFalse(test.isRedirection(doc));
    }
}
