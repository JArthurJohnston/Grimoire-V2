package com.paratussoftware.imageProcessing.identification;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;

public class WandIdentifier {
    public static boolean isPossibleWandPoint(PointCluster pointCluster) {
        return clusterIsBigEnough(pointCluster) &&
                clusterIsSmallEnough(pointCluster) &&
                isSquarish(pointCluster);
    }

    private static boolean clusterIsBigEnough(PointCluster pointCluster) {
        return pointCluster.width() > Settings.MIN_WAND_SIZE &&
                pointCluster.height() > Settings.MIN_WAND_SIZE;
    }

    private static boolean clusterIsSmallEnough(PointCluster pointCluster) {
        return pointCluster.width() < Settings.MAX_WAND_SIZE &&
                pointCluster.height() < Settings.MAX_WAND_SIZE;
    }

    private static boolean isSquarish(PointCluster cluster){
        return Math.abs(cluster.width() - cluster.height()) < 7;
    }

}
