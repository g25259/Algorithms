import javax.print.DocFlavor;

/**
 * Created by MingJe on 2015/12/16.
 */
public class TernaryST<Value> {
    private Node root = new Node();


    private class Node {
        private Value value;
        private char c;
        private Node left, mid, right;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {x = new Node(); x.c = c;}
        if (c < x.c) x.left = put(x.left, key, val, d);
        else if (c > x.c) x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1) x.mid = put(x.mid, key, val, d + 1);
        else x.value = val;
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid, key, d + 1);
        return x;
    }

    public static void main(String[] args) {
        TernaryST<Integer> trieST = new TernaryST<>();
        trieST.put("by", 4);
        trieST.put("sea", 6);
        trieST.put("sells", 1);
        trieST.put("she", 0);
        trieST.put("shells", 3);
        trieST.put("shore", 7);
        trieST.put("the", 5);
        System.out.println(trieST.get("sells"));
        System.out.println(trieST.get("the"));
        System.out.println(trieST.get("te"));
    }

}
