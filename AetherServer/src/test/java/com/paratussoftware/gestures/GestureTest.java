package com.paratussoftware.gestures;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GestureTest {

    private static final Gesture[] ALL_GESTURES = Gesture.class.getEnumConstants();

    @Test
    public void closeToAnotherGesture() {
        checkGestureIsCloseTo(Gesture.DOWNWARDS, Gesture.DOWNWARDS_LEFT, Gesture.DOWNWARDS_RIGHT);
        checkGestureIsCloseTo(Gesture.DOWNWARDS_LEFT, Gesture.DOWNWARDS, Gesture.LEFTWARDS);
        checkGestureIsCloseTo(Gesture.LEFTWARDS, Gesture.DOWNWARDS_LEFT, Gesture.UPWARDS_LEFT);
        checkGestureIsCloseTo(Gesture.UPWARDS_LEFT, Gesture.LEFTWARDS, Gesture.UPWARDS);
        checkGestureIsCloseTo(Gesture.UPWARDS, Gesture.UPWARDS_LEFT, Gesture.UPWARDS_RIGHT);
        checkGestureIsCloseTo(Gesture.UPWARDS_RIGHT, Gesture.UPWARDS, Gesture.RIGHTWARDS);
        checkGestureIsCloseTo(Gesture.RIGHTWARDS, Gesture.UPWARDS_RIGHT, Gesture.DOWNWARDS_RIGHT);
        checkGestureIsCloseTo(Gesture.DOWNWARDS_RIGHT, Gesture.RIGHTWARDS, Gesture.DOWNWARDS);
    }

    private void checkGestureIsCloseTo(Gesture beingTested, Gesture nearby1, Gesture nearby2){
        assertTrue(beingTested.isCloseTo(beingTested));

        assertTrue(beingTested.isCloseTo(nearby1));
        assertTrue(beingTested.isCloseTo(nearby2));

        assertTrue(nearby1.isCloseTo(beingTested));
        assertTrue(nearby2.isCloseTo(beingTested));

        LinkedList<Gesture> gestures = allButNearbyGestures(beingTested, nearby1, nearby2);

        for (Gesture gesture : gestures) {
            assertFalse(beingTested.getLabel() + " should NOT be close to " + gesture.getLabel(), beingTested.isCloseTo(gesture));
        }

        assertFalse(Gesture.NONE.isCloseTo(beingTested));
        assertFalse(beingTested.isCloseTo(Gesture.NONE));
    }

    private LinkedList<Gesture> allButNearbyGestures(Gesture beingTested, Gesture nearby1, Gesture nearby2) {
        List<Gesture> gestureList = Arrays.asList(ALL_GESTURES);
        LinkedList<Gesture> gestures = new LinkedList<>();
        gestures.addAll(gestureList);
        gestures.remove(beingTested);
        gestures.remove(nearby1);
        gestures.remove(nearby2);
        return gestures;
    }

}