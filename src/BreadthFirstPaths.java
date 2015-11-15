import java.util.Arrays;

/**
 * Created by g2525_000 on 2015/11/13.
 */
public class BreadthFirstPaths implements Paths {
    private final Graph G;
    private final int s;
    private boolean marked[];
    private int edgeTo[];
    private int distTo[];
    public BreadthFirstPaths(Graph g, int v) {
        G = g;
        s = v;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        Arrays.fill(edgeTo, -1);
        bfs();

    }

    private void bfs() {
        Deque<Integer> queue = new Deque<>();
        queue.addLast(s);
        marked[s] = true;
        distTo[s] = 0;
        int dist = 0;
        while (!queue.isEmpty()) {
            int w = queue.removeFirst();

            for (int x : G.adj(w)) {
                if (!marked[x]) {
                    queue.addLast(x);
                    marked[x] = true;
                    edgeTo[x] = w;
                    distTo[x] = distTo[w] + 1;
                }
            }
        }

    }

    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable pathTo(int v) {
        if (hasPathTo(v)) return null;
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
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, 0);
    }
}
