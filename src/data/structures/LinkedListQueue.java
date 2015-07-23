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
        if(isEmpty())
            return null;
        T item = first.value;
        Node temp = first;
        temp = null;
        first = first.next;
        size--;
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void traverse(){
        if(isEmpty()) {
            System.out.print("Empty");
            return;
        }
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            System.out.print(current.value + " ");
            current = current.next;
        }
    }

    class Node<T> {
        Node next;
        T value;
    }
}
