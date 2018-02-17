package com.paratussoftware.gestures;

import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;

import java.awt.*;

public class GestureDetector {

    private static Gesture calculateDirectionFromPoints(final Point currentPoint, final Point firstPoint) {
        final double slope = PointMath.slope(currentPoint, firstPoint);
        if (PointMath.isDiagonal(slope)) {
            return calculateDiagonalMovement(currentPoint, firstPoint, slope);
        }
        return calculateHorizontalAndVerticalMovement(currentPoint, firstPoint);
    }

    private static Gesture calculateHorizontalAndVerticalMovement(Point currentPoint, Point firstPoint) {
        final double differenceInX = Math.abs(currentPoint.getX() - firstPoint.getX());
        final double differenceInY = Math.abs(currentPoint.getY() - firstPoint.getY());
        if (differenceInX > differenceInY) {
            return currentPoint.getX() > firstPoint.getX() ? Gesture.LEFTWARDS : Gesture.RIGHTWARDS;
        } else {
            return currentPoint.getY() > firstPoint.getY() ? Gesture.DOWNWARDS : Gesture.UPWARDS;
        }
    }

    private static Gesture calculateDiagonalMovement(Point currentPoint, Point firstPoint, double slope) {
        final boolean isRightwardsMovement = currentPoint.getX() > firstPoint.getX();
        if (slope > 0) {
            return isRightwardsMovement ? Gesture.DOWNWARDS_LEFT : Gesture.UPWARDS_RIGHT;
        } else {
            return isRightwardsMovement ? Gesture.UPWARDS_LEFT : Gesture.DOWNWARDS_RIGHT;
        }
    }

    public Gesture gestureFrom(final WandMotion wandMotion) {
        final Point currentPoint = wandMotion.getCurrentCluster().centerPoint();
        final RingBuffer<PointCluster> pastWandPoints = wandMotion.getPastClusters();

        for (int i = 0; i < pastWandPoints.size(); i++) {
            final Point eachPoint = pastWandPoints.get(i).centerPoint();
            if (PointMath.distanceBetween(currentPoint, eachPoint) >= Settings.GESTURE_DETECTION_DISTANCE) {
                return calculateDirectionFromPoints(currentPoint, eachPoint);
            }
        }
        return Gesture.NONE;
    }

}
