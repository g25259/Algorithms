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

        for (int i = 0; i < points.length - 4; i++) {
            Arrays.sort(points);
            Arrays.sort(points, i + 1, N - 1, copyPoint[i].SLOPE_ORDER);
            int collinear = 1;
            int startIndex = -1;
            Double preSlope = copyPoint[i].slopeTo(points[i+1]);
            for (int j = i + 2; j < points.length; j++) {
                Double cuSlope = points[j].slopeTo(copyPoint[i]);
                if (preSlope.equals(cuSlope)) {
                    collinear++;
                    if (collinear == 2) {
                        startIndex = j - 1;
                    }
                } else {
                    if (collinear >= 4) {
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
                    collinear = 1;
                }
                preSlope = cuSlope;

            }
            if (collinear >= 4) {
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
        }
    }
}
