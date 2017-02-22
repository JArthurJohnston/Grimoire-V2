package grimoire.models.processors;

import java.util.Iterator;

public class BufferIterator<T> implements Iterator<T> {
    private boolean firstElementHasBeenReturned;
    private int firstIndex;
    private int index;
    private T[] values;

    BufferIterator(int firstIndex, int lastIndex, T[] values){
        this.firstIndex = firstIndex;
        index = lastIndex;
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return !firstElementHasBeenReturned && index >= 0;
    }

    private int decrementIndex(){
        index--;
        if(index < 0)
            index = values.length - 1;
        return index;
    }

    @Override
    public T next() {
        int currentIndex = index;
        decrementIndex();
        if(currentIndex == firstIndex){
            firstElementHasBeenReturned = true;
        }
        return values[currentIndex];
    }

    public int getIndex() {
        return index;
    }
}
