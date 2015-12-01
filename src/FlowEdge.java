/**
 * Created by MingJe on 2015/12/1.
 */
public class FlowEdge {
    private int v, w;
    private double flow;
    private double capacity;

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new java.lang.IllegalArgumentException();
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v) return flow;
        else if (vertex == w) return capacity - flow;
        else throw new java.lang.IllegalArgumentException();
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == v) flow -= delta;
        else if (vertex == w) flow += delta;
        else throw new java.lang.IllegalArgumentException();
    }

}
