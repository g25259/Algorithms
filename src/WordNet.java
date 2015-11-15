
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by MingJe on 2015/11/15.
 */
public class WordNet {

    List<Synset> synsetList;
    List<String> nouns;
    Digraph hypernymsRelation;
    SAP sap ;
    //Class for Synset
    private class Synset {
        int id;
        String synonym;
        String gloss;

        public Synset(int id, String synonym, String gloss) {
            this.id = id;
            this.synonym = synonym;
            this.gloss = gloss;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new java.lang.NullPointerException();
        synsetList = new ArrayList<>();
        nouns = new ArrayList<>();
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            synsetList.add(new Synset(Integer.parseInt(fields[0]),
                    fields[1], fields[2]));
            fields = fields[1].split(" ");
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
        int idxA = -1, idxB = -1;
        for (int i = 0; i < synsetList.size(); i++) {
            Synset synset = synsetList.get(i);
            if (synset.synonym.equals(nounA))
                idxA = i;
            if (synset.synonym.equals(nounB))
                idxB = i;
        }
        return sap.length(idxA, idxB);
    }

    // a synset (second field of synsetList.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new java.lang.NullPointerException();
        int idxA = -1, idxB = -1;
        for (int i = 0; i < synsetList.size(); i++) {
            Synset synset = synsetList.get(i);
            if (synset.synonym.equals(nounA))
                idxA = i;
            if (synset.synonym.equals(nounB))
                idxB = i;
        }

        return synsetList.get(sap.ancestor(idxA, idxB)).synonym;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
