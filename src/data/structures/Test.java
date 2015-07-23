package data.structures;

/**
 * Created by MingJe on 2015/7/23.
 */
public class Test {
    public static void main(String[] args) {
        LinkedListStack<Double> doubleStack = new LinkedListStack<>();
        ArrayStack<Double> doubleArrayStack = new ArrayStack<>(10);
        LinkedListQueue<Double> doubleLinkedListQueue = new LinkedListQueue<>();
        ArrayQueue<Double> doubleArrayQueue = new ArrayQueue<>(10);
        for (int i = 0; i < 5; i++) {
            double item = Math.random() * 10 + 1;
            doubleStack.push(item);
            doubleArrayStack.push(item);
            doubleLinkedListQueue.enqueue(item);
            doubleArrayQueue.enqueue(item);
            System.out.println(item);
        }

        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop() +
                " " + doubleLinkedListQueue.dequeue() + " " + doubleArrayQueue.dequeue());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop() +
                " " + doubleLinkedListQueue.dequeue() + " " + doubleArrayQueue.dequeue());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop() +
                " " + doubleLinkedListQueue.dequeue() + " " + doubleArrayQueue.dequeue());

        for (int i = 0; i < 2; i++) {
            double item = Math.random() * 10 + 1;
            doubleStack.push(item);
            doubleArrayStack.push(item);
            doubleLinkedListQueue.enqueue(item);
            doubleArrayQueue.enqueue(item);
            System.out.println(item);
        }
        doubleStack.traverse();
        System.out.println();
        doubleArrayStack.traverse();
        System.out.println();
        doubleLinkedListQueue.traverse();
        System.out.println();
        doubleArrayQueue.traverse();
        System.out.println();

        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop() +
                " " + doubleLinkedListQueue.dequeue() + " " + doubleArrayQueue.dequeue());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop() +
                " " + doubleLinkedListQueue.dequeue() + " " + doubleArrayQueue.dequeue());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop() +
                " " + doubleLinkedListQueue.dequeue() + " " + doubleArrayQueue.dequeue());

        doubleStack.traverse();
        System.out.println();
        doubleArrayStack.traverse();
        System.out.println();
        doubleLinkedListQueue.traverse();
        System.out.println();
        doubleArrayQueue.traverse();
        System.out.println();
        System.out.println(doubleStack.size() + " " + doubleArrayStack.size() + " "
                + doubleLinkedListQueue.size() + " " + doubleArrayQueue.size());

    }
}
