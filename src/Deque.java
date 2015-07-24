import java.util.Iterator;

/**
 * Created by g2525_000 on 2015/7/24.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> first, last;

    public Deque() {

    }                          // construct an empty deque

    public boolean isEmpty() {
        return size == 0;
    }                // is the deque empty?

    public int size() {
        return size;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> newItem = new Node<>();
        newItem.value = item;
        if (isEmpty()) {
            first = last = newItem;
        } else {
            newItem.next = first;
            first.previous = newItem;
            first = newItem;
        }
        size++;
    }         // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> newItem = new Node<>();
        newItem.value = item;
        if (isEmpty()) {
            first = last = newItem;
        } else {
            last.next = newItem;
            newItem.previous = last;
            last = newItem;
        }
        size++;
    }          // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.value;
        size--;
        if (isEmpty()) {
            first = last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        return item;
    }               // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.value;
        size--;
        if (isEmpty()) {
            first = last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        return item;
    }                // remove and return the item from the end


    @Override
    public Iterator<Item> iterator() {
        return new InnerIterator(first);
    }        // return an iterator over items in order from front to end

    private class InnerIterator<Item> implements Iterator {
        private Node<Item> current;
        private int size;

        public InnerIterator(Node first) {
            current = first;
            size = size();
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Object next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Node temp = current;
            current = current.next;
            size--;
            return temp.value;
        }

        @Override
        public void remove() throws java.lang.UnsupportedOperationException {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private class Node<T> {
        Node<T> next;
        Node<T> previous;
        T value;
    }

    public static void main(String[] args) {
        Deque<Double> deque = new Deque();
        deque.addFirst(0.1);
        deque.addFirst(0.2);
        deque.addLast(0.3);
        deque.addLast(0.4);
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
        deque.removeFirst();
        //deque.removeFirst();
        for (Double item : deque) {
            System.out.print(item + " ");
        }


    }  // unit testing


}
