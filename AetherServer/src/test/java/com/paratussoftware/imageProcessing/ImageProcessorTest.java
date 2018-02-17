package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import com.paratussoftware.imageProcessing.motions.WandMotion;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    @Test
    public void testConstruction() {
        final ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 8);

        final ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);

        assertSame(ringBuffer, imageProcessor.getRingBuffer());
        assertNotNull(imageProcessor.getClusterCreator());
        assertNotNull(imageProcessor.getMotionTracker());
    }

    @Test
    public void testBuildsBufferedClustersCorrectly() {
        final ImageProcessor imageProcessor = new ImageProcessor(null);
        final RingBuffer<List<PointCluster>> bufferedClusters = imageProcessor.getBufferedClusters();

        assertEquals(32, bufferedClusters.getCapacity());
    }

    @Test
    public void testProcessFrame_buffersPointClusters() {
        assertEquals(848, Settings.IMAGE_WIDTH);
        assertEquals(480, Settings.IMAGE_HEIGHT);
        Settings.IMAGE_WIDTH = 10;
        Settings.IMAGE_HEIGHT = 10;
        final ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 8);
        final ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);

        imageProcessor.processFrame(arrayWithOneCluster());

        final List<PointCluster> pointClusters = imageProcessor.getBufferedClusters().get(0);
        assertEquals(1, pointClusters.size());
        assertEquals(5, pointClusters.get(0).width());
        assertEquals(5, pointClusters.get(0).height());

        final MotionTracker motionTracker = imageProcessor.getMotionTracker();
        final List<WandMotion> trackedMotions = motionTracker.getTrackedMotions();
        assertEquals(1, trackedMotions.size());
        final WandMotion wandMotion = trackedMotions.get(0);
        assertEquals(5, wandMotion.getCurrentCluster().width());
        assertEquals(5, wandMotion.getCurrentCluster().height());
    }

    private byte[] arrayWithOneCluster() {
        return new byte[]{
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
    }


}