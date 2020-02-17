package ch.hslu.vsk.stringpersistor.impl;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;

public class StringPersistorTest {


    @Test
    public void noFileSet() throws IOException {
        final StringPersistorFileDefault stringPersistorFileDefault = new StringPersistorFileDefault();
        assertThrows(IllegalArgumentException.class, () -> {
            stringPersistorFileDefault.save(Instant.now(), "Exception" );
        });
    }

    @Test
    public void readingNoFileSet() throws IOException {
        final StringPersistorFileDefault stringPersistorFileDefault = new StringPersistorFileDefault();
        assertThrows(IllegalStateException.class, () -> {
            stringPersistorFileDefault.get(10);
        });
    }

    @Test
    public void testSaveExtreme(){
        StringPersistorMemory stringPersistorMemory = new StringPersistorMemory();
        for(int i= 0; i < 1000; i++){
            stringPersistorMemory.save(Instant.now(), "dummy");
        }
        assertEquals(1000, stringPersistorMemory.get(1000).size());
    }

    @Test
    public void testGetExtreme(){

    }


}
