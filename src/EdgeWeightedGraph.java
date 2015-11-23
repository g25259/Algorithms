import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Created by g2525_000 on 2015/11/23.
 */
public class EdgeWeightedGraph {
    protected Bag<Edge>[] adj;
    protected Bag<Edge> edges;
    protected final int V;
    protected int degree;

    public EdgeWeightedGraph(In in) {
        V = Integer.parseInt(in.readLine());
        edges = new Bag<>();
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag();
        }
        int E = Integer.parseInt(in.readLine());
        for (int i = 0; i < E; i++) {
            String line = in.readLine();
            String[] vertex = line.split(" ");
            Edge e = new Edge(Integer.parseInt(vertex[0]),
                    Integer.parseInt(vertex[1]), Double.parseDouble(vertex[2]));
            addEdge(e);
        }
    }

    public EdgeWeightedGraph(int V) {
        this.V = V;
        edges = new Bag<>();
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag();
        }

    }
    public Bag<Edge> edges() {
        return edges;
    }
    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        degree += 2;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return degree /= 2;
    }

}
