package ch.hslu.vsk.logger.common;


/**
 * An Interface for the Logger component to persist LogMessage instances.
 */
public interface LogPersistor {

    /**
     * This method saves the given LogMessage instance.
     * logMessage which has to be persisted
     */
    void save(LogMessage logMessage);
}
