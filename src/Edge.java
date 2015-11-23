/**
 * Created by g2525_000 on 2015/11/23.
 */
public class Edge implements Comparable<Edge> {

    private int V, W;
    private double weight;
    //Create a weighted edge V-W
    public Edge(int v, int w, double weight) {
        V = v;
        W = w;
        this.weight = weight;
    }
    //either endpoint
    public int either() {
        return V;
    }
    //the endpoint that's not V
    public int other(int v) {
        if (V == v) {
            return W;
        } else if (W == v)
            return V;
        else
            return -1;
    }


    public double weight(){ {
        return weight;
    }
    }
    @Override
    public String toString(){
        return V + " " + W + " " + weight;
    }
    @Override
    public int compareTo(Edge that) {
        if (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return 1;
        return 0;
    }
}
