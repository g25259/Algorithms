/**
 * Created by MingJe on 2015/12/17.
 */
public class RabinKarp {
    private static final int R = 256;
    private long Q;
    private long patHash;
    private long RM;
    private int M;

    public RabinKarp(String pat) {
        Q = longRandomPrime();
        int M = pat.length();
        this.M = M;
        patHash = hash(pat, M);

        RM = 1;
        for (int i = 1; i <= M - 1; i++) {
            RM = (RM * R) % Q;
        }

    }

    public int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        if (patHash == txtHash) return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - txt.charAt(i - M) * RM % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (txtHash == patHash) return i - M + 1;
        }
        return N;
    }

    private long hash(String txt, int M) {
        long hash = 0;
        for (int i = 0; i < M; i++) {
            hash = (hash * R + txt.charAt(i)) % Q;
        }
        return hash;
    }

    private long longRandomPrime() {
        return 997;
    }
}
