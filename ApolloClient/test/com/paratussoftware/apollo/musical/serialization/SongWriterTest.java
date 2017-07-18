package com.paratussoftware.apollo.musical.serialization;

import com.paratussoftware.apollo.musical.Melody;
import org.junit.Test;

import static com.paratussoftware.apollo.musical.Melody.Tab.*;

public class SongWriterTest {

    @Test
    public void testReadAndWriteMelody() throws Exception {
        final SongWriter songWriter = new SongWriter();
        final Melody powerRangersJingle = new Melody("", E, E, D, E, G, E);

        songWriter.write(powerRangersJingle, "./test/testRes/ranger.melody");
    }


}