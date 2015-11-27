import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by MingJe on 2015/11/24.
 */
public class DijkstraSP implements ShortestPath{

    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {

        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(G.V());
        for (int v = 0; v < distTo.length; v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v)) {
                relax((DirectedEdge) e, pq);
            }
        }

    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public boolean hasPathTo(int v) {
        if (edgeTo[v] != null) return true;
        return false;
    }

    private void relax(DirectedEdge e, IndexMinPQ pq) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight) {
            distTo[w] = distTo[v] + e.weight;
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In());
        DijkstraSP sp = new DijkstraSP(G, 0);
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%d to %d (%.2f): ", 0, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v))
                StdOut.print(e + "  ");
            StdOut.println();
        }
    }

}
