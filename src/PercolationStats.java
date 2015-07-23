/**
 * Created by MingJe on 2015/7/13.
 */
public class PercolationStats {


    private int experimentTimes;
    private double[] fractions;

    /**
     * Perform T independent experiments on an N-by-N grid.
     *
     * @param N size of grid
     * @param T number of experiment times
     */
    public PercolationStats(final int N, final int T) {
        if (N < 1 || T < 1) {
            throw new java.lang.IllegalArgumentException("N or T should larger than 0");
        }
        this.experimentTimes = T;
        fractions = new double[T];
        int randomIndexOfRow = 0;
        int randomIndexOfCol = 0;
        int fraction = 0;
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            fraction = 0;
            while (!percolation.percolates()) {
                randomIndexOfRow = StdRandom.uniform(1, N + 1);
                randomIndexOfCol = StdRandom.uniform(1, N + 1);
                if (!percolation.isOpen(randomIndexOfRow, randomIndexOfCol)) {
                    percolation.open(randomIndexOfRow, randomIndexOfCol);
                    fraction++;
                }
            }
            fractions[i] = (double) fraction / (N * N);
        }
    }

    /**
     * Sample mean of percolation threshold.
     *
     * @return Mean of percolation threshold.
     */
    public double mean() {

        return StdStats.mean(fractions);
    }

    /**
     * Sample standard deviation of percolation threshold.
     *
     * @return Standard deviation of percolation threshold.
     */
    public double stddev() {

        return StdStats.stddev(fractions);
    }

    /**
     * Low  endpoint of 95% confidence interval.
     *
     * @return  Low  endpoint of 95% confidence interval.
     */
    public double confidenceLo() {

        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentTimes));
    }

    /**
     * High endpoint of 95% confidence interval.
     *
     * @return High endpoint of 95% confidence interval.
     */
    public double confidenceHi() {

        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentTimes));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
