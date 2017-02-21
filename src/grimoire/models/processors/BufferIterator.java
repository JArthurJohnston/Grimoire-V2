package grimoire.models.processors;

import java.util.Iterator;

public class BufferIterator<T> implements Iterator<T> {
    private int index;
    private Buffer<T> buffer;

    BufferIterator(Buffer<T> buffer){
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
