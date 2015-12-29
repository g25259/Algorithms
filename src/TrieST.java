import edu.princeton.cs.algs4.Queue;

/**
 * Created by MingJe on 2015/12/16.
 */
public class TrieST<Value> {
    private static final int R = 26;
    private Node root;


    private static class Node {
        protected Object value;
        private Node[] next = new Node[R];
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.value = val;
            return x;
        }
        char c = (char) (key.charAt(d) - 'A');
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.value;
    }

    public Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = (char) (key.charAt(d) - 'A');
        return get(x.next[c], key, d + 1);
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Iterable<String> collect() {
        Queue<String> q = new Queue<>();
        collect(root, "", q);
        return q;
    }

    private void collect(Node x, String prefix, Queue<String> queue) {
        if (x == null) return;
        if (x.value != null) queue.enqueue(prefix);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], prefix + c, queue);
        }
    }

    public Iterable<String> keyWithPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        Queue<String> queue = new Queue<>();
        collect(x, prefix, queue);
        if (queue.size() == 0)
            return null;
        return queue;
    }

    public boolean isKeyWithPrefix(Node x, String prefix) {
        //Node x = get(root, prefix, 0);
        Queue<String> q = new Queue<>();
        dig(x, prefix, q);
        if (q.size() == 0)
            return false;
        return true;
    }

    private void dig(Node x, String prefix, Queue<String> queue) {
        if (x == null) return;
        if (x.value != null) {
            queue.enqueue(prefix);
            return;
        }
        for (char c = 0; c < R; c++) {
            collect(x.next[c], prefix + c, queue);
        }
    }

    public String longestPrefixOf(String query) {
        return query.substring(0, search(root, query, 0, 0));

    }

    private int search(Node x, String query, int length, int d) {
        if (x == null) return length;
        if (x.value != null) length = d;
        if (d == query.length()) return length;
        return search(x.next[query.charAt(d)], query, length, d + 1);
    }

    public static void main(String[] args) {
        TrieST<Integer> trieST = new TrieST<>();
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
        System.out.println(trieST.longestPrefixOf("shells"));
    }
}
