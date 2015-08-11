package datastructures;

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

    public void enqueue(T item) {

        queue[last++] = item;
        size++;
        if (isFull())
            resize(size * 2);
        last %= queue.length;

    }

    public void resize(int capacity) {
        T[] newQueue = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(i + first) % size];
        }
        queue = newQueue;
    }

    public T dequeue() {
        if (isEmpty())
            return null;
        T item = queue[first];
        queue[first] = null;
        first++;
        first = first % queue.length;
        size--;
        return item;
    }

    public void traverse(){
        if(isEmpty()) {
            System.out.print("Empty");
            return;
        }
            for (int i = 0; i < size; i++) {
            System.out.print(queue[(i + first) % queue.length] + " ");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == queue.length;
    }

}
