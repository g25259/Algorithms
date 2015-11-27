/**
 * Created by MingJe on 2015/11/23.
 */
public class DirectedEdge extends Edge{
    private int v, w;
    double weight;

    public DirectedEdge(int v, int w, double weight) {
        super(v, w, weight);
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

}
