package com.paratussoftware.apollo.musical;

import java.util.LinkedList;

public class Instrument {
    private final String name;
    private final LinkedList<Note> notes;

    public Instrument(final String name, final LinkedList<Note> notes) {
        this.name = name;
        this.notes = notes;
    }

    public String getName() {
        return this.name;
    }

    public LinkedList<Note> getNotes() {
        return this.notes;
    }
}
