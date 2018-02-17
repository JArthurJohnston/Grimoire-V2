package com.paratussoftware.imageProcessing.motions;

import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.PointCluster;

public class WandMotion implements Comparable<WandMotion> {

    private final RingBuffer<PointCluster> pastClusters;
    private PointCluster currentCluster;

    public WandMotion(final PointCluster firstCluster) {
        this.currentCluster = firstCluster;
        this.pastClusters = new RingBuffer<>(32);
    }

    public WandMotion(final PointCluster currentCluster, final RingBuffer<PointCluster> pastClusters) {
        this.currentCluster = currentCluster;
        this.pastClusters = pastClusters;
    }

    public double length() {
        double length = 0;
        PointCluster currentPoint = this.currentCluster;
        for (int i = 0; i < this.pastClusters.size(); i++) {
            final PointCluster eachPastPoint = this.pastClusters.get(i);
            length += currentPoint.distanceTo(eachPastPoint);
            currentPoint = eachPastPoint;
        }
        return length;
    }

    public RingBuffer<PointCluster> getPastClusters() {
        return this.pastClusters;
    }

    public PointCluster getCurrentCluster() {
        return this.currentCluster;
    }

    @Override
    public int compareTo(final WandMotion other) {
        return Double.compare(this.length(), other.length());
    }

    public void addCluster(final PointCluster newCluster) {
        this.pastClusters.write(this.currentCluster);
        this.currentCluster = newCluster;
    }
}
