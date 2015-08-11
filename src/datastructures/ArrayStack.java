package datastructures;

/**
 * Created by MingJe on 2015/7/23.
 */
public class ArrayStack<T> {
    private T[] stack;
    private int size;

    public ArrayStack(int capacity) {
        stack = (T[]) new Object[capacity];
    }

    public ArrayStack() {
        stack = (T[]) new Object[10];
    }

    public void push(T item) {
        if (isFull()) {
            resize(size * 2);
        }
        stack[size++] = item;
    }

    public boolean isFull() {
        return size == stack.length;
    }

    public T pop() {
        if (isEmpty())
            return null;
        T item = stack[--size];
        stack[size] = null;
        if (!isEmpty() && size < stack.length / 4) {
            resize(stack.length / 2);
        }

        return item;
    }

    private void resize(int capacity) {
        T[] quarterStack = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            quarterStack[i] = stack[i];
        }
        stack = quarterStack;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void traverse() {
        if(isEmpty()) {
            System.out.print("Empty");
            return;
        }
        for (int i = size - 1; i > -1; i--) {
            System.out.print(stack[i] + " ");
        }

    }

    public T peek() {
        if (isEmpty())
            return null;
        T item = stack[size - 1];
        return item;
    }

}
