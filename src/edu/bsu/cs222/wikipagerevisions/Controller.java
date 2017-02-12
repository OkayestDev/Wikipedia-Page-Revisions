package edu.bsu.cs222.wikipagerevisions;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Label notify;
    private wikiXMLParser wikiParser = new wikiXMLParser();
    private boolean hasBeenSearched = false;


    @FXML
    public void handleSearchButton() {
        if (!searchField.getText().equals("")) {
            timestampColumn.setText("Timestamp");
            Platform.runLater(() -> {
                clear();
                wikiParser.clear();
                notify.setText("");
                hasBeenSearched = true;
                wikiParser.wikiParser(searchField.getText());
                checkNotifications();
                loadListToGUI(wikiParser.getRevisionsList(), "Timestamp");
            });
        }
    }

    @FXML
    public void handleRevisionCountButton() {
        if (hasBeenSearched) {
            timestampColumn.setText("Revision Count");
            Platform.runLater(() -> {
                clear();
                loadListToGUI(wikiParser.getUniqueUserRevisionsList(), "RevisionsCount");
            });
        }
    }

    @FXML
    private void loadListToGUI(List<Revisions> rev, String secondColumn) {
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>(secondColumn));
        for(Revisions temp: rev) {
            revisionsTable.getItems().add(temp);
        }
    }

    private void clear() {
        revisionsTable.getItems().removeAll(wikiParser.getRevisionsList());
        revisionsTable.getItems().removeAll(wikiParser.getUniqueUserRevisionsList());
    }

    private void checkNotifications() {
        if (wikiParser.isGoodConnection()) {
            handleRedirection();
            handlePageDoesNotExist();
        }
        else {
            handleBadConnection();
        }
    }

    private void handleRedirection(){
        if (wikiParser.isRedirection()) {
            notify.setText(wikiParser.getRedirection());
        }
    }

    private void handlePageDoesNotExist() {
        if (!wikiParser.doesPageExist()) {
            notify.setText("Page does not exist");
        }
    }

    private void handleBadConnection() {
        if (!wikiParser.isGoodConnection()) {
            notify.setText("Page could not be Reached. Check internet connection or the server may be down");
        }
    }
}