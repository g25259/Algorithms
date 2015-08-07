import java.util.*;

/**
 * Created by g2525_000 on 2015/8/7.
 */
public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        Node(Point2D p) {
            this.p = p;
        }
    }

    private int size;
    //private final Comparator<Point2D> kdTreeComparator = (Point2D p1, Point2D p2) -> p1.compareTo(p2);
    // construct an empty set of points
    private Node root;


    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException("Insert null point");
        root = insert(p, root, false);
        size++;
    }

    private Node insert(Point2D p, Node x, boolean type) {
        if (x == null) return new Node(p);
        if (!type) {
            int cmp = Double.compare(x.p.x(), p.x());
            if (cmp < 0) x.lb = insert(p, x.lb, true);
            else if (cmp >= 0) x.rt = insert(p, x.rt, true);
        } else {
            int cmp = Double.compare(x.p.y(), p.y());
            if (cmp < 0) x.lb = insert(p, x.lb, true);
            else if (cmp >= 0) x.rt = insert(p, x.rt, true);
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException("Contains Null point");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        set.forEach(p -> p.draw());
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        java.util.Queue<Point2D> q = new ArrayDeque<>();
        set.forEach(p -> {
            if (rect.contains(p)) q.add(p);
        });
        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Point2D nearest = Collections.min(set, (p1, p2) -> {
            double distanceOne = p1.distanceTo(p);
            double distanceTwo = p2.distanceTo(p);
            if (distanceOne < distanceTwo) return -1;
            else if (distanceOne == distanceTwo) return 0;
            else return 1;
        });

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        PointSET pointSET = new PointSET();
        while (in.hasNextLine()) {
            pointSET.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        pointSET.draw();
        pointSET.size();
        System.out.println(pointSET.contains(new Point2D(0.500000, 1.000000)));
        System.out.println(pointSET.contains(new Point2D(0.589, 0.689)));
        System.out.println(pointSET.isEmpty());
        System.out.println(pointSET.nearest(new Point2D(0.2067778, 0.095448)));
        RectHV rectHV = new RectHV(0.0, 0.5, 0.5, 1.00);
        rectHV.draw();
        pointSET.range(rectHV).forEach(p -> StdOut.print(p + " "));
        System.out.println();
    }
}
