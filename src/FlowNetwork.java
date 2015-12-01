import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Created by MingJe on 2015/12/1.
 */
public class FlowNetwork {
    private int v;
    private Bag<FlowEdge>[] adj;
    private int flow;
    private int e;
    public FlowNetwork(int v) {
        this.v = v;
        adj = new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public FlowNetwork(In in) {
        v = Integer.parseInt(in.readLine());
        adj = new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
        int e = Integer.parseInt(in.readLine());
        for (int i = 0; i < e; i++) {
            String line = in.readLine();
            String[] vertex = line.split(" ");
            int v = Integer.parseInt(vertex[0]);
            int w = Integer.parseInt(vertex[1]);
            int capacity = Integer.parseInt(vertex[2]);
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    public void addEdge(FlowEdge e) {
        adj[e.from()].add(e);
        adj[e.to()].add(e);
        this.e++;
    }

    public int V() {
        return v;
    }

    public int E() {
        return e;
    }
    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }
}
