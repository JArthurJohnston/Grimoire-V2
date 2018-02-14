package com.paratussoftware.gestures;

import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.clusters.WandMotion;
import org.junit.Test;

import static org.junit.Assert.*;

public class GestureDetectorTest {

    @Test
    public void testGestureForWandMotion() throws Exception {
        final PointCluster mostRecentCluster = PointCluster.newWith(10, 10);
        final RingBuffer<PointCluster> clusterBuffer = new RingBuffer<>(8);
        clusterBuffer.put(PointCluster.newWith(5, 5));
        clusterBuffer.put(PointCluster.newWith(0, 0));

        final WandMotion wandMotion = new WandMotion(mostRecentCluster, clusterBuffer);
        final GestureDetector gestureDetector = new GestureDetector();

        final Gesture actualGesture = gestureDetector.gestureFor(wandMotion);

        assertEquals(Gesture.DOWNWARDS_RIGHT, actualGesture);
    }


}