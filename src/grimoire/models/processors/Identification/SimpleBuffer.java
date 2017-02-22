package grimoire.models.processors.Identification;

import java.util.Iterator;

public class SimpleBuffer<T>{

    private final T[] values;
    private int index;

    public SimpleBuffer(int capacity, T value){
        this(capacity);
        for (int i = 0; i < values.length; i++) {
            values[i] = value;
        }
    }

    private SimpleBuffer(int capacity){
        index = 0;
        if(capacity == 0){
            throw new RuntimeException("Cannot instantiate Buffer with capacity of 0");
        }
        values = (T[])new Object[capacity];
    }

    private int index(){
        index++;
        if(index >= values.length)
            index = 0;
        return index;
    }

    public void add(T value){
        values[index()] = value;
    }

//    public Iterator<T> getIterator(){
//        return new Iterator<T>() {
//            int iteratorIndex = index + 1;
//
//            private int currentIteratorIndex(){
//                if(iteratorIndex >= values.length){
//                    iteratorIndex = 0;
//                }
//                return iteratorIndex;
//            }
//
//            private void incrementIteratorIndex(){
//                iteratorIndex++;
//            }
//
//            @Override
//            public boolean hasNext() {
//                return currentIteratorIndex() != index;
//            }
//
//            @Override
//            public T next() {
//                return values[currentIteratorIndex()];
//            }
//
//        };
//    }


}
