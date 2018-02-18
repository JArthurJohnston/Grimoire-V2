package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.PriorityBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.motions.WandMotion;
import com.paratussoftware.imageProcessing.identification.WandIdentifier;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class MotionTracker {
    private final PriorityBuffer<WandMotion> trackedMotions;
    //^^^ replace with PriorityBuffer

    /*
    Now that motions are getting tracked, they need to be prioritized and eventually removed
    for garbage collection.
    Currently the trackedMotions list will grow infinitely.
     */
    public MotionTracker() {
        this.trackedMotions = buildDefaultPriorityBuffer();
    }

    private PriorityBuffer<WandMotion> buildDefaultPriorityBuffer() {
        return new PriorityBuffer<>(5, (o1, o2) -> Double.compare(o2.length(), o1.length()));
    }

    public void track(final List<PointCluster> pointClusters) {
        for (final PointCluster eachCluster : pointClusters) {
            if (WandIdentifier.isPossibleWandPoint(eachCluster)) {
                final WandMotion wandMotion = nearestWandMotionTo(eachCluster);
                if (withinRangeOfExistingWandMotion(eachCluster, wandMotion)) {
                    wandMotion.addCluster(eachCluster);
                } else {
                    this.trackedMotions.push(new WandMotion(eachCluster));
                }
            }
        }
    }

    private boolean withinRangeOfExistingWandMotion(PointCluster eachCluster, WandMotion wandMotion) {
        return wandMotion != null &&
                wandMotion.getCurrentCluster().distanceTo(eachCluster) < Settings.MOTION_TRACKING_DISTANCE;
    }

    private WandMotion nearestWandMotionTo(final PointCluster cluster) {
        if (this.trackedMotions.isEmpty()) {
            return null;
        }
        LinkedList<WandMotion> sortedMotions = this.trackedMotions.sort(Comparator.comparingDouble(wandMotion -> wandMotion.getCurrentCluster().distanceTo(cluster)));
        return sortedMotions.getFirst();
    }

    List<WandMotion> getTrackedMotions() {
        return this.trackedMotions.asList();
    }
}
