/**
 * Created by g2525_000 on 2015/12/17.
 */
public class BFSS {
    public static int search(String txt, String pat) {
        int M = pat.length();
        int N = txt.length();

        for (int i = 0; i < N - M; i++) {
            int j = 0;
            for (j = 0; j < M; j++) {
                if (pat.charAt(j) != txt.charAt(i + j))
                    break;
            }
            if (j == M) return i;
        }
        return N;
    }

    public static void main(String[] args) {
        System.out.println(search("Now is the time for all people to come to the aid of their party. Now is the time for all good people to \n" +
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
                "Democrats to come to the aid of their party.", "attack at dawn"));
    }

}
