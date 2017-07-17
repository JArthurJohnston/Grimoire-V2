package com.paratussoftware.data;

public class RingBuffer<T> {
    private final T[] values;
    private final int mask;
    private final int capacity;
    private int writeIndex;
    private int readIndex;

    /**
     * For this to work correctly, the capacity of the buffer
     * MUST be a power of 2. Otherwise the bitwise AND with the mask
     * variable wont work when pushing/popping.
     *
     * @param capacity MUST be a power of 2
     */
    public RingBuffer(final int capacity) {
        if (!isPowerOfTwo(capacity)) {
            throw new RuntimeException("Capacity must be a power of two");
        }
        this.capacity = capacity;
        this.mask = capacity - 1;
        this.values = (T[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return this.readIndex == this.writeIndex;
    }

    public T[] getValues() {
        return this.values;
    }

    public void write(final T value) {
        this.values[this.writeIndex++ & this.mask] = value;
    }

    public T read() {
        return this.values[this.readIndex++ & this.mask];
    }

    public T get(final int index) {
        return this.values[(this.readIndex + index) & this.mask];
    }

    private boolean isPowerOfTwo(final int capacity) {
        return (capacity & (capacity - 1)) == 0;
    }

    public void put(final T value) throws InterruptedException {
        synchronized (this) {
            while (readingFromWriteIndex()) {
                wait();
            }
            write(value);
            notify();
        }
    }

    private boolean readingFromWriteIndex() {
        return (this.writeIndex & this.mask) == (this.readIndex & this.mask) && !isEmpty();
    }

    public T take() throws InterruptedException {
        synchronized (this) {
            while (isEmpty()) {
                wait();
            }
            notify();
            return read();
        }
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int size() {
        return this.writeIndex < this.capacity ? this.writeIndex : this.capacity;
    }
}
