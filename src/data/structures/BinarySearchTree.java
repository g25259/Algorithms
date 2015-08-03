package data.structures;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by MingJe on 2015/8/2.
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int count;

        public Node(Key k, Value v, int count) {
            key = k;
            value = v;
            this.count = count;
        }
    }

    private Node root;

    public void put(Key k, Value v) {
        root = put(root, k, v);
    }

    private Node put(Node x, Key k, Value v) {
        if (x == null) return new Node(k, v, 0);
        int cmp = k.compareTo(x.key);
        if (cmp > 0) x.right = put(x.right, k, v);
        else if (cmp < 0) x.left = put(x.left, k, v);
        else x.value = v;
        x.count = x.left.count + x.right.count + 1;
        return x;

    }

    public Value get(Key k) {
        Node current = root;
        while (current != null) {
            int cmp = k.compareTo(root.key);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return current.value;
        }
        return null;
    }

    public void delete(Key k) {

    }

    public Key floor(Key k) {
        Node floor = floor(k, root);
        return floor.key;
    }

    private Node floor(Key k, Node x) {
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(k, x.left);
        if (cmp > 0 ) {
            Node canidate = x;
            x = floor(k, x.right);
            if (x == null) return canidate;
            return x;
        }
    }

    public Queue<Key> inOrder() {
        Queue<Key> q = new ArrayDeque<>();
        inOrder(root, q);
        return q;
    }

    private void inOrder(Node x, Queue q) {
        if (x == null) return;
        inOrder(x.left, q);
        q.add(x.key);
        inOrder(x.right, q);
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node x) {
        if (x == null) return;
        System.out.println(x.value);
        preOrder(x.left);
        preOrder(x.right);
    }

    public Iterable<Key> iterator() {
        return inOrder();
    }


    public static void main(String[] args) {
        BinarySearchTree<Double, Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 10; i++) {
            bst.put((Math.random() * 10 + 1), (int) (Math.random() * 50 + 1));
        }

        for (Double item : bst.iterator()) System.out.println(item);
    }


}
