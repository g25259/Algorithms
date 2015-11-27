import edu.princeton.cs.algs4.In;

/**
 * Created by MingJe on 2015/11/23.
 */
public class EdgeWeightedDigraph extends EdgeWeightedGraph {
    public EdgeWeightedDigraph(int V) {
        super(V);
    }

    public EdgeWeightedDigraph(In in) {
        super(in);
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
    }

}
