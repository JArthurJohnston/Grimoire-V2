package com.paratussoftware.apollo.musical;

public class Note {


    private final String name;
    private final int lowerRange;
    private final int upperRange;

    public Note(final String name, final int lowerRange, final int upperRange) {
        this.name = name;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    public String getName() {
        return this.name;
    }

    public int getLowerRange() {
        return this.lowerRange;
    }

    public int getUpperRange() {
        return this.upperRange;
    }
}
