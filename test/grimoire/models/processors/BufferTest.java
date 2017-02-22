package grimoire.models.processors;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BufferTest {

    @Test
    public void testAddClusterCollection() throws Exception{
        Buffer buffer = new Buffer(5);
        String expected1 = "one";
        String expected2 = "two";

        buffer.add(expected1);

        assertSame(expected1, buffer.getFirst());
        assertSame(expected1, buffer.getLast());

        buffer.add(expected2);

        assertSame(expected1, buffer.getFirst());
        assertSame(expected2, buffer.getLast());
    }

    @Test
    public void testAddOverridesFirstClustersWhenCapacityIsReached() throws Exception{
        String expected1 = "one";
        String expected2 = "two";
        String expected3 = "three";
        String expected4 = "four";
        String expected5 = "five";
        String expected6 = "six";

        Buffer buffer = new Buffer(3);
        String[] initialClusters = {
                expected1, expected2, expected3
        };
        addArrayToBuffer(buffer, initialClusters);

        assertSame(expected1, buffer.getFirst());
        assertSame(expected3, buffer.getLast());

        buffer.add(expected4);

        assertSame(expected2, buffer.getFirst());
        assertSame(expected4, buffer.getLast());

        buffer.add(expected5);

        assertSame(expected3, buffer.getFirst());
        assertSame(expected5, buffer.getLast());

        buffer.add(expected6);

        assertSame(expected4, buffer.getFirst());
        assertSame(expected6, buffer.getLast());
    }

    @Test
    public void testAccessElementByIndex() throws Exception{
        Buffer<String> buffer = new Buffer<>(4);
        String value1 = "hello";
        String value2 = "world";
        String value3 = "foo";
        String value4 = "bar";
        addArrayToBuffer(buffer, new String[]{value1, value2, value3, value4});

        assertSame(value1, buffer.get(0));
        assertSame(value2, buffer.get(1));
        assertSame(value3, buffer.get(2));
        assertSame(value4, buffer.get(3));
    }

    @Test
    public void testAccessElementsByIndex_afterCapacityReached() throws Exception{
        Buffer<String> buffer = new Buffer<String>(3);
        String value1 = "hello";
        String value2 = "world";
        String value3 = "foo";
        String value4 = "bar";

        assertNull(buffer.get(0));

        addArrayToBuffer(buffer, new String[]{value1, value2, value3, value4});

        assertSame(value2, buffer.get(0));
        assertSame(value3, buffer.get(1));
        assertSame(value4, buffer.get(2));
    }

    @Test
    public void testIteratorStartsAtFirstValueEachTimeCalled() throws Exception{
        Buffer<String> buffer = new Buffer<String>(3);
        String value1 = "hello";
        String value2 = "world";
        String value3 = "foo";
        String value4 = "bar";
        addArrayToBuffer(buffer, new String[]{value1, value2, value3});
        buffer.add(value4);

        Iterator<String> iterator = buffer.iterator();

        assertTrue(iterator.hasNext());
        assertSame(value2, iterator.next());

        assertTrue(iterator.hasNext());
        assertSame(value3, iterator.next());

        assertTrue(iterator.hasNext());
        assertSame(value4, iterator.next());

        assertFalse(iterator.hasNext());

        Iterator<String> newIterator = buffer.iterator();

        assertTrue(newIterator.hasNext());
        assertSame(value2, newIterator.next());

        assertTrue(newIterator.hasNext());
        assertSame(value3, newIterator.next());

        assertTrue(newIterator.hasNext());
        assertSame(value4, newIterator.next());

        assertFalse(newIterator.hasNext());
    }



    @Test
    public void testIteratorWhenEmpty() throws Exception{
        Buffer<String> buffer = new Buffer<String>(3);
        Iterator iterator = buffer.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testBufferThrowsExceptionWhenBuiltWithCapacityOfZero() throws Exception{
        try {
            new Buffer<String>(0);
            fail("should not reach here");
        } catch (RuntimeException e){
            assertEquals("Cannot instantiate Buffer with capacity of 0", e.getMessage());
        }
    }

    private void addArrayToBuffer(Buffer buffer, Object[] clusters){
        for (Object cluster : clusters) {
            buffer.add(cluster);
        }

    }
}