package ch.hslu.vsk.logger.server;


import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.RMIRegistration;
import ch.hslu.vsk.logger.common.RemoteViewHandler;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerServerTest {
    @Test
    public void connectToServer() {
        final String host = "localhost";
        try (Socket client = new Socket(host, 3200)) {
            assertEquals(true, client.isConnected());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() {
        final LoggerServer server = new LoggerServer(3200);
        assertTrue(server instanceof Runnable);
        assertTrue(server instanceof RMIRegistration);
        assertEquals(3200, server.port);
    }

    @Test
    public void testRegister() throws RemoteException {
        final LoggerServer server = new LoggerServer(3200);
        server.register(new RemoteViewHandler() {
            @Override
            public void push(LogMessage logMessage) throws RemoteException {
                return;
            }
        });
        assertFalse(server.viewers.isEmpty());
    }

    @Test
    public void testGetFileThatExists() throws IOException {
        File file = new File("target/test.txt");
        assertEquals(file,LoggerServer.getFile("target/test.txt"));
    }

    @Test
    public void testGetFileThatNotExists() throws IOException {
        File file = new File("target/notExisting.txt");
        assertEquals( file, LoggerServer.getFile("target/notExisting.txt"));
    }

    @Test
    public void testCreatePath() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        String directory = System.getProperty("user.home");
        String path = directory + File.separator + "vsk_group5_HS19/"  + timeStamp + ".txt";
        assertEquals(path, LoggerServer.createFilePath(directory));
    }

}
