import java.util.Arrays;

/**
 * Created by MingJe on 2015/7/27.
 */
public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] copyPoint = new Point[N];
        int n = 0;
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            points[n] = new Point(x, y);
            n++;
        }
        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            copyPoint[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points, copyPoint[i].SLOPE_ORDER);
            int collinear = 0;
            int startIndex = -1;
            int endIndex = -1;
            double preSlope = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < points.length; j++) {
                double cuSlope = points[j].slopeTo(copyPoint[i]);
                if (cuSlope == Double.NEGATIVE_INFINITY) continue;
                if (preSlope == cuSlope) {
                    collinear++;
                    if (collinear == 1) {
                        startIndex = j;
                    }
                } else {
                    if (collinear >= 4) {
                        endIndex = j;

                        Point[] collinearP = new Point[collinear];
                        for (int k = 0; k < collinear; k++) {
                            collinearP[k] = points[startIndex + k];
                        }
                        Arrays.sort(collinearP);
                        for (int m = 0; m < collinearP.length; m++) {
                            if (m == collinearP.length - 1)
                                StdOut.print(collinearP[m]);
                            else
                                StdOut.print(collinearP[m] + " -> ");
                        }
                    }
                    collinear = 0;
                }
                preSlope = cuSlope;

            }
        }
    }
}
