package edu.bsu.cs222.wikipagerevisions;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Document;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller {
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Revisions, String> userColumn;
    @FXML
    private TableColumn<Revisions, String> timestampColumn;
    @FXML
    private TableView<Revisions> revisionsTable;
    @FXML
    private Label redirectionNotify;

    private Model model = new Model();
    private List<Revisions> revisionsList = new ArrayList<>();
    private boolean hasBeenSearched = false;

    @FXML
    public void handleSearchButton() {
        if (!searchField.getText().equals("")) {
            timestampColumn.setText("Timestamp");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    clear();
                    model.clear();
                    URL url = model.loadURL(searchField.getText());
                    Document doc = model.URLtoDoc(url);
                    revisionsList = model.parseRevisions(doc);
                    handleRedirection(doc);
                    loadRevisionsToGUI();
                }
            });
            {
            }
        }
    }

    @FXML
    public void handleUserCountButton() {
        if (hasBeenSearched) {
            timestampColumn.setText("Revision Count");
            Platform.runLater(new Runnable() {
                public void run() {
                    revisionsTable.getItems().removeAll(revisionsList);
                    loadUserCountsToGUI();
                }
            });
        }
    }

    @FXML
    public void loadRevisionsToGUI() {
        Iterator<Revisions> iter = revisionsList.iterator();
        hasBeenSearched = true;
        while(iter.hasNext()) {
            Revisions rev = iter.next();
            revisionsTable.getItems().add(rev);
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        }
    }

    public void loadUserCountsToGUI() {
        Iterator<Revisions> iter = revisionsList.iterator();
    }

    public void clear() {
        revisionsTable.getItems().removeAll(revisionsList);
        redirectionNotify.setText("");
    }

    public void handleRedirection(Document doc){
        if (model.isRedirection(doc)) {
            redirectionNotify.setText(model.getRedirection(doc));
        }
    }
}
