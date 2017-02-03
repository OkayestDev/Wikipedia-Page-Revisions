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

import javax.swing.text.Document;
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("test-assests//Soup"):
    }

    @Test
    public void testDoesPageExist() {
        Assert.assertTrue(test.doesPageExist(doc));
    }

    @Test
    public void testDoesPageHaveRevisions() {

    }

    @Test
    public void testIsRedirection(){

    }
}
