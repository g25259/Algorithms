import java.util.*;

/**
 * Created by g2525_000 on 2015/7/31.
 */
public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    final int[] blocks;
    private final int N;
    private final int hammingDistance;
    private final int manhattanDistance;
    private final int blankIndex;

    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N * N];
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[count++] = blocks[i][j];
            }
        }
        hammingDistance = initHamming();
        manhattanDistance = initManhattan();
        blankIndex = findBlank();
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    private int findBlank() {
        for (int i = 0; i < N * N; i++) {
            if (blocks[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingDistance;
    }

    private int initHamming() {
        int hammingDistance = 0;
        for (int i = 0; i < N * N; i++) {
            if (blocks[i] == 0) continue;
            if (blocks[i] != (i + 1))
                hammingDistance++;

        }
        return hammingDistance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanDistance;
    }

    private int initManhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < N * N; i++) {
            if (blocks[i] == 0) continue;
            if (blocks[i] != i)
                manhattanDistance += Math.abs(i - blocks[i]);

        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int count = 1;
        for (int i = 0; i < N * N; i++) {
            if (blocks[i] == 0) continue;
            if (blocks[i] != count) return false;
        }
        return true;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] twinBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(blocks, i * N, twinBlocks[i], 0, N);
        }

        int row = 0;
        while (true) {
            if (twinBlocks[row][0] != 0 && twinBlocks[row][1] != 0) {
                int tmp = twinBlocks[row][0];
                twinBlocks[row][0] = twinBlocks[row][1];
                twinBlocks[row][1] = tmp;
                return new Board(twinBlocks);
            }
            row++;

        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != Board.class) return false;
        if (this == (Board) y) return true;
        if (N != ((Board) y).dimension()) return false;
        if (manhattanDistance != ((Board) y).manhattan()) return false;
        if (hammingDistance != ((Board) y).hamming()) return false;
        for (int i = 0; i < N * N; i++) {
            if (blocks[i] != ((Board) y).blocks[i]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        int[][] twinBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(blocks, i * N, twinBlocks[i], 0, N);
        }
        int row = blankIndex / N;
        int col = blankIndex % N;
        if (col != 0) {
            swap(twinBlocks, row, col - 1, col, false);
            neighbors.enqueue(new Board(twinBlocks));
            swap(twinBlocks, row, col - 1, col, false);
        }
        if (col != N - 1) {
            swap(twinBlocks, row, col + 1, col, false);
            neighbors.enqueue(new Board(twinBlocks));
            swap(twinBlocks, row, col + 1, col, false);
        }
        if (row != 0) {
            swap(twinBlocks, col, row - 1, row, true);
            neighbors.enqueue(new Board(twinBlocks));
            swap(twinBlocks, col, row - 1, row, true);
        }
        if (row == N - 1) {
            swap(twinBlocks, col, row + 1, row, true);
            neighbors.enqueue(new Board(twinBlocks));
            swap(twinBlocks, col, row + 1, row, true);
        }
        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N);
        for (int i = 0; i < N * N; i++) {
            if (i % N == 0) s.append("\n");
            s.append(String.format("%2d ", blocks[i]));
        }
        s.append("\n");
        return s.toString();
    }

    private void swap(int[][] blocks, int row, int i, int j, boolean roc) {
        if (roc == false) {
            int tmp = blocks[row][i];
            blocks[row][i] = blocks[row][j];
            blocks[row][j] = tmp;
        } else {
            int tmp = blocks[i][row];
            blocks[i][row] = blocks[j][row];
            blocks[j][row] = tmp;
        }
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.print(initial);
        StdOut.println("Hamming : " + initial.hamming());
        StdOut.println("Manhattan : " + initial.manhattan());

    }
}
