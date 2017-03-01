package grimoire.models.processors.gestures;

import grimoire.models.UserSettings;
import grimoire.models.clusters.PointCluster;
import grimoire.models.processors.WandMotion;
import java.awt.Point;
import java.util.LinkedList;

import static grimoire.models.processors.PointMath.*;

public class GestureDetector {

    public static Gesture getMostRecentGesture(WandMotion motion){
        Point currentPoint = motion.getCurrentWandPoint().getCenterPoint();
        LinkedList<PointCluster> pastWandPoints = motion.getPastWandPoints();

        for (PointCluster pastWandPoint : pastWandPoints) {
            Point eachPoint = pastWandPoint.getCenterPoint();
            if(distanceBetween(currentPoint, eachPoint) >= UserSettings.GESTURE_DETECTION_DISTANCE){
                return calculateDirectionBetweenPoints(currentPoint, eachPoint);
            }
        }
        return Gesture.NONE;
    }

    private static Gesture calculateDirectionBetweenPoints(Point currentPoint, Point firstPoint) {
        final double slope = slope(currentPoint, firstPoint);
        if(isDiagonal(slope)){
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
