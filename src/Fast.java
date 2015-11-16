import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        List<List<Point>> allLine = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int m = 0; m < N; m++) {
                if (m < i) points[m] = copyPoints[m];
                else if (m > i) points[m - 1] = copyPoints[m];
            }
            List<Point> line = new ArrayList<>();
            line.add(copyPoints[i]);
            int collinear = 2;
            Arrays.sort(points, 0, N - 1, copyPoints[i].SLOPE_ORDER);
            double preSlope = points[0].slopeTo(copyPoints[i]);
            int startIndex = 0;
            for (int j = 1; j < N - 1; j++) {

                double cuSlope = points[j].slopeTo(copyPoints[i]);
                if (cuSlope == Double.POSITIVE_INFINITY){

                }
                int cmp = Double.compare(cuSlope, preSlope);
                if (cmp == 0) {
                    if (collinear == 2) startIndex = j - 1;
                    collinear++;
                }
                if (cmp != 0 || j == N - 2) {
                    if (collinear > 3) {
                        for (int k = startIndex; k < startIndex + collinear - 1; k++) {
                            line.add(points[k]);
                        }
                        allLine.add(line);
                        line = new ArrayList<>();
                        line.add(copyPoints[i]);
                    }
                    preSlope = cuSlope;
                    collinear = 2;
                }

            }

        }
        for (int i = 0; i < allLine.size(); i++) {
            List<Point> line = allLine.get(i);
            boolean printed = false;
            if (line.get(0).compareTo(line.get(1)) > 0) continue;
            printed = true;
            for (int j = 0; j < line.size() - 1; j++) {
                StdOut.print(line.get(j) + " -> ");
            }
            if (printed) {
                StdOut.println(line.get(line.size() - 1));
                line.get(0).drawTo(line.get(line.size() - 1));
            }
        }
        StdDraw.show(0);
        //StdOut.print(Double.compare(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));


    }
}
            /*Arrays.sort(points, i + 1, N, copyPoints[i].SLOPE_ORDER);
            int collinear = 2;
            Double preSlope = 0.0;
            int startIndex = 0;
            boolean isPrint = false;
            for (int j = 0; j < N; j++) {

                Double cuSlope = copyPoints[i].slopeTo(points[j]);
                if (i == j - 1) {
                    preSlope = cuSlope;
                    continue;
                }
                if (cuSlope.equals(preSlope)) {
                    collinear++;
                    if (collinear == 3) startIndex = j - 1;
                }
                if (!cuSlope.equals(preSlope) || j == N - 1) {
                    if (collinear >= 4) {
                        /*for (int k = 0; k < headPoints.size(); k++) {
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
*/