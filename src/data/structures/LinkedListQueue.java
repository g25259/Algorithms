package data.structures;

/**
 * Created by MingJe on 2015/7/23.
 */
public class LinkedListQueue<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public void enqueue(T item) {
        Node<T> newItem = new Node<>();
        newItem.value = item;
        if(isEmpty())
            first = last = newItem;
        else {
            last.next = newItem;
            last = newItem;
        }
        size++;

    }

    public T dequeue() {
        T item = first.value;
        first = first.next;
        size--;
        if(isEmpty())
            last = null;
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }


    class Node<T> {
        Node next;
        T value;
    }
}
