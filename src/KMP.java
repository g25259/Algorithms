/**
 * Created by g2525_000 on 2015/12/17.
 */
public class KMP {
    private int[][] dfa;
    private static final int R = 256;
    private String pat;

    public KMP(String pat) {
        this.pat = pat;
        int M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        int X = 0;
        for (int i = 1; i < M; i++) {
            for (int c = 0; c < R; c++)
                dfa[c][i] = dfa[c][X];
            dfa[pat.charAt(i)][i] = i + 1;
            X = dfa[pat.charAt(i)][X];
        }
    }

    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int i = 0, j = 0;
        for (; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M) return i - M;
        else return N;
    }


    public static void main(String[] args) {
        KMP kmp = new KMP("attack at dawn");

        System.out.println(kmp.search("Now is the time for all people to come to the aid of their party. Now is the time for all good people to \n" +
                "come to the aid of their party. Now is the time for many good people to come to the aid of their party. \n" +
                "Now is the time for all good people to come to the aid of their party. Now is the time for a lot of good \n" +
                "people to come to the aid of their party. Now is the time for all of the good people to come to the aid of \n" +
                "their party. Now is the time for all good people to come to the aid of their party. Now is the time for \n" +
                "each good person to come to the aid of their party. Now is the time for all good people to come to the aid \n" +
                "of their party. Now is the time for all good Republicans to come to the aid of their party. Now is the \n" +
                "time for all good people to come to the aid of their party. Now is the time for many or all good people to \n" +
                "come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now \n" +
                "is the time for all good Democrats to come to the aid of their party. Now is the time for all people to \n" +
                "come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now \n" +
                "is the time for many good people to come to the aid of their party. Now is the time for all good people to \n" +
                "come to the aid of their party. Now is the time for a lot of good people to come to the aid of their \n" +
                "party. Now is the time for all of the good people to come to the aid of their party. Now is the time for \n" +
                "all good people to come to the aid of their \n" +
                "attack at dawn\n" +
                " party. Now is the time for each person to come \n" +
                "to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is \n" +
                "the time for all good Republicans to come to the aid of their party. Now is the time for all good people \n" +
                "to come to the aid of their party. Now is the time for many or all good people to come to the aid of their \n" +
                "party. Now is the time for all good people to come to the aid of their party. Now is the time for all good \n" +
                "Democrats to come to the aid of their party."));
    }
}
