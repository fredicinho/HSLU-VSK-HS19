package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StringPersistorMemory implements StringPersistorFileStrategy {

    private List<PersistedString> arrayListString = new ArrayList <>();
    @Override
    public void setFile(File file) {
        //no file to set here because it will be safed in memory
    }

    @Override
    public void save(Instant instant, String s) {
        arrayListString.add(new PersistedString(instant, s));
    }

    @Override
    public List <PersistedString> get(int i) {
        return arrayListString;
    }

}
