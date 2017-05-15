package grimoire.image_analysis.buffer;

import org.junit.Test;

import static org.junit.Assert.*;


public class RingBufferTest {

    @Test
    public void testConstruction() throws Exception{
        RingBuffer ringBuffer = new RingBuffer(7);

        assertEquals(7, ringBuffer.getCapacity());
        assertTrue(ringBuffer.isEmpty());
    }

    @Test
    public void testPushAndPop() throws Exception{
        RingBuffer<String> stringRingBuffer = new RingBuffer<>(8);

        String hello = "Hello";
        stringRingBuffer.write(hello);

        assertFalse(stringRingBuffer.isEmpty());

        String popedValue = stringRingBuffer.read();

        assertEquals(hello, popedValue);
        assertTrue(stringRingBuffer.isEmpty());
    }

    @Test
    public void testPushAndPop_WrapsWhenCapacityIsReached() throws Exception{
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