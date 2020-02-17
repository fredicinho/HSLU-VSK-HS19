/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.logger.component;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFileDefault;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 *
 * @author freda
 */
public class LoggerSetupComponent implements LoggerSetup {
    private String id;
    private LogLevel li;
    private InetAddress inetad;
    private int port;
    private LoggerComponent log;
    protected Socket socket;
    private StringPersistorFileDefault localFile;


    public LoggerSetupComponent() {
        this.initializeLocalLogger();
    }

    public LoggerSetupComponent(final String id, final LogLevel li, final InetAddress inetad, final int port) {
        this.id = id;
        this.li = li;
        this.inetad = inetad;
        this.port = port;
        this.initializeLocalLogger();
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LoggerSetupComponent)) {
            return false;
        }
        final LoggerSetupComponent other = (LoggerSetupComponent) obj;
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.li, other.li)) {
            return false;
        }
        if (!Objects.equals(this.inetad, other.inetad)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, li, inetad, port);
    }

    @Override
    public Logger getLogger() {
            this.log = new LoggerComponent(this.li, this);
        return this.log;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(final String s) {
        this.id = s;
    }

    @Override
    public LogLevel getLogLevel() {
        return this.li;
    }

    @Override
    public void setLogLevel(final LogLevel logLevel) {
        this.li = logLevel;
    }

    @Override
    public InetAddress getServerHost() {
        return this.inetad;
    }

    @Override
    public void setServerHost(final InetAddress inetAddress) {
        this.inetad = inetAddress;
    }

    @Override
    public int getServerPort() {
        return this.port;
    }

    @Override
    public void setServerPort(final int i) {
        this.port = i;
    }

    public void openSocket() throws IOException {
        this.socket = new Socket(this.getServerHost().getHostName(), this.getServerPort());
    }

    public void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            System.err.println("Closing of Socket failed");
        }
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void initializeLocalLogger() {
        try {
            this.localFile = new StringPersistorFileDefault(this.getFile(this.createFilePath(System.getProperty("user.home"))));
        } catch (final IOException ex) {
            System.err.println("Error creating local log file, cause: " + ex.getMessage());
        }
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
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        new File(directory + File.separator + "vsk_group5_HS19").mkdirs();
        return directory + File.separator + "vsk_group5_HS19/"  + timeStamp + ".txt";
    }

    public StringPersistorFileDefault getLocalFile() {
        return this.localFile;
    }
}
