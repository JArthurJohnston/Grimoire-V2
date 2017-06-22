package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.config.Settings;

import java.awt.*;

public class PointCluster {
    private int topBoundary, bottomBoundary, rightBoundary, leftBoundary;

    public static PointCluster newWith(int x, int y) {
        return new PointCluster(y, y, x, x);
    }

    private PointCluster(int top, int bottom, int right, int left) {
        this.topBoundary = top;
        this.bottomBoundary = bottom;
        this.rightBoundary = right;
        this.leftBoundary = left;
    }

    public Point centerPoint(){
        int xDiff = Math.abs(rightBoundary - leftBoundary);
        int yDiff = Math.abs(bottomBoundary - topBoundary);
        return new Point(rightBoundary - (xDiff/2), bottomBoundary - (yDiff/2));
    }

    public boolean contains(int xCoord, int yCoord){
        return this.distanceTo(xCoord, yCoord) <= Settings.CLUSTER_CONTAINTS_DISTANCE;
    }

    public void addPoint(int x, int y){
        updateBoundaries(x, y);
    }

    public int right(){
        return rightBoundary;
    }

    public int left(){
        return leftBoundary;
    }

    public int top(){
        return topBoundary;
    }

    public int bottom(){
        return bottomBoundary;
    }

    public double distanceTo(PointCluster otherCluster){
        Point thatCenter = otherCluster.centerPoint();
        return distanceTo(thatCenter.getX(), thatCenter.getY());
    }

    public double distanceTo(double xCoord, double yCoord){
        Point thisCenter = this.centerPoint();
        return Math.sqrt(Math.pow(thisCenter.getX() - xCoord, 2) +
                Math.pow(thisCenter.getY() - yCoord, 2));
    }

    public int width(){
        return rightBoundary - leftBoundary;
    }

    public int height(){
        return bottomBoundary - topBoundary;
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
}
