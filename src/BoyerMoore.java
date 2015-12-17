
import java.util.Arrays;

/**
 * Created by g2525_000 on 2015/12/17.
 */
public class BoyerMoore {
    private static final int R = 256;
    private int[] right = new int[R];
    private String pat;
    public BoyerMoore(String pat) {
        this.pat = pat;
        Arrays.fill(right, -1);
        for (int i = 0; i < pat.length(); i++) {
            right[pat.charAt(i)] = i;
        }
    }

    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1 , j - right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) return i;

        }
        return N;
    }
}
