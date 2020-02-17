package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;

import java.time.Instant;
import java.util.List;

public class StringPersistorContext {

    private StringPersistorFileStrategy strategy;

    public StringPersistorFileStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(StringPersistorFileStrategy strategy) {
        this.strategy = strategy;
    }
    //use the strategy
    public void save(Instant instant, String string) {
        strategy.save(instant, string);
    }
    public List <PersistedString> get(int i) {
        return strategy.get(i);
    }
}
