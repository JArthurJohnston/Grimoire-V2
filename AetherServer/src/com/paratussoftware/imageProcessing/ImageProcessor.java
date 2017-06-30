package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.ClusterCreator;
import com.paratussoftware.imageProcessing.clusters.PointCluster;

import java.util.LinkedList;
import java.util.List;

public class ImageProcessor implements Runnable {

    private final ByteArrayRingBuffer ringBuffer;
    private boolean isRunning;
    private final ClusterCreator clusterCreator;
    private final RingBuffer<List<PointCluster>> bufferedClusters;
    private final MotionTracker motionTracker;

    public ImageProcessor(final ByteArrayRingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
        this.clusterCreator = new ClusterCreator();
        this.bufferedClusters = new RingBuffer<>(32);
        this.motionTracker = new MotionTracker();
    }

    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        this.isRunning = true;
        while (this.isRunning) {
            final byte[] framePixels = this.ringBuffer.read();
            processFrame(framePixels);
        }
    }

    void processFrame(final byte[] framePixels) {
        final LinkedList<PointCluster> pointClusters = this.clusterCreator.clusterPixels(framePixels);
        this.motionTracker.track(pointClusters);
        try {
            this.bufferedClusters.put(pointClusters);
        } catch (final InterruptedException e) {
        }
    }

    RingBuffer<List<PointCluster>> getBufferedClusters() {
        return this.bufferedClusters;
    }

    ClusterCreator getClusterCreator() {
        return this.clusterCreator;
    }

    ByteArrayRingBuffer getRingBuffer() {
        return this.ringBuffer;
    }

    MotionTracker getMotionTracker() {
        return this.motionTracker;
    }
}
