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
    private Label Notify;

    private Model model = new Model();
    private List<Revisions> revisionsList = new ArrayList<>();
    private boolean hasBeenSearched = false;

    @FXML
    public void handleSearchButton() {
        if (!searchField.getText().equals("")) {
            timestampColumn.setText("Timestamp");
            Platform.runLater(() -> {
                clear();
                model.clear();
                executeModel();
            });
        }
    }

    public void executeModel() {
        URL url = model.loadURL(searchField.getText());
        if (handleBadConnectionOrURL(url)) {
            Document doc = model.URLtoDoc(url);
            revisionsList = model.parseRevisions(doc);
            handleRedirection(doc);
            handlePageDoesNotExist(doc);
            loadRevisionsToGUI();
        }
    }

    @FXML
    public void handleUserCountButton() {
        if (hasBeenSearched) {
            timestampColumn.setText("Revision Count");
            Platform.runLater(() -> {
                revisionsTable.getItems().removeAll(revisionsList);
                loadUserCountsToGUI();
            });
        }
    }

    @FXML
    private void loadRevisionsToGUI() {
        Iterator<Revisions> iter = revisionsList.iterator();
        hasBeenSearched = true;
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        while(iter.hasNext()) {
            Revisions rev = iter.next();
            revisionsTable.getItems().add(rev);
        }
    }

    private void loadUserCountsToGUI() {
        Iterator<Revisions> iter = revisionsList.iterator();
    }

    private void clear() {
        revisionsTable.getItems().removeAll(revisionsList);
        Notify.setText("");
    }

    private void handleRedirection(Document doc){
        if (model.isRedirection(doc)) {
            Notify.setText(model.getRedirection(doc));
        }
    }

    private void handlePageDoesNotExist(Document doc) {
        if (!model.doesPageExist(doc)) {
            Notify.setText("Page does not exist");
        }
    }

    private boolean handleBadConnectionOrURL(URL url) {
        try {
            url.openConnection();
            return true;
        }
        catch(Exception e) {
            Notify.setText("Check Internet Connection or URL");
            return false;
        }
    }
}
