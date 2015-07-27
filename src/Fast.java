import java.util.Arrays;

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

        for (int i = 0; i < N; i++) {
            Arrays.sort(points);
            Arrays.sort(points, i + 1, N, copyPoints[i].SLOPE_ORDER);
            int collinear = 2;
            Double preSlope = 0.0;
            int startIndex = 0;
            for (int j = i + 1; j < N; j++) {
                Double cuSlope = copyPoints[i].slopeTo(points[j]);
                if (i == j - 1) {
                    preSlope = cuSlope;
                    continue;
                }
                if (cuSlope.equals(preSlope)) {
                    collinear++;
                    if(collinear == 3) startIndex = i;

                } else {
                    if( collinear >= 4)
                    {
                        Point[] collinearPoints = new Point[collinear];
                        for (int k = 0; k < collinear; k++) {
                            collinearPoints[k] = points[startIndex + k];
                        }
                        collinearPoints[0].drawTo(collinearPoints[collinearPoints.length]);
                    } else if (collinear == 3){
                        collinear = 2;
                    }

                }
                preSlope = cuSlope;
            }
            if( collinear >= 4)
            {
                StdOut.print(collinear);
            }

        }

    }
}
