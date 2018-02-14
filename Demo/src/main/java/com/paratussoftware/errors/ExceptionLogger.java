package com.paratussoftware.errors;

import com.paratussoftware.Grimoire;

import java.io.*;
import java.util.Date;

public class ExceptionLogger {
    private static PrintWriter writer;

    private static String newline = "\n\r";

    public static void scribble(Exception e) {
        PrintWriter writer = getWriter();
        writer.write(new Date().toString());
        writer.write(newline);
        e.printStackTrace(writer);
        writer.write(newline);
        writer.flush();
    }

    public static PrintWriter getWriter() {
        if(writer == null){
            writer = initializeWriter();
        }
        return writer;
    }

    private static PrintWriter initializeWriter(){
        OutputStreamWriter writer;
        try {
            writer = new FileWriter(Grimoire.UserSettings.ERROR_LOG_LOCATION);
        } catch (IOException e) {
            writer = new OutputStreamWriter(System.out);

        }
        return new PrintWriter(writer);
    }
}
