package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ITStringPersistorFileDefault {

   @Test
    public void testStringPersistorReadWriteFromFile() throws IOException {
        int numberOfMessages = 10;
        StringPersistorContext context = new StringPersistorContext();
        File file = new File("target/testfile.txt");
       if(!file.exists()){
           file.createNewFile();
       }
        context.setStrategy(new StringPersistorFileDefault(file));


        for(int i = 0; i<numberOfMessages; i++){
            context.save(Instant.now(),  "LogMessage{loglevel= INFO, message= 'Hello, world! 9, timeServerReceivedLog= 2019-11-05T16:21:58.111280Z}" + i);
        }
        List <PersistedString> myList = new ArrayList <>();
        myList = context.get(10);
        assertEquals(numberOfMessages, myList.size());
    }


}
