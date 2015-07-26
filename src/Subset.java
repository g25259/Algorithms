
/**
 * Created by MingJe on 2015/7/25.
 */
public class Subset {
    public static void main(String[] args) {
        /*int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String sequence = "";
        StringBuffer stringBuffer = new StringBuffer();
        while (!StdIn.isEmpty()) {
            stringBuffer.append(" " + StdIn.readString());
        }
        sequence = stringBuffer.toString();
        //sequence = "\"AA BB BB BB BB BB CC CC \"";
        //sequence = sequence.substring(sequence.indexOf("\"") + 1, sequence.lastIndexOf("\"") - 1);
        String[] items = sequence.split("\\s+");
        int N = items.length;
        for (int i = 0; i < k; i++) {
            int r = StdRandom.uniform(0, N - i);
            queue.enqueue(items[r]);
            items[r] = items[N - 1 - i];
        }
        for (String item : queue)
            StdOut.println(item);
    */
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            q.enqueue(item);
        }
        while (k > 0) {
            StdOut.println(q.dequeue());
            k--;
        }
    }
}
