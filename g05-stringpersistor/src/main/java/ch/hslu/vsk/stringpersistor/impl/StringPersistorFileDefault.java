/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Majkel
 */
public class StringPersistorFileDefault implements StringPersistorFileStrategy {

    public BufferedReader reader = null;
    public BufferedWriter writer = null;
    public final String lineSeparator = System.getProperty("line.separator");


    public StringPersistorFileDefault(){
    }
    public StringPersistorFileDefault(File file){
        this.setFile(file);
    }
    //Dependency Injection
    protected StringPersistorFileDefault(StringPersistor stringPersistor){}



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
        if (this.writer == null) {
            throw new IllegalArgumentException("You need to set a file");
        }
        PersistedString persistedString = new PersistedString(timestamp, payload);
        try {
            writer.write(persistedString.toString() + this.lineSeparator);
            writer.flush();
        } catch (IOException ex) {
            throw new IllegalStateException("Writing was not possible");
        }
    }

    @Override
    public List<PersistedString> get(int count) {
        int counter = 0;
        String line = null;
        StringBuilder messageBuffer = new StringBuilder();
        if (this.reader == null) {
            throw new IllegalStateException("No file set so far");
        }
        if (count < 1) {
            return new ArrayList<>();
        }
        final List<PersistedString> mylist = new ArrayList<>();
        try {
            while ((line = this.reader.readLine()) != null && counter < count) {
                //Indicates new Message
                System.out.println("reading");
                if (PersistedStringParser.stringStartsWithIsoInstant(line)) {
                    messageBuffer.append(line + this.lineSeparator);
                    System.out.println("parsing");
                    mylist.add(PersistedStringParser.parseStringIntoPersistedString(messageBuffer.toString().trim()));
                    counter++;
                    messageBuffer = new StringBuilder();
                }


            }

        } catch (IOException ex) {
            throw new IllegalStateException("Error reading " + count + " items from " + this.reader + ".",
                    ex);
        }
        return mylist;

    }
    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }
}
