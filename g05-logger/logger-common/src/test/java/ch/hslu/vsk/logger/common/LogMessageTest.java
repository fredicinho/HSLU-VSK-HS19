package ch.hslu.vsk.logger.common;

import ch.hslu.vsk.logger.api.LogLevel;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LogMessageTest {

    @Test
    void testConstructor() {
        final Instant time = Instant.now();
        final LogMessage lm = new LogMessage("bla", LogLevel.INFO, time);
        assertEquals(time, lm.getTimeStamp());
    }

    @Test
    void getMessage() {
        final LogMessage lm = new LogMessage("bla", LogLevel.INFO);
        assertEquals("bla", lm.getMessage());
    }

    @Test
    void getLogLevel() {
        final LogMessage lm = new LogMessage("bla", LogLevel.INFO);
        assertEquals(LogLevel.INFO, lm.getLogLevel());
    }

    @Test
    void getTimeServerReceivedLog() {
        final Instant server = Instant.now();
        final LogMessage lm = new LogMessage("bla", LogLevel.INFO, server, server);
        assertEquals(server, lm.getTimeServerReceivedLog());
    }
}