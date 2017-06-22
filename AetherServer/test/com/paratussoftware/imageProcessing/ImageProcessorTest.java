package com.paratussoftware.imageProcessing;

import com.paratussoftware.buffers.ByteArrayRingBuffer;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    @Test
    public void testConstruction() throws Exception{
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 8);

        ImageProcessor imageProcessor = new ImageProcessor(ringBuffer);

        assertSame(ringBuffer, imageProcessor.getRingBuffer());
        assertNotNull(imageProcessor.getClusterCreator());
    }
}