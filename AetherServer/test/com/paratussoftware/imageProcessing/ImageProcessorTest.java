package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import com.paratussoftware.buffers.RingBuffer;
import com.paratussoftware.imageProcessing.clusters.PointCluster;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    @Test
    public void testConstruction() throws Exception{
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 8);

        ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);

        assertSame(ringBuffer, imageProcessor.getRingBuffer());
        assertNotNull(imageProcessor.getClusterCreator());
    }

    @Test
    public void testBuildsBufferedClustersCorrectly() throws Exception{
        ImageProcessor imageProcessor = new ImageProcessor(null);
        RingBuffer<List<PointCluster>> bufferedClusters = imageProcessor.getBufferedClusters();

        assertEquals(32, bufferedClusters.getCapacity());
    }

    @Test
    public void testProcessFrame_buffersPointClusters() throws Exception{
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 8);
        ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);

        imageProcessor.processFrame(new byte[]{0,0,0,1,1,1,0,0,0});

        List<PointCluster> pointClusters = imageProcessor.getBufferedClusters().get(0);
        assertEquals(1, pointClusters.size());
        assertEquals(2, pointClusters.get(0).width());
    }


}