package ch.hslu.vsk.logger.server;


import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.LogPersistor;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.Instant;
import java.util.logging.Logger;

public final class LogHandler implements Runnable {

    private static Logger logger = Logger.getLogger("Logger");
    private final Socket client;
    private ObjectInputStream inputStream;
    private final LogPersistor stringPersistorAdapter;
    private final LoggerServer server;

    public LogHandler(final Socket client, final LogPersistor stringPersistorAdapter, final LoggerServer server) throws IOException {
        this.client = client;
        this.stringPersistorAdapter = stringPersistorAdapter;
        this.inputStream = new ObjectInputStream(this.client.getInputStream());
        this.server = server;
    }


    @Override
    public void run() {
        logger.info("Connection to " + client);
        Object receivedObject = null;
        while (true) {
            try {
                receivedObject = this.inputStream.readObject();
            } catch (EOFException ex) {
                System.out.println("socket on port " + this.client.getPort() + " was closed, finished");
                return;
            } catch (IOException | ClassNotFoundException ex) {
                System.err.print(ex.getMessage());
            }
            if (receivedObject != null) {
                LogMessage receivedMessage = (LogMessage) receivedObject;
                LogMessage approvedMessage = new LogMessage(receivedMessage.getMessage(), receivedMessage.getLogLevel(), receivedMessage.getTimeStamp(), Instant.now());
                System.out.println(approvedMessage);
                this.stringPersistorAdapter.save(approvedMessage);
                this.server.notifyViewers(approvedMessage);
            }
        }
    }
    public LogPersistor getLogPersistor() {
        return stringPersistorAdapter;
    }

    public Socket getClient() {
        return client;
    }

}
