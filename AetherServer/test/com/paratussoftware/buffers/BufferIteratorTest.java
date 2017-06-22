package com.paratussoftware.buffers;

import org.junit.Test;

import static org.junit.Assert.*;

public class BufferIteratorTest {

    @Test
    public void testLastInFirstOut() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(16);
        BufferIterator.lastInFirstOut(ringBuffer);
    }

}