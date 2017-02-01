package edu.bsu.cs222.wikipagerevisions;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class Controller {
    private String authors[][] = new String[10][3]; //hold revision authors [][username, timestamp, comment]

    @FXML
    private TextField searchFeild;

    public void openXMLFile()
    {
    
    }
}
