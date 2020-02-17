package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.*;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorContext;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFileDefault;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class LoggerServer implements Runnable, RMIRegistration {

    private final static int serverPort = 3200;
    protected final int port;
    private Remote remoteServerObject;
    protected final Collection<RemoteViewHandler> viewers;

    public LoggerServer(final int port) {
        this.port = port;
        this.viewers = new LinkedList<>();
    }


    public static void main(final String[] args) {
        LoggerServer.startingRegistry();
        final LoggerServer server = new LoggerServer(LoggerServer.serverPort);
        try {
            server.registerServer();
            Thread serverThread = new Thread(server);
            serverThread.start();
        } catch (RemoteException e) {
            System.err.println("Error with the Remote Object");
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            System.err.println("Object ");
            e.printStackTrace();
        }
    }


    @Override
    public void register(RemoteViewHandler viewer) throws RemoteException {
        this.viewers.add(viewer);
    }

    public synchronized void notifyViewers(final LogMessage logMessage) {
        this.viewers.stream().forEach(remoteViewHandler -> {
            try {
                remoteViewHandler.push(logMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerServer() throws RemoteException, AlreadyBoundException {
        final Registry reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        this.remoteServerObject = UnicastRemoteObject.exportObject(this, 0);
        reg.bind("LoggerServer", this.remoteServerObject);
    }

    private static void startingRegistry() {
        Thread registryThread = new Thread(new RegistrySetup());
        registryThread.start();
    }


    public static File getFile(final String pathOfLogFile) throws IOException {
        File logFile = new File(pathOfLogFile);
        if (logFile.isFile()) {
            System.out.println("exists");
            return logFile;
        } else {
            System.out.println("dont exist");
            logFile.createNewFile();
            return logFile;
        }
    }

    public static String createFilePath(final String directory) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        new File(directory + File.separator + "vsk_group5_HS19").mkdirs();
        return directory + File.separator + "vsk_group5_HS19/"  + timeStamp + ".txt";
    }

    @Override
    public void run() {
        ServerSocket listen = null;
        try {
            listen = new ServerSocket(this.serverPort);
            final ExecutorService executor = Executors.newFixedThreadPool(5);
            File file = getFile(createFilePath(System.getProperty("user.home")));
            StringPersistorContext context = new StringPersistorContext();
            //Strategy to set as default
            context.setStrategy(new StringPersistorFileDefault(file));
            LogPersistor stringPersistorAdapter = new StringPersistorAdapter(context);
            while (true) {
                try {
                    System.out.println("Waiting for connection...");
                    final Socket client = listen.accept();

                    final LogHandler handler = new LogHandler(client, stringPersistorAdapter, this);
                    executor.execute(handler);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (final IOException ex) {
            ex.printStackTrace();
        } finally {
            if (listen != null && !listen.isClosed()) {
                try {
                    listen.close();
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
