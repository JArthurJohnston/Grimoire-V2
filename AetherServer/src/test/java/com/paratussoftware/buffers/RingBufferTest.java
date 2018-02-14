package com.paratussoftware.buffers;

import org.junit.Test;

import static org.junit.Assert.*;

public class RingBufferTest {

    @Test
    public void testConstruction() {
        final RingBuffer<String> ringBuffer = new RingBuffer<>(16);

        final Object[] ringBufferValues = ringBuffer.getValues();
        assertEquals(16, ringBufferValues.length);
        assertEquals(16, ringBuffer.getCapacity());
    }

    @Test
    public void testMustBeBuildWithCapacityThatIsAPowerOfTwo() {
        final RingBuffer<Object> ringBuffer = new RingBuffer<>(32);
        assertEquals(32, ringBuffer.getValues().length);

        try {
            new RingBuffer<>(12);
            fail("should have thrown exception");
        } catch (final RuntimeException e) {
            assertEquals("Capacity must be a power of two", e.getMessage());
        }
    }

    @Test
    public void testReadAndWrite() {
        final RingBuffer<String> ringBuffer = new RingBuffer<>(16);

        final String expectedString = "sdflkjsdflkj";

        ringBuffer.write(expectedString);

        assertEquals(expectedString, ringBuffer.read());

        assertNull(ringBuffer.read());
    }

    @Test
    public void testisEmpty() {
        final RingBuffer<String> ringBuffer = new RingBuffer<>(4);

        assertTrue(ringBuffer.isEmpty());

        final String expectedString = "sdflkjsdflkj";
        ringBuffer.write(expectedString);

        assertFalse(ringBuffer.isEmpty());

        ringBuffer.read();

        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    public void testReadWhenFull() {
        final RingBuffer<String> ringBuffer = new RingBuffer<>(4);

        final String expectedString = "sdflkjsdflkj";
        final String expectedString2 = "khgjhg";
        final String expectedString3 = "adsoiyqwe";
        final String expectedString4 = "jkfkjdkjdf";

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
    public void testWritePastFull() {
        final RingBuffer<String> ringBuffer = new RingBuffer<>(2);

        final String expectedString = "sdflkjsdflkj";
        final String expectedString2 = "khgjhg";
        final String expectedString3 = "adsoiyqwe";

        ringBuffer.write(expectedString);
        ringBuffer.write(expectedString2);
        ringBuffer.write(expectedString3);

        assertEquals(expectedString3, ringBuffer.read());
        assertEquals(expectedString2, ringBuffer.read());
    }

    @Test
    public void testGetAtIndex() {
        final RingBuffer<String> ringBuffer = new RingBuffer<>(4);

        final String expectedString = "sdflkjsdflkj";
        final String expectedString2 = "khgjhg";
        final String expectedString3 = "adsoiyqwe";
        final String expectedString4 = "jkfkjdkjdf";

        ringBuffer.write(expectedString);
        ringBuffer.write(expectedString2);
        ringBuffer.write(expectedString3);
        ringBuffer.write(expectedString4);

        assertEquals(expectedString3, ringBuffer.get(2));
        assertEquals(expectedString, ringBuffer.get(0));
        assertEquals(expectedString4, ringBuffer.get(3));
        assertEquals(expectedString2, ringBuffer.get(1));
    }

    @Test
    public void testSize() {
        final int capacity = 4;
        final RingBuffer<String> ringBuffer = new RingBuffer<>(capacity);

        assertEquals(0, ringBuffer.size());

        ringBuffer.write("foo");

        assertEquals(1, ringBuffer.size());

        ringBuffer.write("bar");
        ringBuffer.write("baz");
        ringBuffer.write("quz");
        ringBuffer.write("hello");

        assertEquals(capacity, ringBuffer.size());
    }
}
