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
    private wikiXMLParser parser = new wikiXMLParser();
    private boolean hasBeenSearched = false;

    @FXML
    public void handleSearchButton() {
        if (!searchField.getText().equals("")) {
            timestampColumn.setText("Timestamp");
            Platform.runLater(() -> {
                clear();
                parser.clear();
                notify.setText("");
                hasBeenSearched = true;
                parser.wikiParser(searchField.getText());
                checkNotifications();
                loadListToGUI(parser.getRevisionsList(), "Timestamp");
            });
        }
    }

    @FXML
    public void handleRevisionCountButton() {
        if (hasBeenSearched) {
            timestampColumn.setText("Revision Count");
            Platform.runLater(() -> {
                clear();
                loadListToGUI(parser.getUniqueUserRevisionsList(), "RevisionsCount");
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
        revisionsTable.getItems().removeAll(parser.getRevisionsList());
        revisionsTable.getItems().removeAll(parser.getUniqueUserRevisionsList());
    }

    private void checkNotifications() {
        if (parser.isGoodConnection()) {
            handleRedirection();
            handlePageDoesNotExist();
        }
        else {
            handleBadConnection();
        }
    }

    private void handleRedirection(){
        if (parser.isRedirection()) {
            notify.setText(parser.getRedirection());
        }
    }

    private void handlePageDoesNotExist() {
        if (!parser.doesPageExist()) {
            notify.setText("Page does not exist");
        }
    }

    private void handleBadConnection() {
        if (!parser.isGoodConnection()) {
            notify.setText("Page could not be Reached. Check internet connection or the server may be down");
        }
    }
}