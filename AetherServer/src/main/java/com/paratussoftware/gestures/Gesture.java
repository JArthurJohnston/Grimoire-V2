package com.paratussoftware.gestures;

public enum Gesture {

    NONE("NONE"),
    UPWARDS("Up"),
    DOWNWARDS("Down"),
    LEFTWARDS("Left"),
    RIGHTWARDS("Right"),
    UPWARDS_RIGHT("Up Right"),
    UPWARDS_LEFT("Up Left"),
    DOWNWARDS_RIGHT("Down Right"),
    DOWNWARDS_LEFT("Down Left");

    public final String label;

    Gesture(final String label) {
        this.label = label;
    }
}
