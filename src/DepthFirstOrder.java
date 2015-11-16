import edu.princeton.cs.algs4.In;

import java.util.Stack;

/**
 * Created by MingJe on 2015/11/14.
 */
public class DepthFirstOrder {

    Stack<Integer> reversePost;
    boolean[] marked;
    protected Graph G;

    public DepthFirstOrder(Graph g) {
        G = g;
        reversePost = new Stack<>();
        marked = new boolean[G.V()];

        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) {
                dfs(i);
            }

        }

    }

    public void dfs(int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(w);
        }
        reversePost.push(v);
    }

    public Stack<Integer> reversePost() {
        return reversePost;
    }

    public static void main(String[] args) {
        Graph G = new Digraph(new In());
        DepthFirstOrder topological = new DepthFirstOrder(G);
        int size = topological.reversePost().size();
        for (int i = 0; i < size; i++) {
            System.out.println(topological.reversePost().pop());
        }
    }
}
