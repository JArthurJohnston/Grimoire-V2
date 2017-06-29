package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;
import com.paratussoftware.imageProcessing.identification.WandIdentifier;

import java.util.LinkedList;
import java.util.List;

public class MotionTracker {
    private final RingBuffer<List<WandMotion>> bufferedClusters;
    private final LinkedList<WandMotion> trackedMotions;

    public MotionTracker(final RingBuffer<List<WandMotion>> ringBuffer) {
        this.trackedMotions = new LinkedList<>();
        this.bufferedClusters = ringBuffer;
    }

    public RingBuffer<List<WandMotion>> getBufferedClusters() {
        return this.bufferedClusters;
    }

    public void track(final List<PointCluster> pointClusters) {
        for (final PointCluster eachCluster : pointClusters) {
            if (WandIdentifier.isPossibleWandPoint(eachCluster))
                this.trackedMotions.add(new WandMotion(eachCluster));
        }
    }

    public List<WandMotion> getTrackedMotions() {
        return this.trackedMotions;
    }
}
