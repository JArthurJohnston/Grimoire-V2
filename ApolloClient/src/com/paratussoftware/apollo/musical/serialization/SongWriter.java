package com.paratussoftware.apollo.musical.serialization;

import com.paratussoftware.apollo.musical.Melody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SongWriter {


    public void write(final Melody melody, final String fileName) {
        try {
            final FileWriter fileWriter = new FileWriter(new File(fileName));

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
