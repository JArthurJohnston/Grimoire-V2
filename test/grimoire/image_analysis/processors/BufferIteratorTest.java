package grimoire.image_analysis.processors;

import com.paratussoftware.image_analysis.buffer.Buffer;
import com.paratussoftware.image_analysis.buffer.FifoBufferIterator;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


public class BufferIteratorTest {

    @Test
    public void testConstruction() throws Exception{
        int expectedLastIndex = 1;
        int expectedFirstIndex = 0;
        String[] expectedValues = {"one", "two"};

        FifoBufferIterator<String> iterator = new FifoBufferIterator<>(0, 1, expectedValues);

//        assertSame(expectedBuffer, fifoIterator.getBuffer());
        assertEquals(expectedLastIndex, iterator.getIndex());
    }

    @Test
    public void testIteratorBeforeCapacityReached() throws Exception{
        Buffer<String> buffer = new Buffer<String>(5);
        String value1 = "hello";
        String value2 = "world";
        String value3 = "foo";
        addArrayToBuffer(buffer, new String[]{value1, value2, value3});

        Iterator<String> iterator = buffer.fifoIterator();

        assertTrue(iterator.hasNext());
        assertEquals(value3, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(value2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(value1, iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorWhenCapacityReached() throws Exception{
        Buffer<String> buffer = new Buffer<String>(3);
        String value1 = "hello";
        String value2 = "world";
        String value3 = "foo";
        addArrayToBuffer(buffer, new String[]{value1, value2, value3});

        Iterator<String> iterator = buffer.fifoIterator();

        assertTrue(iterator.hasNext());
        assertEquals(value3, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(value2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(value1, iterator.next());

        assertFalse(iterator.hasNext());
    }


    @Test
    public void testIterator_afterCapacityReached() throws Exception{
        Buffer<String> buffer = new Buffer<String>(3);
        String value1 = "hello";
        String value2 = "world";
        String value3 = "foo";
        String value4 = "bar";
        addArrayToBuffer(buffer, new String[]{value1, value2, value3});
        buffer.add(value4);

        assertEquals(0, buffer.getLastIndex());
        assertEquals(1, buffer.getFirstIndex());

        Iterator<String> iterator = buffer.fifoIterator();

        assertTrue(iterator.hasNext());
        assertSame(value4, iterator.next());

        assertTrue(iterator.hasNext());
        assertSame(value3, iterator.next());

        assertTrue(iterator.hasNext());
        assertSame(value2, iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testHasNextIsFalseWhenValuesIsEmpty() throws Exception{
        FifoBufferIterator<Object> iterator = new FifoBufferIterator<>(0, -1, new Object[0]);

        assertFalse(iterator.hasNext());
    }


    private void addArrayToBuffer(Buffer buffer, Object[] clusters){
        for (Object cluster : clusters) {
            buffer.add(cluster);
        }

    }

}