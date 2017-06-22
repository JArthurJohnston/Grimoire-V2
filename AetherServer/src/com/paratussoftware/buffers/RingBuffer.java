package com.paratussoftware.buffers;

public class RingBuffer<T> {
    private T[] values;
    private int writeIndex, readIndex, mask;
    private int capacity;

    /**
     * For this to work correctly, the capacity of the buffer
     * MUST be a power of 2. Otherwise the bitwise AND with the mask
     * variable wont work when pushing/popping.
     *
     * @param capacity MUST be a power of 2
     */
    public RingBuffer(int capacity) {
        if(!isPowerOfTwo(capacity)){
            throw new RuntimeException("Capacity must be a power of two");
        }
        this.capacity = capacity;
        this.mask = capacity - 1;
        this.values = (T[])new Object[capacity];
    }

    public boolean isEmpty(){
        return readIndex == writeIndex;
    }

    public T[] getValues() {
        return values;
    }

    public void write(T value) {
        values[writeIndex++ & mask] = value;
    }

    public T read() {
        return values[readIndex++ & mask];
    }

    public T get(int index){
        return values[(readIndex + index) & mask];
    }

    private boolean isPowerOfTwo(int capacity){
        return (capacity & (capacity - 1)) == 0;
    }

    public void put(T value) throws InterruptedException {
        synchronized (this){
            while (readingFromWriteIndex()){
                wait();
            }
            write(value);
            notify();
        }
    }

    private boolean readingFromWriteIndex() {
        return (writeIndex & mask) == (readIndex & mask) && !isEmpty();
    }

    public T take() throws InterruptedException {
        synchronized (this){
            while (isEmpty()){
                wait();
            }
            notify();
            return read();
        }
    }

    public int getCapacity() {
        return capacity;
    }
}
