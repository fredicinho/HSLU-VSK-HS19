package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.RemoteViewHandler;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MatthiasKüng
 */
public class LogList implements RemoteViewHandler{

    private final List<LogMessage> list = new ArrayList<>();
    private final ObservableList<LogMessage> observableList
            = FXCollections.synchronizedObservableList(FXCollections.observableList(this.list));

    public void addLogMessage(final LogMessage message) {
        this.observableList.add(message);
    }

    public ObservableList<LogMessage> getObservableList() {
        return this.observableList;
    }

    @Override
    public void push(final LogMessage message) throws RemoteException {
        this.observableList.add(message);
    }

}
