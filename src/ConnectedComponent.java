/**
 * Created by g2525_000 on 2015/11/13.
 */
public class ConnectedComponent {

    private final Graph G;
    private int count;
    private int[] id;
    private boolean[] marked;

    public ConnectedComponent(Graph g) {
        G = g;
        id = new int[G.V()];
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) {
                dfs(i);
                count++;
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    private void dfs(int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(w);
        }
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In());
        ConnectedComponent cc = new ConnectedComponent(G);

    }

}
