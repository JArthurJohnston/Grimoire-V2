package grimoire.image_analysis.clusters;

import grimoire.Grimoire;
import grimoire.image_analysis.processors.PointMath;

import java.awt.*;

public class PointCluster {

    private Point centerPoint;

    public static PointCluster newWith(int x, int y){
        PointCluster newCluster = new PointCluster();
        newCluster.rightBoundary = x;
        newCluster.leftBoundary = x;
        newCluster.topBoundary = y;
        newCluster.bottomBoundary = y;
        newCluster.add(x, y);
        return newCluster;
    }

    private int rightBoundary, leftBoundary, topBoundary, bottomBoundary;

    private PointCluster(){}

    boolean contains(int x, int y){
        int containsDistance = Grimoire.UserSettings.CLUSTER_CONTAINS_DISTANCE;
        boolean withinHorizontalBounds = x <= rightBoundary + containsDistance
                && x >= leftBoundary - containsDistance;
        boolean withinVerticalBounds = y >= topBoundary - containsDistance
                && y <= bottomBoundary + containsDistance;
        return withinHorizontalBounds && withinVerticalBounds;
    }

    public void add(int x, int y){
        updateBoundaries(x, y);
    }

    private void updateBoundaries(int x, int y) {
        if(x > rightBoundary)
            rightBoundary = x;
        if(x < leftBoundary)
            leftBoundary = x;
        if(y < topBoundary)
            topBoundary = y;
        if(y > bottomBoundary)
            bottomBoundary = y;
    }

    public Point getCenterPoint(){
        if(centerPoint == null){
            centerPoint = initializeCenterPoint();
        }
        return centerPoint;
    }

    private Point initializeCenterPoint() {
        int xDiff = Math.abs(rightBoundary - leftBoundary);
        int yDiff = Math.abs(bottomBoundary - topBoundary);
        return new Point(rightBoundary - (xDiff/2), bottomBoundary - (yDiff/2));
    }

    public int width(){
        return rightBoundary - leftBoundary;
    }

    public int height(){
        return bottomBoundary - topBoundary;
    }

    public int xCoordinate(){
        return leftBoundary;
    }

    public int yCoordinate(){
        return topBoundary;
    }

    public String toString(){
        return "PointCluster: L: " + leftBoundary + "\tT: " + topBoundary + "\tR: " + rightBoundary + "\tB: " + bottomBoundary;
    }

    public double distanceTo(PointCluster otherCluster){
        return PointMath.distanceBetween(this.getCenterPoint(), otherCluster.getCenterPoint());
    }
}
