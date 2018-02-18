package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.PriorityBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.gestures.Gesture;
import com.paratussoftware.gestures.GestureDetector;
import com.paratussoftware.imageProcessing.clusters.ClusterCreator;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.motions.WandMotion;

import java.util.LinkedList;
import java.util.List;

public class ImageProcessor implements Runnable {

    private final ByteArrayRingBuffer ringBuffer;
    private final ClusterCreator clusterCreator;
    private final MotionTracker motionTracker;
    private boolean isRunning;
    private final PriorityBuffer<WandMotion> prioritizedMotions;
    private final RingBuffer<Gesture> gestureOutputBuffer;

    public ImageProcessor(final ByteArrayRingBuffer ringBuffer, final RingBuffer<Gesture> gestureBuffer) {
        this.ringBuffer = ringBuffer;
        this.clusterCreator = new ClusterCreator();
        this.prioritizedMotions = buildDefaultPriorityBuffer();
        this.motionTracker = new MotionTracker(prioritizedMotions);
        this.gestureOutputBuffer = gestureBuffer;
    }

    private PriorityBuffer<WandMotion> buildDefaultPriorityBuffer() {
        return new PriorityBuffer<>(8, (o1, o2) -> Double.compare(o2.length(), o1.length()));
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

        Gesture gesture = GestureDetector.gestureFrom(prioritizedMotions.getFirst());
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

    public PriorityBuffer<WandMotion> getPrioritizedMotions() {
        return prioritizedMotions;
    }

    public RingBuffer<Gesture> getGestureOutputBuffer() {
        return gestureOutputBuffer;
    }
}
