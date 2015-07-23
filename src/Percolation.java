/**
 * Created by MingJe on 2015/7/12.
 */

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int top = 0;
    private int bottom;
    private int size;

    /**
     * Initialize grid
     *
     * @param N the size of  grid
     */
    public Percolation(int N) {                // create N-by-N grid, with all sites blocked
        if (N < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];
        bottom = size * size + 1;

    }

    /**
     * Map 2D coordinates to 1D coordinates.
     *
     * @param i index of row
     * @param j index of  column
     * @return corresponding index
     */
    private int xyTo21D(int i, int j) {
        return size * (i - 1) + j - 1;
    }

    /**
     * Open site (row i, column j) if it is not open already
     *
     * @param i index of row
     * @param j index of  column
     */
    public void open(int i, int j) {
        validate(i, j);
        if (isOpen(i, j))
            return;

        if (i == 1) {
            uf.union(top, j-1);
        } else if (i == size) {
            uf.union(bottom, xyTo21D(size, j));
        }
        if (j > 1 && isOpen(i, j - 1))
            uf.union(xyTo21D(i, j), xyTo21D(i, j - 1));
        if (j < size && isOpen(i, j + 1))
            uf.union(xyTo21D(i, j), xyTo21D(i, j + 1));
        if (i > 1 && isOpen(i - 1, j))
            uf.union(xyTo21D(i, j), xyTo21D(i - 1, j));
        if (i < size && isOpen(i + 1, j))
            uf.union(xyTo21D(i, j), xyTo21D(i + 1, j));

        grid[i - 1][j - 1] = true;

    }

    /**
     * Determine whether index is legal range
     *
     * @param i index of row
     * @param j index of column
     */
    private void validate(int i, int j) {
        if (i > size || i < 1 || j > size || j < 1) {
            throw new java.lang.IndexOutOfBoundsException("index not in legal range");
        }
    }

    /**
     * Is site (row i, column j) open?
     *
     * @param i index of row
     * @param j index of column
     * @return open or not
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return grid[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     *
     * @param i index of row
     * @param j index of column
     * @return full or not
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        if (uf.connected(top, xyTo21D(i, j)))
            return true;
        return false;
    }

    /**
     * Does the system percolate?
     *
     * @return percolate or not
     */
    public boolean percolates() {
        if (uf.connected(top, bottom))
            return true;
        return false;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        System.out.println(p.uf.connected(p.xyTo21D(1,1),p.xyTo21D(1,2)));
        p.open(1, 1);
        System.out.println(p.uf.connected(p.xyTo21D(1, 1), p.xyTo21D(1, 2)));
        p.open(1, 2);
        System.out.println(p.uf.connected(p.xyTo21D(1,1),p.xyTo21D(1,2)));

    }
}

