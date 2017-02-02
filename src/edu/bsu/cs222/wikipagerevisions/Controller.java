package edu.bsu.cs222.wikipagerevisions;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;

public class Controller {

    @FXML
    private TextField searchField;

    private String authors[][] = new String[10][3]; //hold revision authors [][username, timestamp, comment]
    private int count;

    @FXML
    public void openXML()
    {
        String urlStart = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=";
        String urlEnd = "&rvprop=timestamp|comment|user&rvlimit=4;";
        String search = searchField.getText();
        search = search.replace(" ", "_");
        search = urlStart + search + urlEnd;

        try {
            URL url = new URL(search);
            //System.out.println(url);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void readXMLFile()
    {
        count = 0;
    }
}
