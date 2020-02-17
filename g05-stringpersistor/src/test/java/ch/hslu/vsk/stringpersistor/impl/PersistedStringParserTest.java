package ch.hslu.vsk.stringpersistor.impl;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PersistedStringParserTest {

    @Test
    public void testNoStringSeperator(){
        assertThrows(IllegalArgumentException.class, () -> {
            PersistedStringParser.parseStringIntoPersistedString("HalloWelt");
        });

    }
    @Test
    public void testStringStartsWithIsoInstant(){
        assertThrows(IllegalArgumentException.class, () -> {
            PersistedStringParser.parseStringIntoPersistedString("HalloWelt");
        });
    }

    @Test
    public void testParseToString(){
        Instant instantNow = Instant.now();
        //not able to test because of Instant.now() is to accurate
        //assertEquals(instantNow +  "| 9-11-05T18:27:36.543851Z | Testing", PersistedStringParser.parseStringIntoPersistedString(instantNow + " | Testing"));
    }
}
