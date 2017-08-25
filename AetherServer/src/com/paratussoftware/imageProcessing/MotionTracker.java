package com.paratussoftware.imageProcessing;

import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;
import com.paratussoftware.imageProcessing.identification.WandIdentifier;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class MotionTracker {
    private final LinkedList<WandMotion> trackedMotions;

    /*
    Now that motions are getting tracked, they need to be prioritized and eventually removed
    for garbage collection.
    Currently the trackedMotions list will grow infinitely.
     */
    public MotionTracker() {
        this.trackedMotions = new LinkedList<>();
    }

    public void track(final List<PointCluster> pointClusters) {
        for (final PointCluster eachCluster : pointClusters) {
            if (WandIdentifier.isPossibleWandPoint(eachCluster)) {
                final WandMotion wandMotion = nearestWandMotionTo(eachCluster);
                if (wandMotion != null && wandMotion.getCurrentCluster().distanceTo(eachCluster) < Settings.MOTION_TRACKING_DISTANCE) {
                    wandMotion.addCluster(eachCluster);
                } else {
                    this.trackedMotions.add(new WandMotion(eachCluster));
                }
            }
        }
    }

    private WandMotion nearestWandMotionTo(final PointCluster cluster) {
        if (this.trackedMotions.isEmpty()) {
            return null;
        }
        this.trackedMotions.sort(Comparator.comparingDouble(wandMotion -> wandMotion.getCurrentCluster().distanceTo(cluster)));
        return this.trackedMotions.getFirst();
    }

    List<WandMotion> getTrackedMotions() {
        return this.trackedMotions;
    }
}
