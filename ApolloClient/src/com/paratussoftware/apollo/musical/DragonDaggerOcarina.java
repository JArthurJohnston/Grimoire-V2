package com.paratussoftware.apollo.musical;

import java.util.LinkedList;

public class DragonDaggerOcarina extends Instrument {

    public DragonDaggerOcarina() {
        super("Dragon Dagger Ocarina", createNotes());
    }

    private static LinkedList<Note> createNotes() {
        final LinkedList<Note> notes = new LinkedList<>();
        notes.add(new Note("C", 527, 555));
        notes.add(new Note("D", 584, 596));
        notes.add(new Note("E", 647, 669));
//        notes.add(new Note("F#", 672, 786)); // overlaps with F, ignore for now
        notes.add(new Note("F", 691, 715));
        notes.add(new Note("G", 759, 777));
        notes.add(new Note("G#", 825, 845));
        notes.add(new Note("A", 855, 868));
        return notes;
    }
}
