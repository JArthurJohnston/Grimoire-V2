template<class T, long size>
class ConcurrentBuffer{
    ConcurrentBuffer(){

    }

    T *pop();
    void push(T *image);
}

