/**
 * Created by g2525_000 on 2015/11/13.
 */
public class Digraph extends Graph {

    public Digraph(int V) {
        super(V);
    }

    public Digraph(In in) {
        super(in);
    }

    @Override
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(new In());
        System.out.println(G);
    }

}
