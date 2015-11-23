import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

/**
 * Created by g2525_000 on 2015/11/23.
 */
public class PrimMST implements MST {
    private double mstWeight;
    private Queue<Edge> mst = new Queue<>();
    boolean[] marked;
    private EdgeWeightedGraph G;


    public PrimMST(EdgeWeightedGraph G) {
        this.G = G;
        MinPQ<Edge> pq = new MinPQ<>();
        mst = new Queue<>();
        marked = new boolean[G.V()];
        visit(G, 0, pq);

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            if (!marked[v]) visit(G, v, pq);
            if (!marked[w]) visit(G, w, pq);
        }
    }

    private void visit(EdgeWeightedGraph g, int v, MinPQ<Edge> pq) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)])
                pq.insert(e);
        }
    }


    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return mstWeight;
    }
}
