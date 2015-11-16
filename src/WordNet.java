import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MingJe on 2015/11/15.
 */
public class WordNet {

    private List<Synset> synsetList;
    private SET<String> nouns;
    private Digraph hypernymsRelation;
    private SAP sap;

    //Class for Synset
    private class Synset {
        private int id;
        private List<String> synonym;


        public Synset(int id, String[] synonym) {
            this.id = id;
            this.synonym = new ArrayList<>();
            for (int i = 0; i < synonym.length; i++) {
                this.synonym.add(synonym[i]);
            }

        }

    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new java.lang.NullPointerException();
        synsetList = new ArrayList<>();
        nouns = new SET<>();
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            synsetList.add(new Synset(Integer.parseInt(fields[0]),
                    fields = fields[1].split(" ")));

            for (int i = 0; i < fields.length; i++) {
                nouns.add(fields[i]);
            }
        }

        hypernymsRelation = new Digraph(synsetList.size());
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            for (int i = 1; i < fields.length; i++) {
                hypernymsRelation.addEdge(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[i]));
            }
        }
        DirectedCycle cycle = new DirectedCycle(hypernymsRelation);
        if (cycle.hasCycle()) throw new java.lang.IllegalArgumentException();
        int root = 0;
        for (int i = 0; i < hypernymsRelation.V(); i++) {
            if (hypernymsRelation.outdegree(i) == 0 ) ++root;
        }
        if (root != 1) throw new java.lang.IllegalArgumentException();
        sap = new SAP(hypernymsRelation);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new java.lang.NullPointerException();
        return nouns.contains(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new java.lang.NullPointerException();
        if (!nouns.contains(nounA) || !nouns.contains(nounB)) throw new java.lang.IllegalArgumentException();

        List<Integer> setA = new ArrayList<>();
        List<Integer> setB = new ArrayList<>();
        for (int i = 0; i < synsetList.size(); i++) {
            Synset synset = synsetList.get(i);
            if (synset.synonym.contains(nounA))
                setA.add(synset.id);
            if (synset.synonym.contains(nounB))
                setB.add(synset.id);
        }
        int distance = sap.length(setA, setB);


        return distance;
    }

    // a synset (second field of synsetList.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new java.lang.NullPointerException();
        if (!nouns.contains(nounA) || !nouns.contains(nounB)) throw new java.lang.IllegalArgumentException();

        List<Integer> setA = new ArrayList<>();
        List<Integer> setB = new ArrayList<>();
        for (int i = 0; i < synsetList.size(); i++) {
            Synset synset = synsetList.get(i);
            if (synset.synonym.contains(nounA))
                setA.add(synset.id);
            if (synset.synonym.contains(nounB))
                setB.add(synset.id);
        }

        StringBuilder sb = new StringBuilder();
        List<String> synset = synsetList.get(sap.ancestor(setA, setB)).synonym;

        for (int i = 0; i < synset.size(); i++) {
            sb.append(synset.get(i) + " ");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        wordnet.sap("horse", "hood");
    }
}
