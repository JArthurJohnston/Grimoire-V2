package com.paratussoftware.gesture_analysis.gestures;

import com.paratussoftware.Grimoire;
import com.paratussoftware.image_analysis.clusters.PointCluster;
import com.paratussoftware.image_analysis.processors.PointMath;
import com.paratussoftware.image_analysis.processors.WandMotion;

import java.awt.Point;
import java.util.LinkedList;

public class GestureDetector {

    public static Gesture getMostRecentGesture(WandMotion motion){
        Point currentPoint = motion.getCurrentWandPoint().getCenterPoint();
        LinkedList<PointCluster> pastWandPoints = motion.getPastWandPoints();

        for (PointCluster pastWandPoint : pastWandPoints) {
            Point eachPoint = pastWandPoint.getCenterPoint();
            if(PointMath.distanceBetween(currentPoint, eachPoint) >= Grimoire.UserSettings.GESTURE_DETECTION_DISTANCE){
                return calculateDirectionBetweenPoints(currentPoint, eachPoint);
            }
        }
        return Gesture.NONE;
    }

    private static Gesture calculateDirectionBetweenPoints(Point currentPoint, Point firstPoint) {
        final double slope = PointMath.slope(currentPoint, firstPoint);
        if(PointMath.isDiagonal(slope)){
            boolean isRightwardsMovement = currentPoint.getX() > firstPoint.getX();
            if(slope > 0){
                return isRightwardsMovement? Gesture.DOWNWARDS_LEFT : Gesture.UPWARDS_RIGHT;
            }else {
                return isRightwardsMovement? Gesture.UPWARDS_LEFT : Gesture.DOWNWARDS_RIGHT;
            }
        }
        double differenceInX = Math.abs(currentPoint.getX() - firstPoint.getX());
        double differenceInY = Math.abs(currentPoint.getY() - firstPoint.getY());
        if(differenceInX > differenceInY){
            return currentPoint.getX() > firstPoint.getX() ? Gesture.LEFTWARDS : Gesture.RIGHTWARDS;
        }else {
            return currentPoint.getY() > firstPoint.getY() ? Gesture.DOWNWARDS : Gesture.UPWARDS;
        }
    }


}
