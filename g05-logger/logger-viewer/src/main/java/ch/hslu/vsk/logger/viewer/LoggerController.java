package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.LogMessage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author MatthiasKüng
 */
public class LoggerController {

    private LogList loglist;

    @FXML
    private TableView<LogMessage> logTable;

    @FXML
    private TableColumn<LogMessage, String> message;
    
    @FXML
    private TableColumn<LogMessage, String> loglevel;
    
    @FXML
    private TableColumn<LogMessage, String> timeServerReceivedLog;
    
    @FXML
    private TableColumn<LogMessage, String> timeLogged;

    public void initializeModel(final LogList loglist) {
        if (this.loglist != null) {
            throw new IllegalStateException("Model was already initialized.");
        }
        this.loglist = loglist;
        this.message.setCellValueFactory(new PropertyValueFactory<>("message"));
        this.loglevel.setCellValueFactory(new PropertyValueFactory<>("loglevel"));
        this.timeServerReceivedLog.setCellValueFactory(new PropertyValueFactory<>("timeServerReceivedLog"));
        this.timeLogged.setCellValueFactory(new PropertyValueFactory<>("timeLogged"));
        
    }

    public void updateLogTable() {
        ObservableList<LogMessage> observableList;
        observableList = this.loglist.getObservableList();

        this.logTable.setItems(observableList);
        
    }
}


