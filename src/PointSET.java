import java.util.*;
import java.util.Queue;

/**
 * Created by MingJe on 2015/8/7.
 */
public class PointSET {
    private final TreeSet<Point2D> set;

    //private final Comparator<Point2D> point2DComparator = (Point2D p1, Point2D p2) -> p1.compareTo(p2);
    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.size() == 0;
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException("Insert null point");
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException("Contains Null point");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        set.forEach(p -> draw());
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new ArrayDeque<>();
        set.forEach(p -> {
            if (rect.contains(p)) q.add(p);
        });
        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Point2D nearest = Collections.max(set, (p1, p2)->{
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
        while (in.hasNextLine()){
            pointSET.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        pointSET.draw();
    }
}

