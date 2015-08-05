package data.structures;

import java.time.temporal.ValueRange;

/**
 * Created by g2525_000 on 2015/8/5.
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> extends BinarySearchTree<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    //private Node root;


    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateleft(Node x) {
        assert isRed(x.right);
        Node c = x.right;
        x.right = c.left;
        c.left = x;
        c.color = x.color;
        x.color = RED;
        return c;
    }

    private Node rotateRight(Node x) {
        assert isRed(x.left);
        Node c = x.left;
        x.left = c.right;
        c.right = x;
        c.color = x.color;
        x.color = RED;
        return c;
    }

    private void filpColors(Node x) {
        assert !isRed(x);
        assert isRed(x.left);
        assert isRed(x.right);
        x.color = RED;
        x.left.color = BLACK;
        x.right.color = BLACK;
    }

    @Override
    public void put(Key k, Value v) {
        if (root == null) root = new Node(k, v, 1, BLACK);
        else {
            root = put(root, k, v);
            root.color = false;
        }
    }

    private Node put(Node x, Key k, Value v) {
        if (x == null) return new Node(k, v, 1, RED);
        int cmp = k.compareTo(x.key);
        if (cmp > 0) x.right = put(x.right, k, v);
        else if (cmp < 0) x.left = put(x.left, k, v);
        else x.value = v;
        x.count = size(x.left) + size(x.right) + 1;

        if(isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if(isRed(x.left) && isRed(x.right)) filpColors(x);
        if (isRed(x.right) && !isRed(x.left)) x = rotateleft(x);

        return x;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    public static void main(String[] args) {
        RedBlackBST<String, Integer> rbBST = new RedBlackBST<>();
        rbBST.put("S", 1);
        rbBST.put("E", 1);
        rbBST.put("A", 1);
        rbBST.put("R", 1);
        rbBST.put("C", 1);
        rbBST.put("H", 1);
        rbBST.put("X", 1);
        rbBST.put("M", 1);
        rbBST.put("P", 1);
        rbBST.put("L", 1);

        for (String s : rbBST.inOrder())
            System.out.print(s + " ");
    }

}
