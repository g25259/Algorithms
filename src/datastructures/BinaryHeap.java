package datastructures;

import java.util.Arrays;

/**
 * Created by MingJe on 2015/8/2.
 */
public class BinaryHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;

    public BinaryHeap() {
        heap = (T[]) new Comparable[10];
    }

    public BinaryHeap(int capacity) {
        heap = (T[]) new Comparable[capacity + 1];
    }


    public BinaryHeap(T[] items) {
        size = items.length;
        heap = (T[]) new Comparable[size + 1];
        System.arraycopy(items, 0, heap, 1, size);
        bottomUp();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T item) {
        if (size + 1 == heap.length)
            resize(2 * heap.length);
        heap[++size] = item;
        swim(size);
    }

    private void resize(int capacity) {
        T[] newHeap = (T[]) new Comparable[capacity];
        newHeap = Arrays.copyOf(heap, size + 1);
        heap = newHeap;

    }

    private void swim(int k) {
        while (k >= 2 && heap[k].compareTo(heap[k / 2]) > 0) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (size >= 2 * k) {
            int j = 2 * k;
            if (j < size && heap[j].compareTo(heap[j + 1]) < 0) j++;
            if (heap[k].compareTo(heap[j]) >= 0) break;
            swap(k, j);
            k = j;
        }
    }

    private void bottomUp() {
        for (int i = size / 2; i >= 1; i--) {
            sink(i);
        }
    }

    private void topDown() {

    }

    public int size() {
        if (isEmpty()) throw new java.lang.ArrayIndexOutOfBoundsException("Empty Heap");
        return size;
    }

    public T delMax() {
        T max = heap[1];
        heap[1] = heap[size--];
        heap[size + 1] = null;
        sink(1);
        if (!isEmpty() && size < heap.length / 4) resize(heap.length / 2);
        return max;
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(Arrays.toString(heap));
        String ss = s.toString();
        return ss;
    }

    public static void main(String[] args) {
        Integer[] k = new Integer[10];
        for (int i = 0; i < k.length; i++) {
            k[i] = (int) (Math.random() * 10 + 1);
        }
        BinaryHeap bh = new BinaryHeap(k);
        System.out.println(bh);
        bh.delMax();
        System.out.println(bh);

    }

}
