import java.util.*;

/**
 * Created by MingJe on 2015/7/31.
 */
public class Solver {
    private final Comparator<Board> boardComparator = new Comparator<Board>() {
        @Override
        public int compare(Board o1, Board o2) {
            int o1Priority = o1.hamming() + o1.manhattan();
            int o2Priority = o2.hamming() + o2.manhattan();
            if (o1Priority < o2Priority) return -1;
            else if (o1Priority > o2Priority) return 1;
            return 0;
        }
    };

    private class Node<T> {
        Node<T> previous;
        T value;

        Node(T value) {
            this.value = value;
        }

    }

    private Node<Board> goal;
    private Node<Board> twinGoal;
    private MinPQ<Board> mpq;
    private MinPQ<Board> twinMpq;
    private int moves;
    private boolean isSolvable;
    private ArrayDeque<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();
        mpq = new MinPQ<>(boardComparator);
        twinMpq = new MinPQ<>(boardComparator);
        Board twin = initial.twin();
        mpq.insert(initial);
        twinMpq.insert(twin);
        goal = new Node<>(initial);
        twinGoal = new Node<>(twin);
        solution = new ArrayDeque<>();
        solve();
    }

    public void solve() {
        Board min = mpq.delMin();
        Board twinMin = twinMpq.delMin();
        while (!goal.value.isGoal() && !twinGoal.value.isGoal()) {
            addNeighbor(mpq, goal);
            addNeighbor(twinMpq, twinGoal);
            goal = next(mpq, goal);
            twinGoal = next(twinMpq, twinGoal);
        }

        if (!goal.value.isGoal()) isSolvable = false;
        else {
            isSolvable = true;
            int moves = 0;
            while (goal != null) {
                solution.push(goal.value);
                goal = goal.previous;
                moves++;
            }
            this.moves = moves;
        }
    }

    private Node next(MinPQ<Board> mpq, Node<Board> goal) {
        Node<Board> pre = goal;
        Board min = mpq.delMin();
        goal = new Node<>(min);
        goal.previous = pre;
        return goal;
    }

    private void addNeighbor(MinPQ<Board> mpq, Node<Board> goal) {
        for (Board neighbor : goal.value.neighbors()) {
            if (!goal.value.equals(neighbor))
                mpq.insert(neighbor);
        }
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
    }
}
