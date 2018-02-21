package com.paratussoftware.gestures;

import java.util.Arrays;
import java.util.List;

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

    private final String label;
    private List similarGestures;

    Gesture(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isCloseTo(Gesture otherGesture) {
        if(similarGestures == null)
            initializeNearbyGestureMappings();
        return this.equals(otherGesture) | similarGestures.contains(otherGesture);
    }

    private void setNearbyGestures(Gesture... nearbyGestures){
        this.similarGestures =  Arrays.asList(nearbyGestures);
    }

    private void initializeNearbyGestureMappings() {
        UPWARDS.setNearbyGestures(UPWARDS_LEFT, UPWARDS_RIGHT);
        DOWNWARDS.setNearbyGestures(DOWNWARDS_LEFT, DOWNWARDS_RIGHT);
        LEFTWARDS.setNearbyGestures(UPWARDS_LEFT, DOWNWARDS_LEFT);
        RIGHTWARDS.setNearbyGestures(UPWARDS_RIGHT, DOWNWARDS_RIGHT);
        UPWARDS_RIGHT.setNearbyGestures(UPWARDS, RIGHTWARDS);
        UPWARDS_LEFT.setNearbyGestures(UPWARDS, LEFTWARDS);
        DOWNWARDS_RIGHT.setNearbyGestures(DOWNWARDS, RIGHTWARDS);
        DOWNWARDS_LEFT.setNearbyGestures(DOWNWARDS, LEFTWARDS);
        NONE.setNearbyGestures();
    }
}
