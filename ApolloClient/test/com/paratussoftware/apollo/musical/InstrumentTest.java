package com.paratussoftware.apollo.musical;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class InstrumentTest {

    @Test
    public void testConstruction() throws Exception {
        final LinkedList<Note> notes = new LinkedList<>();

        final Instrument instrument = new Instrument("Ocarina", notes);

        assertEquals("Ocarina", instrument.getName());
        assertSame(notes, instrument.getNotes());
    }


}