package data.structures;

/**
 * Created by MingJe on 2015/7/23.
 */
public class Test {
    public static void main(String[] args) {
        LinkedListStack<Double> doubleStack = new LinkedListStack<>();
        ArrayStack<Double> doubleArrayStack = new ArrayStack<>(10);
        for (int i = 0; i < 15; i++) {
            double item = Math.random() * 10 + 1;
            doubleStack.push(item);
            doubleArrayStack.push(item);
            System.out.println(item);
        }
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop());
        System.out.println(doubleStack.pop() + " " + doubleArrayStack.pop());
        doubleStack.traverse();
        System.out.println();
        doubleArrayStack.traverse();
        System.out.println(doubleStack.size() + " " + doubleArrayStack.size());

    }
}
