package com.paratussoftware.image_analysis.buffer;

import java.util.Iterator;

public class LifoBufferIterator<T> implements Iterator<T> {
    private int index;
    private Buffer<T> buffer;


    LifoBufferIterator(Buffer<T> buffer){
        this.buffer = buffer;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < buffer.getCapacity() && buffer.get(index) != null;
    }

    @Override
    public T next() {
        return buffer.get(index++);
    }
}
