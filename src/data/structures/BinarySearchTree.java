package data.structures;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by MingJe on 2015/8/2.
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    protected class Node {
        Key key;
        Value value;
        Node left, right;
        boolean color;
        int count;

        Node(Key k, Value v, int count, boolean color) {
            this.key = k;
            this.value = v;
            this.count = count;
            this.color = color;
        }
    }

    protected Node root;

    public void put(Key k, Value v) {
        root = put(root, k, v);
    }

    private Node put(Node x, Key k, Value v) {
        if (x == null) return new Node(k, v, 1, false);
        int cmp = k.compareTo(x.key);
        if (cmp > 0) x.right = put(x.right, k, v);
        else if (cmp < 0) x.left = put(x.left, k, v);
        else x.value = v;
        x.count = size(x.left) + size(x.right) + 1;
        return x;

    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
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
        delete(k, root);
    }

    public Node delete(Key k, Node x) {
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp < 0) x.left = delete(k, x.left);
        else if (cmp > 0) x.right = delete(k, x.right);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;

            Node t = x;
            x = min(x.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }
    public Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
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

        Node canidate = x;
        x = floor(k, x.right);
        if (x == null) return canidate;
        return x;

    }

    public Key ceil(Key k) {
        Node ceil = ceil(k, root);
        return ceil.key;
    }

    private Node ceil(Key k, Node x) {
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp > 0) return ceil(k, x.right);
        else if (cmp == 0) return x;

        Node canidate = x;
        x = ceil(k, x.left);
        if (x == null) return canidate;
        return x;
    }

    public int rank(Key k) {
        return rank(k, root);
    }

    private int rank(Key k, Node x) {
        if (x == null) return 0;
        int cmp = k.compareTo(x.key);
        if (cmp == 0) return size(x.left);
        if (cmp < 0) return rank(k, x.left);
        else return size(x.left) + 1 + rank(k, x.right);
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
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 1);
        bst.put("X", 1);
        bst.put("E", 1);
        bst.put("A", 1);
        bst.put("C", 1);
        bst.put("R", 1);
        bst.put("H", 1);
        bst.put("M", 1);

        System.out.println(bst.floor("G"));
        System.out.println(bst.ceil("L"));
        System.out.println(bst.rank("G"));
        bst.delete("E");
        for (String item : bst.iterator()) System.out.println(item);

    }


}
