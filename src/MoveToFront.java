import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by g2525_000 on 2015/12/28.
 */
public class MoveToFront {

    private static final int R = 256;
    private static char[] order;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        order = new char[R];
        for (char i = 0; i < order.length; i++) {
            order[i] =  i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            char pos;
            for (pos = 0; pos < R; pos++) {
                if (c == order[pos]) {
                    BinaryStdOut.write(pos, 8);
                    break;
                }
            }
            swim(order, pos);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        order = new char[R];
        for (char i = 0; i < R; i++) {
            order[i] =  i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            //StdOut.print((int)c);
            //BinaryStdOut.write((int)c);
            BinaryStdOut.write(order[c]);
            swim(order, c);
        }
        BinaryStdOut.close();
    }

    private static void swim(char[] order, char pos) {
        for (int i = pos; i > 0; i--) {
            char tmp = order[i];
            order[i] = order[i - 1];
            order[i - 1] = tmp;
        }
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
