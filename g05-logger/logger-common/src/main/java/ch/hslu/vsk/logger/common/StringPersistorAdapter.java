package ch.hslu.vsk.logger.common;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorContext;


import java.io.File;


public final class StringPersistorAdapter implements LogPersistor {
    //concrete implementation to persists strings into files
    private StringPersistorContext context;

    public StringPersistorAdapter(StringPersistorContext context) {
        this.context = context;
    }


    @Override
    public void save(final LogMessage logMessage) {
        System.out.println(logMessage.toString());
        this.context.save(logMessage.getTimeStamp(), logMessage.toString() + "\n");
    }

}
