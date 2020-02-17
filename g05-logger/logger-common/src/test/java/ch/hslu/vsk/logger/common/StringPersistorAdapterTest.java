package ch.hslu.vsk.logger.common;


import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorContext;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class StringPersistorAdapterTest {
    @Test
    public void testSaveWithStrategy(){
        File file = new File("target"){};
        StringPersistorContext context = new FakeContext();
        StringPersistorAdapter adapter = new StringPersistorAdapter(context);
        adapter.save(FakeLogMessage.getLogMessage());
        assertEquals(((FakeContext) context).getString(), FakeLogMessage.getLogMessage().toString() + "\n");



    }

}

class FakeContext extends StringPersistorContext {

    private Instant instant;


    private String string;
    @Override
    public void save(Instant instant, String string) {
        this.instant = instant;
        this.string = string;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public String getString() {
        return string;
    }

}
 class FakeLogMessage extends LogMessage {


    public FakeLogMessage(String message, LogLevel loglevel) {
         super(message, loglevel);
     }

     public static LogMessage getLogMessage(){
        return new LogMessage("HelloWorld", null, null, null);
     }
 }
