package com.paratussoftware.buffers;

import org.junit.Test;

import static org.junit.Assert.*;

public class RingBufferTest {

    @Test
    public void testConstruction() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(16);

        Object[] ringBufferValues = ringBuffer.getValues();
        assertEquals(16, ringBufferValues.length);
        assertEquals(16, ringBuffer.getCapacity());
    }

    @Test
    public void testMustBeBuildWithCapacityThatIsAPowerOfTwo() throws Exception{
        RingBuffer<Object> ringBuffer = new RingBuffer<>(32);
        assertEquals(32, ringBuffer.getValues().length);

        try{
            new RingBuffer<> (12);
            fail("should have thrown exception");
        } catch (RuntimeException e){
            assertEquals("Capacity must be a power of two", e.getMessage());
        }
    }

    @Test
    public void testReadAndWrite() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(16);

        String expectedString = "sdflkjsdflkj";

        ringBuffer.write(expectedString);

        assertEquals(expectedString, ringBuffer.read());

        assertNull(ringBuffer.read());
    }

    @Test
    public void testisEmpty() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(4);

        assertTrue(ringBuffer.isEmpty());

        String expectedString = "sdflkjsdflkj";
        ringBuffer.write(expectedString);

        assertFalse(ringBuffer.isEmpty());

        ringBuffer.read();

        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    public void testReadWhenFull() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(4);

        String expectedString = "sdflkjsdflkj";
        String expectedString2 = "khgjhg";
        String expectedString3 = "adsoiyqwe";
        String expectedString4 = "jkfkjdkjdf";

        ringBuffer.write(expectedString);
        ringBuffer.write(expectedString2);
        ringBuffer.write(expectedString3);
        ringBuffer.write(expectedString4);

        assertEquals(expectedString, ringBuffer.read());
        assertEquals(expectedString2, ringBuffer.read());
        assertEquals(expectedString3, ringBuffer.read());
        assertEquals(expectedString4, ringBuffer.read());

        assertEquals(expectedString, ringBuffer.read());
    }

    @Test
    public void testWritePastFull() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(2);

        String expectedString = "sdflkjsdflkj";
        String expectedString2 = "khgjhg";
        String expectedString3 = "adsoiyqwe";

        ringBuffer.write(expectedString);
        ringBuffer.write(expectedString2);
        ringBuffer.write(expectedString3);

        assertEquals(expectedString3, ringBuffer.read());
        assertEquals(expectedString2, ringBuffer.read());
    }

    @Test
    public void testGetAtIndex() throws Exception{
        RingBuffer<String> ringBuffer = new RingBuffer<>(4);

        String expectedString = "sdflkjsdflkj";
        String expectedString2 = "khgjhg";
        String expectedString3 = "adsoiyqwe";
        String expectedString4 = "jkfkjdkjdf";

        ringBuffer.write(expectedString);
        ringBuffer.write(expectedString2);
        ringBuffer.write(expectedString3);
        ringBuffer.write(expectedString4);

        assertEquals(expectedString3, ringBuffer.get(2));
        assertEquals(expectedString, ringBuffer.get(0));
        assertEquals(expectedString4, ringBuffer.get(3));
        assertEquals(expectedString2, ringBuffer.get(1));
    }

}
