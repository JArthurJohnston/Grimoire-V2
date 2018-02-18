package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.PriorityBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.config.Settings;
import com.paratussoftware.gestures.Gesture;
import com.paratussoftware.imageProcessing.motions.WandMotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.paratussoftware.imageProcessing.identification.ImageTestData.*;
import static org.junit.Assert.*;

public class ImageProcessorTest {

    private ByteArrayRingBuffer ringBuffer;
    private RingBuffer<Gesture> gestureBuffer;
    private ImageProcessor imageProcessor;

    @Before
    public void setUp(){
        Settings.IMAGE_WIDTH = 10;

        gestureBuffer = new RingBuffer<>(4);
        ringBuffer = new ByteArrayRingBuffer(4, 8);
        imageProcessor = new ImageProcessor(ringBuffer, gestureBuffer);
    }

    @After
    public void tearDown(){
        Settings.IMAGE_WIDTH = 848;
    }

    @Test
    public void testConstruction() {
        assertSame(gestureBuffer, imageProcessor.getGestureOutputBuffer());
        assertTrue(imageProcessor.getPrioritizedMotions().isEmpty());
        assertEquals(8, imageProcessor.getPrioritizedMotions().getCapacity());
        assertSame(ringBuffer, imageProcessor.getRingBuffer());
        assertNotNull(imageProcessor.getClusterCreator());
        MotionTracker motionTracker = imageProcessor.getMotionTracker();
        assertNotNull(motionTracker);
        assertSame(motionTracker.getTrackedMotions(), imageProcessor.getPrioritizedMotions());
    }

    @Test
    public void testProcessFrame_buffersPointClusters() {
        imageProcessor.processFrame(arrayWithOneCluster());

        final MotionTracker motionTracker = imageProcessor.getMotionTracker();
        final PriorityBuffer<WandMotion> trackedMotions = motionTracker.getTrackedMotions();
        assertEquals(1, trackedMotions.size());
        final WandMotion wandMotion = trackedMotions.get(0);
        assertEquals(5, wandMotion.getCurrentCluster().width());
        assertEquals(5, wandMotion.getCurrentCluster().height());
    }

    @Test
    public void processFiveFrames_generatesWandMotion() {
        byte[][] motionFrameData = wandMotionFrameData();
        for (byte[] frameDatum : motionFrameData) {
            imageProcessor.processFrame(frameDatum);
        }

        final MotionTracker motionTracker = imageProcessor.getMotionTracker();
        final PriorityBuffer<WandMotion> trackedMotions = motionTracker.getTrackedMotions();
        assertEquals(1, trackedMotions.size());
        final WandMotion wandMotion = trackedMotions.get(0);
        assertEquals(5, wandMotion.getCurrentCluster().width());
        assertEquals(5, wandMotion.getCurrentCluster().height());
    }


}