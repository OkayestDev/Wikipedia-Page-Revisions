package edu.bsu.cs222.wikipagerevisions;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn userColumn;
    @FXML
    private TableColumn timestampColumn;
    private Model model = new Model();
    private List<Revisions> revisionsList = new ArrayList<>();

    public void handleButtonPress() {
        Task<Void> task = new Task<Void>() { //starts a thread?
            @Override
            protected Void call() throws Exception {
                URL url = model.loadURL(searchField.getText());
                Document doc = model.URLtoDoc(url);
                revisionsList = model.parseRevisions(doc);
                loadRevisionsToGUI();
                return null;
            }
        };
    }

    public void loadRevisionsToGUI()
    {
        Iterator<Revisions> iter = revisionsList.iterator();
        int count = 0;
        while(iter.hasNext())
        {
            Revisions rev = iter.next();
            userColumn.setText(rev.getUser());
            timestampColumn.setText(rev.getTimestamp());
            count++;
        }
        System.out.println(count);
    }
}
