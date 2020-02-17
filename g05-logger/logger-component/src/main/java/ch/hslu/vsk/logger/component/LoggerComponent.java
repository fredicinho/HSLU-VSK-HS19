/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.logger.component;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.stringpersistor.api.PersistedString;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.List;


/**
 * @author freda
 */
public class LoggerComponent implements Logger {

    protected LogLevel loglevel;
    private ObjectOutputStream outStream;
    protected LoggerSetupComponent setup;
    private Boolean localLoggerFlag = false;


    public LoggerComponent(final LogLevel li, final LoggerSetupComponent setup) {
        this.loglevel = li;
        this.setup = setup;
    }

    @Override
    public void log(final String message, final LogLevel msgLogLevel) {
        if (this.loglevel.ordinal() > msgLogLevel.ordinal()) {
            return;
        }
        sendMessage(new LogMessage(message, msgLogLevel));
    }

    public void sendMessage(final LogMessage logMessage) {
        if (this.localLoggerFlag) {
            this.logLocally(logMessage);
            try {
                this.setup.openSocket();
                this.outStream = new ObjectOutputStream(this.setup.getSocket().getOutputStream());
                final List<PersistedString> locallyLogged = this.setup.getLocalFile().get(Integer.MAX_VALUE);
                for (PersistedString ps : locallyLogged) {
                    System.out.println(ps.toString());
                    this.outStream.writeObject(MessageFormatterForClient.parse(ps));
                    this.outStream.flush();
                }
                System.out.println("All local saved messages where resent");
                this.setup.closeSocket();
                this.setLocalLoggerFlag(false);
            } catch (SocketException ex) {
                System.err.println("connection to logger server still not possible");
                System.err.println("logMessage will continued logged locally ");
            } catch (IOException ex) {
                System.err.println("Socket connection couldn't be made");
                ex.printStackTrace();
            }
        } else {
            try {
                this.setup.openSocket();
                this.outStream = new ObjectOutputStream(this.setup.getSocket().getOutputStream());
                this.outStream.writeObject(logMessage);
                this.outStream.flush();
                this.setup.closeSocket();
            } catch (IOException e) {
                System.err.println("connection to logger server not possible");
                System.err.println("logMessage will be logged locally now");
                this.setLocalLoggerFlag(true);
                this.logLocally(logMessage);
                e.printStackTrace();
            }

        }

    }

    public void setLocalLoggerFlag(final Boolean flag) {
        this.localLoggerFlag = flag;
    }

    public void logLocally(final LogMessage lm) {
        this.setup.getLocalFile().save(lm.getTimeStamp(), MessageFormatterForClient.format(lm).getPayload());
    }
}

