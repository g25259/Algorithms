import java.util.*;

/**
 * Created by MingJe on 2015/7/31.
 */
public class Solver {
    private static int insertCount = 0;
    private static int delCount = 0;
    private final Comparator<Node<Board>> boardComparator = new Comparator<Node<Board>>() {
        @Override
        public int compare(Node o1, Node o2) {
            Board ob1 = (Board) o1.value;
            Board ob2 = (Board) o2.value;
            int o1Priority = ob1.manhattan() + o1.moved;
            int o2Priority = ob2.manhattan() + o2.moved;
            if (o1Priority < o2Priority) return -1;
            else if (o1Priority > o2Priority) return 1;
            return 0;
        }
    };

    private static class Node<T> {
        Node<T> previous;
        T value;
        int moved;

        Node(T value, int moved) {
            this.value = value;
            this.moved = moved;
        }

    }

    private Node<Board> goal;
    private Node<Board> twinGoal;
    private MinPQ<Node<Board>> mpq;
    private MinPQ<Node<Board>> twinMpq;
    private int moves = -1;
    private boolean isSolvable;
    private ArrayDeque<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();
        mpq = new MinPQ<>(boardComparator);
        twinMpq = new MinPQ<>(boardComparator);
        Board twin = initial.twin();
        goal = new Node<>(initial, 0);
        twinGoal = new Node<>(twin, 0);
        //mpq.insert(goal);
        //twinMpq.insert(goal);
        solve();
    }

    private void solve() {
        //Board min = mpq.delMin().value;
        //Board twinMin = twinMpq.delMin().value;
        while (!goal.value.isGoal() && !twinGoal.value.isGoal()) {
            addNeighbor(mpq, goal);
            addNeighbor(twinMpq, twinGoal);
            goal = mpq.delMin();
            twinGoal = twinMpq.delMin();
            delCount++;
        }

        if (!goal.value.isGoal()) isSolvable = false;
        else {
            isSolvable = true;
            solution = new ArrayDeque<>();
            int moves = -1;
            while (goal != null) {
                solution.push(goal.value);
                goal = goal.previous;
                moves++;
            }
            this.moves = moves;
        }
    }

    private void addNeighbor(MinPQ<Node<Board>> mpq, Node<Board> goal) {

        for (Board neighbor : goal.value.neighbors()) {
            if (goal.previous == null) {
                Node<Board> tmp = new Node(neighbor, goal.moved + 1);
                tmp.previous = goal;
                mpq.insert(tmp);
                insertCount++;
            } else if (!goal.previous.value.equals(neighbor)) {
                Node<Board> tmp = new Node(neighbor, goal.moved + 1);
                tmp.previous = goal;
                mpq.insert(tmp);
                insertCount++;
            }
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
        //StdOut.print(insertCount + "  " + delCount);
    }
}
