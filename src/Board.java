/**
 * Created by g2525_000 on 2015/7/31.
 */
public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private final int[][] blocks;
    private final int N;

    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = blocks;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int hammingDistance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ( blocks[i][j] != i * N + (j + 1))
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < ; i++) {
            
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
    }

    // does this board equal y?
    public boolean equals(Object y) {
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
    }

    public static void main(String[] args) {
    }// unit tests (not graded)
}
