package com.paratussoftware.gestures;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PointMathTest {

    @Test
    public void testIsDiagonal() throws Exception {
        assertFalse(PointMath.isDiagonal(0.1));
        assertFalse(PointMath.isDiagonal(0.4));

        assertTrue(PointMath.isDiagonal(0.5));
        assertTrue(PointMath.isDiagonal(0.9));
        assertTrue(PointMath.isDiagonal(1.5));

        assertFalse(PointMath.isDiagonal(1.6));
        assertFalse(PointMath.isDiagonal(2));
    }

    @Test
    public void testSlope() throws Exception {
        assertEquals(1, PointMath.slope(new Point(0, 0), new Point(1, 1)), 0.001);
        assertEquals(0.5, PointMath.slope(new Point(0, 0), new Point(2, 1)), 0.001);
        assertEquals(0.25, PointMath.slope(new Point(0, 0), new Point(4, 1)), 0.001);
        assertEquals(0.333, PointMath.slope(new Point(0, 0), new Point(3, 1)), 0.001);
    }

    @Test
    public void testDistanceBetweenPoints() throws Exception {
        assertEquals(10, PointMath.distanceBetween(new Point(0, 0), new Point(0, 10)), 0.001);
        assertEquals(15, PointMath.distanceBetween(new Point(0, 0), new Point(0, 15)), 0.001);
        assertEquals(14.142, PointMath.distanceBetween(new Point(0, 0), new Point(10, 10)), 0.001);
    }

}