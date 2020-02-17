package ch.hslu.vsk.logger.viewer;

/**
 *
 * @author MatthiasKüng
 */
import ch.hslu.vsk.logger.common.RMIRegistration;
import ch.hslu.vsk.logger.common.RemoteViewHandler;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoggerViewer extends Application {

    private Remote handler;

    /**
     *
     * @throws RemoteException wenn in der Remote Methode eine Ausnahme
     * passiert.
     * @throws NotBoundException wenn das Remote-Objek nicht registriert ist.
     */
    @Override
    public void start(Stage primaryStage) throws IOException, RemoteException, NotBoundException, Exception {
        primaryStage.setTitle("Logger Gruppe 05");
        final BorderPane root = new BorderPane();
        final FXMLLoader tableLoader = new FXMLLoader(this.getClass().getResource("/LogList.fxml"));
        root.setCenter((Node) tableLoader.load());
        final LoggerController controller = tableLoader.getController();
        final LogList loglist = new LogList();
        controller.initializeModel(loglist);
        controller.updateLogTable();
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
        this.registerViewer(loglist, this.getParameters().getRaw());

    }

    public static void main(final String[] args) {
        launch(args);
    }

    /**
     *
     *
     * @throws RemoteException wenn in der Remote Methode eine Ausnahme
     * passiert.
     * @throws NotBoundException wenn das Remote-Objek nicht registriert ist.
     */
    private void registerViewer(final LogList loglist, final List<String> args) throws RemoteException, NotBoundException {
        String host = "localhost";
        if (args.size() > 0) {
            host = args.get(0);
        }
        final Registry reg = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
        final RMIRegistration registration = (RMIRegistration) reg.lookup("LoggerServer");
        this.handler = UnicastRemoteObject.exportObject(loglist, 0);
        registration.register((RemoteViewHandler) this.handler);
    }

}
