package grimoire.image_analysis.buffer;

public class RingBuffer<T>{

    private T[] values;
    private int writeIndex, readIndex, mask;

    /**
     * For this to work correctly, the capacity of the buffer
     * MUST be a power of 2. Otherwise the bitwise AND with the mask
     * variable wont work when pushing/popping.
     *
     * @param capacity MUST be a power of 2
     */
    public RingBuffer(int capacity) {
        mask = capacity - 1;
        this.values = (T[])new Object[capacity];
    }

    public int getCapacity() {
        return this.values.length;
    }

    public boolean isEmpty() {
        return writeIndex == readIndex;
    }

    public void write(T value) {
        values[writeIndex++ & mask] = value;
    }

    public T read() {
        return values[readIndex++ & mask];
    }

    public void put(T value) throws InterruptedException {
        synchronized (this){
            while ((writeIndex & mask) == (readIndex & mask) && !isEmpty()){
//                System.out.println("Blocking Write");
                wait();
            }
            notify();
        }
        write(value);
    }

    public T take() throws InterruptedException {
        synchronized (this){
            while (isEmpty()){
                wait();
                System.out.println("Blocking Read");
            }
            notify();
        }
        return read();
    }
}
