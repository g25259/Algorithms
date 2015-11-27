
/**
 * Created by MingJe on 2015/11/24.
 */
public interface ShortestPath {

    public double distTo(int v);

    public Iterable<DirectedEdge> pathTo(int v);

    public boolean hasPathTo(int v);

}
