package com.paratussoftware.gestures;

import java.awt.*;

public class PointMath {

    public static double slope(final Point one, final Point two) {
        return (two.getY() - one.getY()) / (two.getX() - one.getX());
    }

    public static boolean isDiagonal(final double slope) {
        final double absSlope = Math.abs(slope);
        return absSlope >= 0.5 && absSlope <= 1.5;
    }

    public static double distanceBetween(final Point a, final Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}
