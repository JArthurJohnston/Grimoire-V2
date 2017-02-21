package grimoire.models.clusters;

import grimoire.models.UserSettings;
import java.awt.*;

public class PointCluster {

    public static PointCluster newWith(int x, int y){
        PointCluster newCluster = new PointCluster();
        newCluster.rightBoundary = x;
        newCluster.leftBoundary = x;
        newCluster.topBoundary = y;
        newCluster.bottomBoundary = y;
        newCluster.add(x, y);
        return newCluster;
    }

    int rightBoundary, leftBoundary, topBoundary, bottomBoundary;
    public boolean hasBeenFound;

    private PointCluster(){
        hasBeenFound = false;
    }

    public boolean contains(int x, int y){
        int containsDistance = UserSettings.CLUSTER_CONTAINS_DISTANCE;
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

    public Point centerPoint(){
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

    public int area(){
        return width() * height();
    }

    public int distanceTo(PointCluster anotherPoint){
        Point thisCenter = centerPoint();
        Point thatCenter = anotherPoint.centerPoint();
        return (int)Math.sqrt(
                Math.pow(thisCenter.getX() - thatCenter.getX(), 2) +
                        Math.pow(thisCenter.getY() - thatCenter.getY(), 2));
    }

    public boolean overlaps(PointCluster otherCluster){
        return distanceTo(otherCluster) >= width()/2;
    }

    public int xCoord(){
        return leftBoundary;
    }

    public int yCoord(){
        return topBoundary;
    }

    public String toString(){
        return "PointCluster: L: " + leftBoundary + "\tT: " + topBoundary + "\tR: " + rightBoundary + "\tB: " + bottomBoundary;
    }
}
