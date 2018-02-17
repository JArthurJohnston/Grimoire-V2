package com.paratussoftware.buffers;

import java.util.*;

public class PrioritizedBuffer<T> {


    private final int capacity;
    private final LinkedList<T> buffer;
    private Comparator<T> comparator;

    public PrioritizedBuffer(int capacity, Comparator<T> comparator) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
        this.comparator = comparator;
    }

    public int getCapacity() {
        return capacity;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public int size() {
        return this.buffer.size();
    }

    public void push(T element) {
        if(this.size() < capacity){
            this.buffer.sort(this.comparator);
            this.buffer.add(element);
        }
    }

    public T highestElement() {
        return this.buffer.poll();
    }

    public boolean contains(T element) {
        return this.buffer.contains(element);
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }
}
