package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MotionTrackerTest {

    @Test
    public void testConstruction() throws Exception {
        final RingBuffer<List<WandMotion>> ringBuffer = new RingBuffer<>(16);

        final MotionTracker motionTracker = new MotionTracker(ringBuffer);

        assertSame(ringBuffer, motionTracker.getBufferedClusters());
        assertTrue(motionTracker.getTrackedMotions().isEmpty());
    }

    @Test
    public void testTracksClusters() throws Exception {
        final RingBuffer<List<WandMotion>> ringBuffer = new RingBuffer<>(4);
        final MotionTracker motionTracker = new MotionTracker(ringBuffer);

        final LinkedList<PointCluster> pointClusters = new LinkedList<>();
        final PointCluster possibleWandPoint = createPointCluster(0, 0, 5, 5);
        final PointCluster clusterThantsTooSmall = PointCluster.newWith(10, 10);
        pointClusters.add(possibleWandPoint);
        pointClusters.add(clusterThantsTooSmall);

        motionTracker.track(pointClusters);

        assertEquals(1, motionTracker.getTrackedMotions().size());
        final WandMotion trackedMotion = motionTracker.getTrackedMotions().get(0);
        assertSame(possibleWandPoint, trackedMotion.getCurrentCluster());
        assertTrue(trackedMotion.getPastClusters().isEmpty());
    }

    private PointCluster createPointCluster(final int startX, final int startY, final int endX, final int endY) {
        final PointCluster pointCluster = PointCluster.newWith(startX, startY);
        pointCluster.addPoint(endX, endY);
        return pointCluster;
    }
}