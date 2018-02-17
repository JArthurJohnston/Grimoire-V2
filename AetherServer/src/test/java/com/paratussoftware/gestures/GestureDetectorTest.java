package com.paratussoftware.gestures;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.WandMotion;
import org.junit.Before;
import org.junit.Test;

import static com.paratussoftware.gestures.WandMotionTestBuilder.*;
import static org.junit.Assert.assertEquals;

public class GestureDetectorTest {

    private GestureDetector gestureDetector;
    private WandMotion downwardsLeftWandMotion;

    @Before
    public void setup() throws Exception{
        Settings.GESTURE_DETECTION_DISTANCE = 10;
        downwardsLeftWandMotion = createDownwardsLeftWandMotion();
        gestureDetector = new GestureDetector();
    }

    @Test
    public void testGestureFromWandMotion_downwardsLeft() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createDownwardsLeftWandMotion());

        assertEquals(Gesture.DOWNWARDS_LEFT, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_downwardsRight() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createDownwardsRightWandMotion());

        assertEquals(Gesture.DOWNWARDS_RIGHT, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_upwardsLeft() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createUpwardsLeftWandMotion());

        assertEquals(Gesture.UPWARDS_LEFT, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_upwardsRight() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createUpwardsRightWandMotion());

        assertEquals(Gesture.UPWARDS_RIGHT, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_rightwards() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createRightwardsWandMotion());

        assertEquals(Gesture.RIGHTWARDS, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_leftwards() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createLeftwardsWandMotion());

        assertEquals(Gesture.LEFTWARDS, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_upwards() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createUpwardsWandMotion());

        assertEquals(Gesture.UPWARDS, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_downwards() throws Exception{
        final Gesture actualGesture = gestureDetector.gestureFrom(createDownwardsWandMotion());

        assertEquals(Gesture.DOWNWARDS, actualGesture);
    }

    @Test
    public void testGestureFromWandMotion_returnsNoneIfDistanceIsNotLongEnough() {
        Settings.GESTURE_DETECTION_DISTANCE = 50;

        final Gesture actualGesture = gestureDetector.gestureFrom(downwardsLeftWandMotion);

        assertEquals(Gesture.NONE, actualGesture);
    }

}
