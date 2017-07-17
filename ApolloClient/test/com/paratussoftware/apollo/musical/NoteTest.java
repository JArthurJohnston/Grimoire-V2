package com.paratussoftware.apollo.musical;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoteTest {

    @Test
    public void testConstruction() throws Exception {
        final Note note = new Note("C", 750, 775);

        assertEquals("C", note.getName());
        assertEquals(750, note.getLowerRange());
        assertEquals(775, note.getUpperRange());
    }

}