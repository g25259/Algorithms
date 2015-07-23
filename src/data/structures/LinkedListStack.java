package data.structures;

/**
 * Created by MingJe on 2015/7/23.
 */
public class LinkedListStack<T> {
    private int size;
    private Node<T> top, bottom;

    public void push(T item) {
        Node<T> newItem = new Node<>(item);
        if (isEmpty())
            top = bottom = newItem;
        else {
            newItem.next = top;
            top = newItem;
        }
        size++;
    }

    public T pop() {
        if (isEmpty())
            return null;
        T temp = top.value;
        top = top.next;
        size--;
        return temp;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T peek() {
        return bottom.value;
    }

    public void traverse() {
        Node<T> current = top;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
    }

    class Node<T> {
        Node<T> next;
        T value;

        Node(T item) {
            value = item;
        }
    }

}
