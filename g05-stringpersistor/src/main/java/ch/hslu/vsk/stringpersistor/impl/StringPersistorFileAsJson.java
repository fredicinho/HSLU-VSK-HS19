package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StringPersistorFileAsJson implements StringPersistorFileStrategy {

    public BufferedReader reader = null;
    public BufferedWriter writer = null;
    public final String lineSeparator = System.getProperty("line.separator");

    public StringPersistorFileAsJson(){
    }

    public StringPersistorFileAsJson(File file){
        this.setFile(file);
    }
    private class JsonObject{

        Instant instantTimeReceived;
        Instant instantTimeLogged;
        String logMessage;

        public JsonObject(Instant instantTimeReceived, String logMessage) {
            this.instantTimeReceived = instantTimeReceived;
            this.logMessage = logMessage;
        }
    }

    @Override
    public void setFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File could not be null");
        }
        if (!file.exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new IllegalArgumentException("Parent directories not exist for this file");
            }
            try {
                if (!file.createNewFile()) {
                    throw new IllegalArgumentException("The File could not be created");
                }
            } catch (IOException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        if (!file.canRead()) {
            throw new IllegalStateException("The file is not readable");
        }
        if (!file.canWrite()) {
            throw new IllegalStateException("The file is not writable");
        }
        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            this.reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (final FileNotFoundException f) {
            throw new IllegalArgumentException("The file " + file + " does not exist.", f);
        }
    }

    @Override
    public void save(Instant timestamp, String payload) {
        JsonObject obj = new JsonObject(timestamp, payload);
        Gson gson = new Gson();
        String str = gson.toJson(obj);

        try {
            writer.write(str);
            writer.flush();
        } catch (IOException ex) {
            throw new IllegalStateException("Writing was not possible");
        }
    }

    @Override
    public List <PersistedString> get(int count) {
        int counter = 0;
        String line = null;
        ArrayList<PersistedString> messageBuffer = new ArrayList <>();
        if (this.reader == null) {
            throw new IllegalStateException("No file set so far");
        }
        if (count < 1) {
            return new ArrayList <>();
        }
        final List<PersistedString> mylist = new ArrayList<>();
        try {
            while ((line = this.reader.readLine()) != null && counter < count) {
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(line, JsonObject.class);
                line = line.substring(Instant.now().toString().length() -3);
                PersistedString persistedString = new PersistedString(obj.instantTimeReceived, obj.logMessage);
                messageBuffer.add(persistedString);
                counter++;
            }

        } catch (IOException ex) {
            throw new IllegalStateException("Error reading " + count + " items from " + this.reader + ".",
                    ex);
        }
        return messageBuffer;
    }
}
