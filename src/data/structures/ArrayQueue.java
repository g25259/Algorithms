package data.structures;

/**
 * Created by MingJe on 2015/7/23.
 */
public class ArrayQueue<T> {
    private int size;
    private T[] queue;
    private int first, last;
    public ArrayQueue() {
        queue = (T[]) new Object[10];
    }

    public ArrayQueue(int capacity) {
        queue = (T[]) new Object[capacity];
    }

    public void enqueue(T item){
        if(isFull())
            resize(size * 2);


    }

    public void resize(int capacity){
        T[] newQueue = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }
    public T dequeue(){

    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private boolean isFull(){
        return size == queue.length;
    }

}
