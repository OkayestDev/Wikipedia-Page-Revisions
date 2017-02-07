package edu.bsu.cs222.wikipagerevisions;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Model model = new Model();
    private List<Revisions> revisionsList = new ArrayList<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    @FXML
    public void handleSearchButtonPress() {
        if (!searchField.getText().equals("")) {
            timestampColumn.setText("Timestamp");
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    clear();
                    URL url = model.loadURL(searchField.getText());
                    Document doc = model.URLtoDoc(url);
                    revisionsList = model.parseRevisions(doc);
                    loadRevisionsToGUI();
                }
            });
            {
            }
        }
    }

    @FXML
    public void handleUserCountButton() {
        timestampColumn.setText("Revision Count");
    }

    public void clear() {
        revisionsTable.getItems().removeAll(revisionsList);
        model.clear();
    }

    @FXML
    public void loadRevisionsToGUI() {
        Iterator<Revisions> iter = revisionsList.iterator();
        while(iter.hasNext()) {
            Revisions rev = iter.next();
            revisionsTable.getItems().add(rev);
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        }
    }
}
