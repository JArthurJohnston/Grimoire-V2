package com.paratussoftware.grimoire.image_analysis.buffer;

import org.junit.Test;
import com.paratussoftware.image_analysis.buffer.RingBuffer;
import org.opencv.core.Mat;

import static org.junit.Assert.*;


public class RingBufferTest {

    @Test
    public void testConstruction(){
        RingBuffer<Mat> ringBuffer = new RingBuffer(7);

        assertEquals(7, ringBuffer.getCapacity());
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    public void testPushAndPop(){
        RingBuffer<String> stringRingBuffer = new RingBuffer<>(8);

        String hello = "Hello";
        stringRingBuffer.write(hello);

        assertFalse(stringRingBuffer.isEmpty());

        String poppedValue = stringRingBuffer.read();

        assertEquals(hello, poppedValue);
        assertTrue(stringRingBuffer.isEmpty());
    }

    @Test
    public void testPushAndPop_WrapsWhenCapacityIsReached(){
        RingBuffer<String> ringBuffer = new RingBuffer<>(4);
        ringBuffer.write("foo");
        ringBuffer.write("bar");
        ringBuffer.write("roo");
        ringBuffer.write("hello");
        ringBuffer.write("world");

        assertFalse(ringBuffer.isEmpty());

        assertEquals("world", ringBuffer.read());
        assertEquals("bar", ringBuffer.read());
        assertEquals("roo", ringBuffer.read());
        assertEquals("hello", ringBuffer.read());
    }



}