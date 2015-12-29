/**
 * Created by g2525_000 on 2015/12/28.
 */
public class CircularSuffixArray {

    private static final int R = 256;   // extended ASCII alphabet size
    private static final int CUTOFF = 15;   // cutoff to insertion sort
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new java.lang.NullPointerException();
        index = new int[s.length()];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
        sortSuffixesArray(s, 0, s.length() - 1, 0, new int[index.length]);
    }

    // length of s
    public int length() {
        return index.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > index.length - 1)
            throw new java.lang.IndexOutOfBoundsException();

        return index[i];
    }

    //Revised MSD for circular suffixes array
    private void sortSuffixesArray(String s, int lo, int hi, int d, int[] aux) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(s, lo, hi, d);
            return;
        }
        // if (hi <= lo) return;
        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(s, index[i], d);
            count[c + 2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(s, index[i], d);
            aux[count[c + 1]++] = index[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++)
            index[i] = aux[i - lo];

        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sortSuffixesArray(s, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
    }

    //Revised LSD
    private void sortSuffixesArray(String s, int W) {
         /*int R = 256;
        int N = index.length;
        int[] aux = new int[N];
        for (int d = W-1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++)
                count[s.charAt((index[i] + d) % N) + 1]++;
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            for (int i = 0; i < N; i++)
                aux[count[s.charAt((index[i] + d) % N)]++] = index[i];
            for (int i = 0; i < N; i++) {
                index[i] = aux[i];
            }
        }*/
        // index =aux;
    }
    private static int charAt(String s, int start, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt((start + d) % s.length());
    }

    // insertion sort a[lo..hi], starting at dth character
    private void insertion(String a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a, index[j], index[j - 1], d); j--)
                exch(j, j - 1);
    }

    // exchange a[i] and a[j]
    private void exch(int i, int j) {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }

    // is v less than w, starting at character d
    private static boolean less(String s, int v, int w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        int N = s.length();
        for (int i = d; i < N; i++) {
            if (charAt(s, v, i) < charAt(s, w, i)) return true;
            if (charAt(s, v, i) > charAt(s, w, i)) return false;
        }
        return false;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        //String s = StdIn.readAll();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            System.out.println(circularSuffixArray.index(i));
        }
    }
}
