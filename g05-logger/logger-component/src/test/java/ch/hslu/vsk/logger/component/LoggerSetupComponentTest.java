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
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author freda
 */

public class LoggerSetupComponentTest {
    @Test
    public void testInterface() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertTrue(glogger instanceof LoggerSetup);
    }

    @Test
    public void testConstructor() throws IOException {
        final InetAddress inet = InetAddress.getByName("localhost");
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, inet, 443);
        assertEquals(glogger.getID(), "Logger1");
        assertEquals(glogger.getLogLevel(), LogLevel.INFO);
        assertEquals(glogger.getServerHost(), InetAddress.getByName("localhost"));
        assertEquals(glogger.getServerPort(), 443);
    }

    @Test
    public void testSetID() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        glogger.setID("Logger2");
        assertEquals(glogger.getID(), "Logger2");
    }

    @Test
    public void testGetID() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertEquals(glogger.getID(), "Logger1");
    }


    @Test
    public void testSetLogLevel() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        glogger.setLogLevel(LogLevel.DEBUG);
        assertEquals(glogger.getLogLevel(), LogLevel.DEBUG);
    }


    @Test
    public void testGetLogLevel() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertEquals(glogger.getLogLevel(), LogLevel.INFO);
    }

    @Test
    public void testSetServerHost() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        glogger.setServerHost(InetAddress.getByName("www.google.ch"));
        assertEquals(glogger.getServerHost(), InetAddress.getByName("www.google.ch"));
    }

    @Test
    public void testGetServerHost() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertEquals(glogger.getServerHost(), InetAddress.getByName("localhost"));
    }

    @Test
    public void testSetServerPort() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        glogger.setServerPort(80);
        assertEquals(glogger.getServerPort(), 80);
    }

    @Test
    public void testGetServerPort() throws IOException {
        final LoggerSetup glogger = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertEquals(glogger.getServerPort(), 443);
    }

    @Test
    public void testEquals() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertTrue(glogger1.equals(glogger2));
    }

    @Test
    public void testEquals2() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.DEBUG, InetAddress.getByName("localhost"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertFalse(glogger1.equals(glogger2));
    }

    @Test
    public void testEquals3() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponentDummy();
        assertFalse(glogger1.equals(glogger2));
    }

    @Test
    public void testEquals4() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 444);
        assertFalse(glogger1.equals(glogger2));
    }

    @Test
    public void testEquals5() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponent("Logger2", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertFalse(glogger1.equals(glogger2));
    }

    @Test
    public void testEquals6() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("8.8.8.8"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertFalse(glogger1.equals(glogger2));
    }

    @Test
    public void testHashCode() throws UnknownHostException {
        final LoggerSetup glogger1 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        final LoggerSetup glogger2 = new LoggerSetupComponent("Logger1", LogLevel.INFO, InetAddress.getByName("localhost"), 443);
        assertEquals(glogger1.hashCode(), glogger2.hashCode());
    }


    @Test
    public void testGetSocket() throws IOException {
        final Socket testSocket = mock(Socket.class);
        final LoggerSetupComponent setup = new LoggerSetupComponent("Logger1", LogLevel.DEBUG, InetAddress.getByName("localhost"), 443) {
            @Override
            public void openSocket() throws IOException {
                this.socket = testSocket;
                return;
            }
        };
        setup.openSocket();
        assertEquals(testSocket, setup.getSocket());
    }

    @Test
    public void testgetLogger1() throws UnknownHostException {
        final LoggerSetupComponent setup = new LoggerSetupComponent("Logger1", LogLevel.DEBUG, InetAddress.getByName("localhost"), 443);
        final Logger logger = setup.getLogger();
        assertTrue(logger instanceof LoggerComponent);
    }

    @Test
    public void testgetLogger2() throws UnknownHostException {
        final LoggerSetupComponent setup = new LoggerSetupComponent("Logger1", LogLevel.DEBUG, InetAddress.getByName("localhost"), 443);
        final LoggerComponent lc = (LoggerComponent) setup.getLogger();
        assertEquals(LogLevel.DEBUG, lc.loglevel);
        assertEquals(setup, lc.setup);
    }

    @Test
    public void testGetFile() throws UnknownHostException {
        final LoggerSetupComponent setup = new LoggerSetupComponent("Logger1", LogLevel.DEBUG, InetAddress.getByName("localhost"), 443);
        assertTrue(setup.getLocalFile() instanceof StringPersistorFileDefault);
    }
}