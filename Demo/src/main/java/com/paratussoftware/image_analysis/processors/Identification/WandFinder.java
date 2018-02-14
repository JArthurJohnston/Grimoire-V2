package com.paratussoftware.image_analysis.processors.Identification;

import com.paratussoftware.Grimoire;
import com.paratussoftware.image_analysis.clusters.PointCluster;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class WandFinder {

    public static boolean isPossibleWandPoint(PointCluster pointCluster) {
        return clusterIsSmallEnough(pointCluster) &&
                clusterIsBigEnough(pointCluster) &&
                isSquarish(pointCluster);
    }

    private static boolean clusterIsSmallEnough(PointCluster pointCluster) {
        return pointCluster.width() < Grimoire.UserSettings.MAX_MOTION_SIZE_LIMIT &&
                pointCluster.height() < Grimoire.UserSettings.MAX_MOTION_SIZE_LIMIT;
    }

    private static boolean clusterIsBigEnough(PointCluster pointCluster) {
        return pointCluster.width() > Grimoire.UserSettings.MIN_MOTION_SIZE_LIMIT &&
                pointCluster.height() > Grimoire.UserSettings.MIN_MOTION_SIZE_LIMIT;
    }

    private static boolean isSquarish(PointCluster cluster){
        return Math.abs(cluster.width() - cluster.height()) < 7;
    }

    public static PointCluster nearestNearbyWandPointTo(PointCluster cluster, LinkedList<PointCluster> pointClusters) {
        if(pointClusters.isEmpty())
            return cluster;
        pointClusters.sort(Comparator.comparingDouble(cluster::distanceTo));
        return pointClusters.getFirst();
    }

    public static LinkedList<PointCluster> findPastWandPointsFor(PointCluster cluster, Iterator<LinkedList<PointCluster>> iterator) {
        LinkedList<PointCluster> pastClusters = new LinkedList<>();
        PointCluster currentCluster = cluster;
        while (iterator.hasNext()){
            LinkedList<PointCluster> previousFramePointClusters = iterator.next();
            PointCluster nearestCluster = nearestNearbyWandPointTo(currentCluster, previousFramePointClusters);
            if(currentCluster.distanceTo(nearestCluster) < Grimoire.UserSettings.MOTION_DETECTION_DISTANCE &&
                    currentCluster.distanceTo(nearestCluster) > 1 &&
                    isPossibleWandPoint(nearestCluster)){
                pastClusters.add(nearestCluster);
                currentCluster = nearestCluster;
            }
        }
        return pastClusters;
    }
}
