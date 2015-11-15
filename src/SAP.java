import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Digraph;

import java.util.*;


/**
 * Created by MingJe on 2015/11/15.
 */
public class SAP {
    private class BreadthFirstPaths implements Comparable<BreadthFirstPaths> {
        private boolean[][] marked;
        private int[][] distTo;
        private boolean []finished;
        public BreadthFirstPaths() {
            marked = new boolean[G.V()][G.V()];
            distTo = new int[G.V()][G.V()];
            finished = new boolean[G.V()];
            //bfs();
        }



        private void bfs(int s) {
            if (finished[s]) return;
            finished[s] = true;
            java.util.Queue<Integer> queue = new ArrayDeque<>();
            queue.add(s);
            marked[s][s] = true;
            distTo[s][s] = 0;
            while (!queue.isEmpty()) {
                int w = queue.remove();

                for (int x : G.adj(w)) {
                    if (!marked[s][x]) {
                        queue.add(x);
                        marked[s][x] = true;
                        distTo[s][x] = distTo[s][w] + 1;
                    }
                }
            }

        }

    }

    private Digraph G;
    private Map<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> sap;
    private Map<Integer, BreadthFirstPaths> bfsList;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new java.lang.NullPointerException();
        sap = new HashMap<>();
        this.G = G;
        bfsList = new HashMap<>();

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v > G.V() - 1 || w > G.V() - 1) throw new java.lang.ArrayIndexOutOfBoundsException();
        Map.Entry<Integer, Integer> pair = new AbstractMap.SimpleEntry<Integer, Integer>(v, w);
        Map.Entry<Integer, Integer> result = sap.get(pair);
        if (result != null) return result.getValue();

        BreadthFirstPaths BFSV = bfsList.get(v);
        if (BFSV == null) {
            BFSV = new BreadthFirstPaths(v);
            bfsList.put(v, BFSV);

        }

        BreadthFirstPaths BFSW = bfsList.get(w);
        if (BFSW == null) {
            BFSW = new BreadthFirstPaths(w);
            bfsList.put(w, BFSW);
        }

        int min = -1;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {

            int distV = BFSV.distTo[i];
            int distW = BFSW.distTo[i];
            if ((distV == 0 && v != i) || (distW == 0 && w != i)) continue;
            int length = distV + distW;

            if (length < min || min == -1) {
                min = length;
                ancestor = i;
            }

        }
        sap.put(pair, new AbstractMap.SimpleEntry<>(ancestor, min));
        return min;
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path

    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v > G.V() - 1 || w > G.V() - 1) throw new java.lang.ArrayIndexOutOfBoundsException();
        Map.Entry<Integer, Integer> pair = new AbstractMap.SimpleEntry<Integer, Integer>(v, w);
        Map.Entry<Integer, Integer> result = sap.get(pair);
        if (result != null) return result.getKey();
        length(v, w);
        return sap.get(pair).getKey();

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new java.lang.NullPointerException();
        int min = -1;
        for (int i : v) {
            for (int j : w) {
                int length = length(i, j);
                if (min == -1 || length < min)
                    min = length;
            }
        }
        return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new java.lang.NullPointerException();
        int min = -1;
        int ancestor = -1;
        for (int i : v) {
            for (int j : w) {
                int length = length(i, j);
                if (min == -1 || length < min) {
                    min = length;
                    ancestor = ancestor(i, j);
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
