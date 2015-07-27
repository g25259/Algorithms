import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by MingJe on 2015/7/26.
 */
public class Brute {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        int n = 0;
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            points[n] = new Point(x, y);
            n++;
        }
        Arrays.sort(points);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double slope12 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < N; k++) {
                    double slope13 = points[i].slopeTo(points[k]);
                    if (slope12 != slope13) break;
                    for (int l = k + 1; l < N; l++) {
                        double slope14 = points[i].slopeTo(points[l]);
                        if (slope12 == slope14) {
                            Point[] collinear = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(collinear);
                            for (int m = 0; m < collinear.length; m++) {
                                if (m == collinear.length - 1)
                                    StdOut.print(collinear[m]);
                                else
                                    StdOut.print(collinear[m] + " -> ");
                            }
                            StdOut.println();
                        }
                    }
                }
            }
        }

    }
}
