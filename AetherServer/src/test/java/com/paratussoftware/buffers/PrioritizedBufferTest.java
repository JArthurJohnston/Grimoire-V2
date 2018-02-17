package com.paratussoftware.buffers;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class PrioritizedBufferTest {

    private int capacity = 4;
    private PrioritizedBuffer<String> prioritizedBuffer;
    private Comparator<String> lengthComparator;

    @Before
    public void setUp(){
        //smallest first string length comparator
        lengthComparator = Comparator.comparingInt(String::length);
        prioritizedBuffer = new PrioritizedBuffer<>(capacity, lengthComparator);
    }

    @Test
    public void construction() {
        assertEquals(capacity, prioritizedBuffer.getCapacity());
        assertTrue(prioritizedBuffer.isEmpty());
        assertSame(lengthComparator, prioritizedBuffer.getComparator());
    }
    
    @Test
    public void pushElement() {
        String element1 = "Lorem ipsum dolor sit amet";

        prioritizedBuffer.push(element1);

        assertEquals(1, prioritizedBuffer.size());
        assertTrue(prioritizedBuffer.contains(element1));

        String element2 = "consectetur adipiscing elit.";

        prioritizedBuffer.push(element2);

        assertEquals(2, prioritizedBuffer.size());
        assertTrue(prioritizedBuffer.contains(element2));
    }

    @Test
    public void popElement() {
        String element1 = "Lorem ipsum dolor sit amet";
        prioritizedBuffer.push(element1);

        String poppedElement = prioritizedBuffer.highestElement();

        assertSame(element1, poppedElement);
    }
    
    @Test
    public void popReturnsObjectsByComparatorPriority() {
        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "aliquam libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        prioritizedBuffer.push(element1);
        prioritizedBuffer.push(element2);
        prioritizedBuffer.push(element3);

        assertSame(element2, prioritizedBuffer.highestElement());
        assertSame(element1, prioritizedBuffer.highestElement());
        assertSame(element3, prioritizedBuffer.highestElement());
    }

    @Test
    public void addElement_overwritesSmallestElementWhenFull() {
        String element1 = "Lorem ipsum dolor sit amet";
        String element2 = "Integer quis";
        String element3 = "aliquam libero, in euismod risus. Sed odio dui, fermentum at dapibus quis, aliquam quis nunc. In ornare orci et commodo eleifend. ";
        String element4 = " Integer iaculis pellentesque nulla id";
        String element5 = " Class aptent taciti sociosqu ad litora";

        prioritizedBuffer.push(element1);
        prioritizedBuffer.push(element2);
        prioritizedBuffer.push(element3);
        prioritizedBuffer.push(element4);
        prioritizedBuffer.push(element5);

        assertEquals(4, prioritizedBuffer.size());
        assertFalse(prioritizedBuffer.contains(element2));
    }

}