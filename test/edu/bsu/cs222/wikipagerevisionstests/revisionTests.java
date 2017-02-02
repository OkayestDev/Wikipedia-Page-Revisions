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
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class revisionTests {

    @FXML
    private Controller controllerTest = new Controller();

    @FXML
    @Test
    public void testLoadURL() {
//        controllerTest.searchField.setText("Barrack Obama");
//        try {
//            URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=Barrack_Obama" +
//                    "&rvprop=timestamp|comment|user&rvlimit=4");
//            Assert.assertEquals(url, controllerTest.loadURL());
//        }
//        catch(IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
