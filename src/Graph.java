/**
 * Created by MingJe on 2015/11/12.
 */
public class Graph {
    protected final int V;
    protected Bag<Integer>[] adj;
    protected int degree;

    public Graph(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag();
        }
    }

    public Graph(In in) {
        V = Integer.parseInt(in.readLine());
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag();
        }
        int E = Integer.parseInt(in.readLine());
        for (int i = 0; i < E; i++) {
            String line = in.readLine();
            String[] vertex = line.split(" ");
            addEdge(Integer.parseInt(vertex[0]),
                    Integer.parseInt(vertex[1]));
        }


    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        degree += 2;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return degree /= 2;
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < adj.length; i++) {
            final int ai = i;
            adj[i].forEach((s) -> stringBuilder.append(ai + "->" + s + "\n"));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Graph graph = new Graph(new In());
        System.out.println(graph);
    }

}
