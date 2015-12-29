import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
/**
 * Created by g2525_000 on 2015/12/28.
 */
public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        //String s = "abcdefghijklmnopqrstuvwxyz0123456789";
        String s = BinaryStdIn.readString();
        CircularSuffixArray array = new CircularSuffixArray(s);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            if (array.index(i) == 0) {
                BinaryStdOut.write(i);
                sb.append(s.charAt(s.length() - 1));
            } else
                sb.append(s.charAt(array.index(i) - 1));
        }
        for (int i = 0; i < sb.length(); i++) {
            BinaryStdOut.write(sb.charAt(i));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        //int first = 10;
        //String s = "z0123456789abcdefghijklmnopqrstuvwxy";
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int N = s.length();
        char[] next = new char[N];
        char[] count = new char[R + 1];
        for (int i = 0; i < N; i++)
            count[s.charAt(i) + 1]++;
        for (char r = 0; r < R; r++)
            count[r + 1] += count[r];
        for (int i = 0; i < N; i++)
            next[count[s.charAt(i)]++] = (char) i;
        for (int i = next[first], j = 0; j < N; i = next[i], j++)
            BinaryStdOut.write(s.charAt(i));
        BinaryStdOut.close();

    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
