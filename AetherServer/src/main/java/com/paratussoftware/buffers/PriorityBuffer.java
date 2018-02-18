package com.paratussoftware.buffers;

import java.util.*;

public class PriorityBuffer<T> {
    //consider subclassing LinkedList, or implementing the List interface

    private final int capacity;
    private final LinkedList<T> buffer;
    private Comparator<T> comparator;

    public PriorityBuffer(int capacity, Comparator<T> comparator) {
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
            sortedAdd(element);
        } else {
            this.buffer.removeLast();
            sortedAdd(element);
        }
    }

    public T getFirst() {
        return this.buffer.poll();
    }

    public boolean contains(T element) {
        return this.buffer.contains(element);
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public T get(int index) {
        return this.buffer.get(index);
    }

    public LinkedList<T> sort(Comparator<T> comparator){
        LinkedList<T> bufferCopy = copyBuffer();
        bufferCopy.sort(comparator);
        return bufferCopy;
    }

    public List<T> asList(){
        return copyBuffer();
    }

    private void sortedAdd(T element) {
        this.buffer.add(element);
        this.buffer.sort(this.comparator);
    }

    private LinkedList<T> copyBuffer() {
        LinkedList<T> bufferCopy = new LinkedList<>();
        bufferCopy.addAll(this.buffer);
        return bufferCopy;
    }
}
