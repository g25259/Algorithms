import java.util.Iterator;

/**
 * Created by g2525_000 on 2015/7/24.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int first;
    private int last;
    private int size;

    public RandomizedQueue() {
        queue = (Item[]) new Object[5];
    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        return size == 0;
    }                // is the queue empty?

    public int size() {
        return size;
    }                       // return the number of items on the queue

    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();

        queue[last++] = item;
        size++;

        if (size == queue.length)
            resize(size * 2);

        last %= queue.length;

    }          // add the item

    private void resize(int capacity) {
        Item[] newQueue = (Item[]) new Object[capacity];
        if(last < first && size != queue.length){
            for (int i = 0; i < first - last - 1; i++) {
                queue[first + i -1] = queue[first + i];
            }
        }
        StdRandom.shuffle(queue, 0, size - 1);

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(i + first) % size];
        }
        first = 0;
        last = size;
        queue = newQueue;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = queue[first++];
        size--;
        if (size > 0 && size == queue.length / 4)
            resize(queue.length / 2);
        first = first % queue.length;
        return item;
    }                   // remove and return a random item

    public Item sample() {
        return queue[size];
    }                    // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new InnerIterator(queue);
    }        // return an independent iterator over items in random order

    private class InnerIterator<Item> implements Iterator<Item> {
        private int size;
        private Item[] queueIterator;

        public InnerIterator(Item[] queue) {
            size = size();
            queueIterator = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                queueIterator[i] = queue[(first + i) % queue.length];
            }
            StdRandom.shuffle(queueIterator, 0, size - 1);

        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return queueIterator[--size];
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Double> queue = new RandomizedQueue();

        queue.enqueue(Math.random());
        queue.enqueue(Math.random());
        queue.enqueue(Math.random());
        queue.enqueue(Math.random());
        queue.enqueue(Math.random());
        queue.enqueue(Math.random());

        for (int i = 0; i < 50; i++) {
            if (Math.random() > 0.5) {
                double item = Math.random();
                queue.enqueue(item);
                System.out.println("Enqueue, size : " + queue.size() + " Item : " + item);
            } else {
                double item = queue.dequeue();
                System.out.println("Dequeue, size : " + queue.size() + " Item : " + item);
            }

        }
    }  // unit testing

}
