import java.util.*;

/**
 * Created by MingJe on 2015/11/12.
 */
public class DepthFirstPaths implements Paths {
    private final Graph G;
    private final int s;
    private boolean marked[];
    private int edgeTo[];

    public DepthFirstPaths(Graph g, int s) {
        G = g;
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
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

        return null;
    }
}
