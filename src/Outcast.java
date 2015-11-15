import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by MingJe on 2015/11/15.
 */
public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = -1;
        int dist;
        String outcast = null;
        for (int i = 0; i < nouns.length; i++) {
            dist = 0;

            for (int j = 0; j < nouns.length; j++) {
                dist += wordNet.distance(nouns[i], nouns[j]);
            }
            if (max < dist) {
                max = dist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}

