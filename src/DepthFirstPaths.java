import java.util.*;

/**
 * Created by MingJe on 2015/11/12.
 */
public class DepthFirstPaths implements Paths {
    protected final Graph G;
    protected final int s;
    protected boolean marked[];
    protected int edgeTo[];

    public DepthFirstPaths(Graph g, int s) {
        G = g;
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        Arrays.fill(edgeTo, -1);
        dfs(s);
    }

    private void dfs(int v) {
        marked[v] = true;

        G.adj(v).forEach(
                (w) -> {
                    if (!marked[w]) {
                        dfs(w);
                        edgeTo[w] = v;
                    }
                }
        );

    }

    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable pathTo(int v) {
        if (!hasPathTo(s)) return null;
        Stack<Integer> paths = new Stack<>();
        int w = v;
        while (w != s) {
            paths.push(w);
            w = edgeTo[w];
        }
        paths.push(s);
        return paths;
    }

    public static void main(String[] args) {
        Graph G = new Digraph(new In());
        DepthFirstPaths dfs = new DepthFirstPaths(G, 0);
    }
}
