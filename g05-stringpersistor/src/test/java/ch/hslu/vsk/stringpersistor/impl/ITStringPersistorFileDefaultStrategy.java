package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ITStringPersistorFileDefaultStrategy {


    @Test
    public void testStringPersistorReadWriteFromFile() throws IOException {
        int numberOfMessages = 10;
        StringPersistorContext context = new StringPersistorContext();
        File file = new File("target/testfile.txt");
        context.setStrategy(new StringPersistorFileDefault(file));
        file.createNewFile();

        for(int i = 0; i<numberOfMessages; i++){
            context.save(Instant.now(),  "LogMessage{loglevel= INFO, message= 'Hello, world! 9, timeServerReceivedLog= 2019-11-05T16:21:58.111280Z}" + i);
        }
        List <PersistedString> myList = new ArrayList <>();
        myList = context.get(10);
        assertEquals(numberOfMessages, myList.size());
    }

}
