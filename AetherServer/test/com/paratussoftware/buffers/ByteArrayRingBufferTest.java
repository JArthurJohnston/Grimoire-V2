package com.paratussoftware.buffers;

import org.junit.Test;

import static org.junit.Assert.*;

public class ByteArrayRingBufferTest {

    @Test
    public void testMustBeBuildWithCapacityThatIsAPowerOfTwo() throws Exception{
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(32, 10);
        assertEquals(32, ringBuffer.getValues().length);
        assertEquals(10, ringBuffer.getValues()[0].length);

        try{
            new ByteArrayRingBuffer(12, 4);
            fail("should have thrown exception");
        } catch (RuntimeException e){
            assertEquals("Capacity must be a power of two", e.getMessage());
        }
    }

    @Test
    public void testConstruction() throws Exception{
        int expectedCapacity = 32;
        int expectedElementLength = 104;
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(expectedCapacity, expectedElementLength);

        byte[][] values = ringBuffer.getValues();

        assertEquals(32, values.length);
        assertEquals(104, values[0].length);
    }

    @Test
    public void testPushAndRead() throws Exception{
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 3);

        byte[] expected = new byte[] {1, 2, 3};
        System.arraycopy(expected, 0, ringBuffer.push(), 0, 3);

        assertArrayEquals(expected, ringBuffer.read());
    }

    @Test
    public void testPushAndReadAtCapacity() throws Exception{
        ByteArrayRingBuffer ringBuffer = new ByteArrayRingBuffer(4, 3);

        byte[] expected1 = new byte[] {1, 2, 3};
        byte[] expected2 = new byte[] {4, 5, 6};
        byte[] expected3 = new byte[] {7, 8, 9};
        byte[] expected4 = new byte[] {10, 11, 12};

        System.arraycopy(expected1, 0, ringBuffer.push(), 0, 3);
        System.arraycopy(expected2, 0, ringBuffer.push(), 0, 3);
        System.arraycopy(expected3, 0, ringBuffer.push(), 0, 3);
        System.arraycopy(expected4, 0, ringBuffer.push(), 0, 3);

        byte[] expected5 = new byte[] {110, 111, 112};
        System.arraycopy(expected5, 0, ringBuffer.push(), 0, 3);

        assertArrayEquals(expected5, ringBuffer.read());
        assertArrayEquals(expected2, ringBuffer.read());
        assertArrayEquals(expected3, ringBuffer.read());
        assertArrayEquals(expected4, ringBuffer.read());
    }


}