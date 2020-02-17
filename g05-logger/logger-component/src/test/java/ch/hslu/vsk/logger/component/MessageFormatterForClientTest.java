package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MessageFormatterForClientTest {

    @Test
    void testFormat1() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.INFO, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        assertEquals("[INFO] Hi", ps.getPayload());
        assertEquals(ps.getTimestamp(), timestampTest);
    }

    @Test
    void testFormat2() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.DEBUG, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        assertEquals("[DEBUG] Hi", ps.getPayload());
        assertEquals(ps.getTimestamp(), timestampTest);
    }

    @Test
    void testFormat3() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.FATAL, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        assertEquals("[FATAL] Hi", ps.getPayload());
        assertEquals(ps.getTimestamp(), timestampTest);
    }

    @Test
    void testFormat4() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.WARN, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        assertEquals("[WARN] Hi", ps.getPayload());
        assertEquals(ps.getTimestamp(), timestampTest);
    }

    @Test
    void testFormat5() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.ERROR, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        assertEquals("[ERROR] Hi", ps.getPayload());
        assertEquals(ps.getTimestamp(), timestampTest);
    }

    @Test
    void testParse1() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.INFO, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        final LogMessage result = MessageFormatterForClient.parse(ps);
        assertEquals(LogLevel.INFO, result.getLogLevel());
        assertEquals("Hi", result.getMessage());
        assertEquals(timestampTest, result.getTimeStamp());
    }

    @Test
    void testParse2() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.DEBUG, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        final LogMessage result = MessageFormatterForClient.parse(ps);
        assertEquals(LogLevel.DEBUG, result.getLogLevel());
        assertEquals("Hi", result.getMessage());
        assertEquals(timestampTest, result.getTimeStamp());
    }

    @Test
    void testParse3() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.ERROR, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        final LogMessage result = MessageFormatterForClient.parse(ps);
        assertEquals(LogLevel.ERROR, result.getLogLevel());
        assertEquals("Hi", result.getMessage());
        assertEquals(timestampTest, result.getTimeStamp());
    }

    @Test
    void testParse4() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.WARN, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        final LogMessage result = MessageFormatterForClient.parse(ps);
        assertEquals(LogLevel.WARN, result.getLogLevel());
        assertEquals("Hi", result.getMessage());
        assertEquals(timestampTest, result.getTimeStamp());
    }

    @Test
    void testParse5() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.FATAL, timestampTest);
        final PersistedString ps = MessageFormatterForClient.format(lm);
        final LogMessage result = MessageFormatterForClient.parse(ps);
        assertEquals(LogLevel.FATAL, result.getLogLevel());
        assertEquals("Hi", result.getMessage());
        assertEquals(timestampTest, result.getTimeStamp());
    }

    @Test
    void testParse6() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.FATAL);
        final PersistedString ps = new PersistedString(lm.getTimeStamp(), lm.toString());
        final LogMessage lm1 = MessageFormatterForClient.parse(ps);
        assertEquals(null, lm1);
    }

    @Test
    void testParse7() {
        final Instant timestampTest = Instant.now();
        final LogMessage lm = new LogMessage("Hi", LogLevel.FATAL);
        final PersistedString ps = new PersistedString(lm.getTimeStamp(), "hi");
        final LogMessage lm1 = MessageFormatterForClient.parse(ps);
        assertEquals(null, lm1);
    }

    @Test
    void testClass() {
        final MessageFormatterForClient ms = new MessageFormatterForClient();
        assertTrue(ms instanceof MessageFormatterForClient);
    }


}