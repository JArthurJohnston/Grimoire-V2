package com.paratussoftware.buffers;

public class ByteArrayRingBuffer {

    private byte[][] values;
    private int readIndex, writeIndex, mask;

    public ByteArrayRingBuffer(int capacity, int elementLength) {
        if(!isPowerOfTwo(capacity)){
            throw new RuntimeException("Capacity must be a power of two");
        }
        this.mask = capacity - 1;
        this.values = new byte[capacity][elementLength];
    }

    public byte[][] getValues() {
        return this.values;
    }

    public synchronized byte[] push(){
        return this.values[writeIndex++ & mask];
    }

    public synchronized byte[] read(){
        return this.values[readIndex++ & mask];
    }

    private boolean isPowerOfTwo(int capacity){
        return (capacity & (capacity - 1)) == 0;
    }
}
