import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

/**
 * Created by g2525_000 on 2015/11/23.
 */
public class KruskalMST implements MST {
    private double mstWeight;
    private Queue<Edge> mst = new Queue<>();
    private EdgeWeightedGraph G;

    public KruskalMST(EdgeWeightedGraph G) {
        this.G = G;
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges) {
            pq.insert(e);
        }

        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
                mstWeight += e.weight();
            }
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
