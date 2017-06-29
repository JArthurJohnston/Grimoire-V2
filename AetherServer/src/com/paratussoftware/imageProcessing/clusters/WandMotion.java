package com.paratussoftware.imageProcessing.clusters;

import com.paratussoftware.buffers.RingBuffer;

public class WandMotion implements Comparable<WandMotion> {

    private PointCluster currentCluster;
    private RingBuffer<PointCluster> pastClusters;

    public WandMotion(PointCluster firstCluster){
        this.currentCluster = firstCluster;
        pastClusters = new RingBuffer<>(32);
    }

    public WandMotion(PointCluster currentCluster, RingBuffer<PointCluster> pastClusters){
        this.currentCluster = currentCluster;
        this.pastClusters = pastClusters;
    }

    public double length(){
        double length = 0;
        PointCluster currentPoint = currentCluster;
        for (int i = 0; i < this.pastClusters.size(); i++) {
            PointCluster eachPastPoint = this.pastClusters.get(i);
            length += currentPoint.distanceTo(eachPastPoint);
            currentPoint = eachPastPoint;
        }
        return length;
    }

    public RingBuffer<PointCluster> getPastClusters() {
        return pastClusters;
    }

    public PointCluster getCurrentCluster() {
        return currentCluster;
    }

    @Override
    public int compareTo(WandMotion other) {
        return Double.compare(this.length(), other.length());
    }

    public void addCluster(PointCluster newCluster) {
        this.pastClusters.write(this.currentCluster);
        this.currentCluster = newCluster;
    }
}
