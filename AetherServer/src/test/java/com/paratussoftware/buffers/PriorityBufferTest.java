package com.paratussoftware.buffers;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PriorityBufferTest {

    private int capacity = 4;
    private PriorityBuffer<String> priorityBuffer;
    private Comparator<String> highestFirstLengthComparator;

    @Before
    public void setUp(){
        highestFirstLengthComparator = (string1, string2) -> Integer.compare(string2.length(), string1.length());
        priorityBuffer = new PriorityBuffer<>(capacity, highestFirstLengthComparator);
    }

    @Test
    public void construction() {
        assertEquals(capacity, priorityBuffer.getCapacity());
        assertTrue(priorityBuffer.isEmpty());
        assertSame(highestFirstLengthComparator, priorityBuffer.getComparator());
    }
    
    @Test
    public void pushElement() {
        String element1 = "Lorem ipsum dolor sit amet";

        priorityBuffer.push(element1);

        assertEquals(1, priorityBuffer.size());
        assertTrue(priorityBuffer.contains(element1));

        String element2 = "consectetur adipiscing elit.";

        priorityBuffer.push(element2);

        assertEquals(2, priorityBuffer.size());
        assertTrue(priorityBuffer.contains(element2));
    }

    @Test
    public void popElement() {
        String element1 = "Lorem ipsum dolor sit amet";
        priorityBuffer.push(element1);

        String poppedElement = priorityBuffer.getFirst();

        assertSame(element1, poppedElement);
    }
    
    @Test
    public void takeFirstRemovesAndReturnsObjectsByComparatorPriority() {
        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "aliquam libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        priorityBuffer.push(element1);
        priorityBuffer.push(element2);
        priorityBuffer.push(element3);

        assertSame(element3, priorityBuffer.getFirst());
        assertEquals(2, priorityBuffer.size());
        assertFalse(priorityBuffer.contains(element3));

        assertSame(element1, priorityBuffer.getFirst());
        assertEquals(1, priorityBuffer.size());
        assertFalse(priorityBuffer.contains(element1));

        assertSame(element2, priorityBuffer.getFirst());
        assertTrue(priorityBuffer.isEmpty());
    }

    @Test
    public void getReturnsObjectsByComparatorPriority() {
        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "aliquam libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        priorityBuffer.push(element1);
        priorityBuffer.push(element2);
        priorityBuffer.push(element3);

        assertSame(element3, priorityBuffer.get(0));
        assertSame(element1, priorityBuffer.get(1));
        assertSame(element2, priorityBuffer.get(2));

        assertEquals(3, priorityBuffer.size());
        assertTrue(priorityBuffer.contains(element1));
        assertTrue(priorityBuffer.contains(element2));
        assertTrue(priorityBuffer.contains(element3));
    }

    @Test
    public void addElement_overwritesSmallestElementWhenFull() {
        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "aliquam libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        String element4 = " Integer iaculis pellentesque nulla id";
        String element5 = " Class aptent taciti sociosqu ad litora";

        priorityBuffer.push(element1);
        priorityBuffer.push(element2);
        priorityBuffer.push(element3);
        priorityBuffer.push(element4);
        priorityBuffer.push(element5);

        assertEquals(4, priorityBuffer.size());
        assertFalse(priorityBuffer.contains(element2));

        assertEquals(element3, priorityBuffer.get(0));
        assertEquals(element5, priorityBuffer.get(1));
        assertEquals(element4, priorityBuffer.get(2));
        assertEquals(element1, priorityBuffer.get(3));
    }

    @Test
    public void sortWithAnotherComparatorWithoutModifyingTheBuffer() {
        Comparator<String> alphaComparator = String::compareTo;

        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "Libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        priorityBuffer.push(element1);
        priorityBuffer.push(element2);
        priorityBuffer.push(element3);

        LinkedList<String> sortedList = priorityBuffer.sort(alphaComparator);

        assertEquals(element2, sortedList.get(0));
        assertEquals(element3, sortedList.get(1));
        assertEquals(element1, sortedList.get(2));

        assertEquals(element3, priorityBuffer.get(0));
        assertEquals(element1, priorityBuffer.get(1));
        assertEquals(element2, priorityBuffer.get(2));
    }

    @Test
    public void asListReturnsACopyOfTheBuffersList() {
        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "Libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        priorityBuffer.push(element1);
        priorityBuffer.push(element2);
        priorityBuffer.push(element3);

        List<String> bufferAsList = priorityBuffer.asList();

        assertEquals(element3, bufferAsList.get(0));
        assertEquals(element1, bufferAsList.get(1));
        assertEquals(element2, bufferAsList.get(2));
    }

}