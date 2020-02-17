/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import com.google.gson.Gson;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Majkel
 */
public class PersistedStringParser {

    /**
     * Set in the specification of the Interface StringPersistor. (in the output of the example)
     */
    private final static String STRINGSEPERATOR = " | ";

    public static PersistedString parseStringIntoPersistedString(String string) {

        if (!stringStartsWithIsoInstant(string)) {

            throw new IllegalArgumentException(

                    "Cannot parse '" + string + "' as PersistedString (must start with an iso date/time)");

        }

        if (!string.contains(STRINGSEPERATOR)) {

            throw new IllegalArgumentException("The string '" + string

                    + "' cannot be parsed, it must have been formatted using PersistedString.toString()");

        }

        final int separatorStartsAt = string.indexOf(STRINGSEPERATOR);

        final int separatorEndsAt = separatorStartsAt + STRINGSEPERATOR.length();

        final String datePart = string.substring(0, separatorStartsAt);

        final String messagePart = string.substring(separatorEndsAt);

        final Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(datePart));

        return new PersistedString(instant, messagePart);
    }

    public static String parsePersistedStringIntoString(PersistedString string){
       return string.toString();
    }

    public final static boolean stringStartsWithIsoInstant(final String lineInTextFile) {

        if (lineInTextFile == null || lineInTextFile.length() == 0) {
            return false;
        }

        final int firstSpaceAt = lineInTextFile.indexOf(" ");
        if (firstSpaceAt == -1) {
            return false;
        }

        final String datePart = lineInTextFile.substring(0, firstSpaceAt);
        try {
            return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(datePart)) != null;
        } catch (final DateTimeParseException dtpEx) {
            return false;
        }
    }


    public static String getSTRINGSEPERATOR() {
        return STRINGSEPERATOR;
    }

    public static PersistedString parseToJsonAndReturnAsPersistedString(String string){
        Gson g = new Gson();
        String str = g.toJson(string);
        PersistedString persistedString = new PersistedString(Instant.now(), str);
        return persistedString;
    }


}
