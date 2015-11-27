import edu.princeton.cs.algs4.*;

/**
 * Created by MingJe on 2015/11/24.
 */
public class AcyclicSP implements ShortestPath {

    private edu.princeton.cs.algs4.DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(edu.princeton.cs.algs4.EdgeWeightedDigraph G, int s) {
        edgeTo = new edu.princeton.cs.algs4.DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        Topological topological = new Topological(G);
        for (int v: topological.order()) {
            for (edu.princeton.cs.algs4.DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(edu.princeton.cs.algs4.DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }

    }

    @Override
    public double distTo(int v) {
        return 0;
    }

    @Override
    public Iterable<DirectedEdge> pathTo(int v) {
        return null;
    }

    @Override
    public boolean hasPathTo(int v) {
        return false;
    }
}
