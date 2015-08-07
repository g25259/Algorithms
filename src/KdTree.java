import java.util.ArrayDeque;
import java.util.Queue;

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

    private int nearestVisitedCount = 0;
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
        if (contains(p)) return;
        root = insert(p, root, false);
        size++;
        if (size == 1) {
            root.rect = new RectHV(0, 0, 1, 1);
        }
    }

    private Node insert(Point2D p, Node x, boolean type) {
        if (x == null) return new Node(p);
        if (!type) {
            int cmp = Double.compare(x.p.x(), p.x());
            if (cmp > 0) {
                x.lb = insert(p, x.lb, true);
                x.lb.rect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else if (cmp <= 0) {
                x.rt = insert(p, x.rt, true);
                x.rt.rect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
            }
        } else {
            int cmp = Double.compare(x.p.y(), p.y());
            if (cmp > 0) {
                x.lb = insert(p, x.lb, false);
                x.lb.rect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            } else if (cmp <= 0) {
                x.rt = insert(p, x.rt, false);
                x.rt.rect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
            }
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException("Contains Null point");
        return contains(root, p, false);
    }

    private boolean contains(Node x, Point2D p, boolean type) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        if (!type) {
            int cmp = Double.compare(x.p.x(), p.x());
            if (cmp > 0) return contains(x.lb, p, !type);
            else return contains(x.rt, p, !type);
        } else {
            int cmp = Double.compare(x.p.y(), p.y());
            if (cmp > 0) return contains(x.lb, p, !type);
            else return contains(x.rt, p, !type);
        }
    }

    // draw all points to standard draw
    public void draw() {
        iterDraw(root, root, false);

    }

    private void iterDraw(Node x, Node p, boolean type) {
        if (x == null) return;
        StdDraw.setPenRadius(.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();
        if (!type) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());

        } else {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());

        }


        iterDraw(x.lb, x, !type);
        iterDraw(x.rt, x, !type);
        return;

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException("Range Null rect");
        Queue<Point2D> q = new ArrayDeque<>();
        range(root, rect, q, false);
        return q;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q, boolean type) {
        if (x == null) return;
        if (rect.contains(x.p))
            q.add(x.p);
        if (!type) {
            int cmp = Double.compare(x.p.x(), rect.xmin());
            if (cmp > 0) range(x.lb, rect, q, !type);
            cmp = Double.compare(x.p.x(), rect.xmax());
            if (cmp <= 0) range(x.rt, rect, q, !type);
        } else {
            int cmp = Double.compare(x.p.y(), rect.ymin());
            if (cmp > 0) range(x.lb, rect, q, !type);
            cmp = Double.compare(x.p.y(), rect.ymax());
            if (cmp <= 0) range(x.rt, rect, q, !type);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException("Nearest Null point");
        if (isEmpty()) return null;
        Node nearest = nearest(root, p, root, false);
        return nearest.p;
    }

    private Node nearest(Node x, Point2D p, Node candidate, boolean type) {
        nearestVisitedCount++;
        if (x == null) return candidate;
        double nearDistance = p.distanceSquaredTo(candidate.p);
        double tmp = x.p.distanceSquaredTo(p);
        if (tmp < nearDistance)
            candidate = x;
        int cmp;
        if (!type) {
            cmp = Double.compare(x.p.x(), p.x());
        } else {
            cmp = Double.compare(x.p.y(), p.y());
        }
        if (cmp <= 0) {
            candidate = nearest(x.rt, p, candidate, !type);
            nearDistance = x.p.distanceSquaredTo(candidate.p);
            if (x.lb == null) return candidate;
            double distToRect = x.lb.rect.distanceSquaredTo(p);
            if (distToRect < nearDistance) candidate = nearest(x.lb, p, candidate, type);
        } else {
            candidate = nearest(x.lb, p, candidate, !type);
            nearDistance = x.p.distanceSquaredTo(candidate.p);
            if (x.rt == null) return candidate;
            double distToRect = x.rt.rect.distanceSquaredTo(p);
            if (distToRect < nearDistance) candidate = nearest(x.lb, p, candidate, type);

        }


        return candidate;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        KdTree kdTree = new KdTree();
        StringBuilder stringBuilder = new StringBuilder();
        while (in.hasNextLine()) {
            stringBuilder.append(in.readLine());
            stringBuilder.append(" ");
        }
        String s = stringBuilder.toString();
        String[] ss = s.split("\\s+");
        for (int i = 0; i < ss.length; i += 2) {
            kdTree.insert(new Point2D(Double.valueOf(ss[i]), Double.valueOf(ss[i + 1])));
        }
        kdTree.draw();
        StdDraw.show();
        System.out.println(kdTree.contains(new Point2D(0.500000, 1.000000)));
        System.out.println(kdTree.contains(new Point2D(0.589, 0.689)));
        System.out.println(kdTree.isEmpty());
        //System.out.println(kdTree.nearest(new Point2D(0.2067778, 0.095448)));
        RectHV rectHV = new RectHV(0.0, 0.5, 0.5, 1.00);
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.GREEN);
        rectHV.draw();
        kdTree.range(rectHV).forEach(p -> StdOut.print(p + " "));
        System.out.println();
        Point2D p = new Point2D(0.81, 0.30);
        StdDraw.setPenRadius(.01);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        p.draw();
        Point2D near = kdTree.nearest(p);
        p.drawTo(near);
        System.out.println(near);
        System.out.println(kdTree.nearestVisitedCount);
    }
}
