template<class T, long size>
class ConcurrentBuffer{
    T[] values;
    ConcurrentBuffer(int capacity){
        this.values = new T[capacity]();
    }

    T *pop();
    void push(T *image);
}

