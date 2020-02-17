package ch.hslu.vsk.stringpersistor.impl;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringPersistorFileDefaultStrategyTest {

    @Test
    public void testSetFileGetReader() throws IOException {
        File file = new File("target/testfile.txt");
        file.createNewFile();
        final StringPersistorFileDefault stringPersistorFileDefault = new StringPersistorFileDefault();
        stringPersistorFileDefault.setFile(file);

        assert(stringPersistorFileDefault.getReader() != null);

    }
    @Test
    public void testSetFileGetWriter() throws IOException {
        File file = new File("target/testfile.txt");
        file.createNewFile();
        final StringPersistorFileDefault stringPersistorFileDefault = new StringPersistorFileDefault();
        stringPersistorFileDefault.setFile(file);

        assert(stringPersistorFileDefault.getWriter() != null);

    }

    @Test
    public void testFileNotExists() throws IOException {
        File file = new File("target/notExisting.txt");
        final StringPersistorFileDefault stringPersistorFileDefault = new StringPersistorFileDefault();
        assertThrows(IllegalArgumentException.class, () -> {
            stringPersistorFileDefault.setFile(file);
        });
    }

    @Test
    public void testReadFromMemory(){
        int numberOfMessages = 10;
        StringPersistorContext context = new StringPersistorContext();
        context.setStrategy(new StringPersistorMemory());
        for(int i = 0; i < numberOfMessages; i++){
            context.save(Instant.now(), "aa");
        }
        assertEquals(numberOfMessages, context.get(numberOfMessages).size());
    }
}
