
/**
 * Created by MingJe on 2015/7/25.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String sequence = "";
        while (StdIn.hasNextChar()) {
            sequence += " " + StdIn.readString();
        }
        sequence = sequence.substring(2, sequence.length() - 1);
        String[] items = sequence.split(" ");

        int N = items.length;
        for (int i = 0; i < k; i++) {
            int r = StdRandom.uniform(0, N - i);
            queue.enqueue(items[r]);
            items[r] = items[N - 1 - i];
        }
        for (String item : queue)
            StdOut.println(item);

    }
}
