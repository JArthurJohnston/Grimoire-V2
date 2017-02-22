package grimoire.models.processors.Identification;

import grimoire.models.UserSettings;
import grimoire.models.clusters.PointCluster;
import grimoire.models.processors.BufferIterator;

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
        return pointCluster.width() < UserSettings.MAX_MOTION_SIZE_LIMIT &&
                pointCluster.height() < UserSettings.MAX_MOTION_SIZE_LIMIT;
    }

    private static boolean clusterIsBigEnough(PointCluster pointCluster) {
        return pointCluster.width() > UserSettings.MIN_MOTION_SIZE_LIMIT &&
                pointCluster.height() > UserSettings.MIN_MOTION_SIZE_LIMIT;
    }

    private static boolean isSquarish(PointCluster cluster){
        return Math.abs(cluster.width() - cluster.height()) < 7;
    }

    static PointCluster nearestNearbyWandPointTo(PointCluster cluster, LinkedList<PointCluster> pointClusters) {
        if(pointClusters.isEmpty())
            return cluster;
        pointClusters.sort(Comparator.comparingInt(cluster::distanceTo));
        return pointClusters.getFirst();
    }

    public static LinkedList<PointCluster> findPastWandPointsFor(PointCluster cluster, Iterator<LinkedList<PointCluster>> iterator) {
        LinkedList<PointCluster> pastClusters = new LinkedList<>();
        PointCluster currentCluster = cluster;
        while (iterator.hasNext()){
            LinkedList<PointCluster> previousFramePointClusters = iterator.next();
            PointCluster nearestCluster = nearestNearbyWandPointTo(currentCluster, previousFramePointClusters);
            if(currentCluster.distanceTo(nearestCluster) < UserSettings.MOTION_DETECTION_DISTANCE &&
                    currentCluster.distanceTo(nearestCluster) > 1 &&
                    isPossibleWandPoint(nearestCluster)){
                pastClusters.addLast(nearestCluster);
                currentCluster = nearestCluster;
            }
        }
        return pastClusters;
    }
}
