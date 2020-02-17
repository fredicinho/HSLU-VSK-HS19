package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.api.Logger;
import ch.hslu.vsk.logger.api.LoggerSetup;

import java.net.InetAddress;

public class LoggerSetupComponentDummy implements LoggerSetup {
    @Override
    public void setID(String id) {
        return;
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        return;
    }

    @Override
    public void setServerHost(InetAddress host) {
        return;
    }

    @Override
    public void setServerPort(int port) {
        return;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public LogLevel getLogLevel() {
        return null;
    }

    @Override
    public InetAddress getServerHost() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }
}
