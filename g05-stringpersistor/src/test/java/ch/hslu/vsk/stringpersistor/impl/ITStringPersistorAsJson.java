package ch.hslu.vsk.stringpersistor.impl;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ITStringPersistorAsJson {
    @Test
    public void testCreationAndSavingToJsonFormat() throws IOException {
        StringPersistorContext context = new StringPersistorContext();
        File file = new File("./target/test.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        context.setStrategy(new StringPersistorFileAsJson(file));
        context.save(Instant.now(), "2019-11-25T16:47:49.298285Z | ./target/g05-loggercomponent-2.0.0-SNAPSHOT.jar");

        assertEquals(context.get(1).size(), 1);
    }
}
