import java.lang.reflect.Array;
import java.util.*;


/**
 * Created by MingJe on 2015/7/27.
 */
public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] copyPoints = new Point[N];
        int n = 0;
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            points[n] = new Point(x, y);
            points[n].draw();
            n++;
        }
        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            copyPoints[i] = points[i];
        }
        List<Point> headPoints = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int m = 0; m < N; m++) {
                points[m] = copyPoints[m];
            }
            Arrays.sort(points, i + 1, N, copyPoints[i].SLOPE_ORDER);
            int collinear = 2;
            Double preSlope = 0.0;
            int startIndex = 0;
            Point lastPoint = copyPoints[i];
            boolean isPrint = false;
            for (int j = i + 1; j < N; j++) {

                Double cuSlope = copyPoints[i].slopeTo(points[j]);
                if (i == j - 1) {
                    preSlope = cuSlope;
                    continue;
                }
                if (cuSlope.equals(preSlope)) {
                    collinear++;
                    //if(points[j].compareTo(copyPoints[i]) <= 0) lastPoint = points[j];
                    if (collinear == 3) startIndex = j - 1;
                }
                if (!cuSlope.equals(preSlope) || j == N - 1) {
                    if (collinear >= 4) {
                        for (int k = 0; k < headPoints.size(); k++) {
                            double printedSlope = headPoints.get(k).slopeTo(copyPoints[i]);
                            if (printedSlope == preSlope) isPrint = true;
                        }
                        if (isPrint) isPrint = false;
                        else {
                            Point[] collinearPoints = new Point[collinear];
                            collinearPoints[0] = copyPoints[i];
                            for (int k = 1; k < collinear; k++) {
                                collinearPoints[k] = points[startIndex + k - 1];
                            }
                            Arrays.sort(collinearPoints);
                            collinearPoints[0].drawTo(collinearPoints[collinearPoints.length - 1]);
                            for (int m = 0; m < collinearPoints.length; m++) {
                                if (m == collinearPoints.length - 1)
                                    StdOut.print(collinearPoints[m]);
                                else
                                    StdOut.print(collinearPoints[m] + " -> ");
                            }
                            headPoints.add(collinearPoints[0]);
                            StdOut.println();
                        }
                        collinear = 2;
                    } else if (collinear == 3) {
                        collinear = 2;
                    }

                }
                preSlope = cuSlope;
            }
        }
        StdDraw.show(0);
    }
}
