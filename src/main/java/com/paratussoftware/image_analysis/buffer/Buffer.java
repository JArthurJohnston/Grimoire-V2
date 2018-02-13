package com.paratussoftware.image_analysis.buffer;

import java.util.Iterator;

public class Buffer<T> {

    private int firstIndex;
    private int lastIndex;
    private T[] values;
    private final int capacity;
    private boolean capacityHasBeenReached;
    private boolean isEmpty = true;

    public Buffer(int capacity){
        if(capacity == 0){
            throw new RuntimeException("Cannot instantiate Buffer with capacity of 0");
        }
        values = (T[])new Object[capacity];
        this.capacity = capacity;
        firstIndex = 0;
        lastIndex = -1;
        capacityHasBeenReached = false;
    }

    public int getCapacity(){
        return capacity;
    }

    private void incrementIndexes(){
        lastIndex++;
        if(lastIndex == capacity){
            capacityHasBeenReached = true;
            lastIndex = 0;
        }
        if(capacityHasBeenReached){
            if(lastIndex == firstIndex){
                firstIndex++;
                if(firstIndex == capacity){
                    firstIndex = 0;
                }
            }
        }
    }

    public void add(T value){
        isEmpty = false;
        incrementIndexes();
        values[lastIndex] = value;
    }

    public T get(int offset){
        int index = firstIndex + offset;
        return index >= capacity ? values[index - capacity] : values[index];
    }

    public T getFirst(){
        return values[firstIndex];
    }

    public T getLast(){
        return values[lastIndex];
    }

    public Iterator<T> fifoIterator(){
        return new FifoBufferIterator<T>(firstIndex, lastIndex, values);
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

}
