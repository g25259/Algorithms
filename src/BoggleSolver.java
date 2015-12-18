import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolDigraph;


/**
 * Created by g2525_000 on 2015/12/18.
 */
public class BoggleSolver {
    private TrieST<Integer> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TrieST<>();
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.put(dictionary[i], 0);
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> validWords = new SET<>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                boolean[][]test = new boolean[board.rows()][board.cols()];
                dfs(board, i, j, "", validWords, test);
            }
        }
        return validWords;
    }

    private void dfs(BoggleBoard board, int row, int col, String prefix, SET<String> validWords, boolean[][] used) {
        if (row > 0) {
            check(board, row - 1, col, prefix, validWords, used);
            used[row -1][col] = false;
            if (col > 0) {
                check(board, row - 1, col - 1, prefix, validWords, used);

            }
            if (col < board.cols() - 1)
                check(board, row - 1, col + 1, prefix, validWords, used);
        }
        if (row < board.rows() - 1) {
            check(board, row + 1, col, prefix, validWords, used);
            if (col > 0)
                check(board, row + 1, col - 1, prefix, validWords, used);
            if (col < board.cols() - 1)
                check(board, row + 1, col + 1, prefix, validWords, used);
        }
        if (col > 0) {
            check(board, row, col - 1, prefix, validWords, used);
        }
        if (col < board.cols() - 1) {
            check(board, row, col + 1, prefix, validWords, used);
        }
    }

    private void check(BoggleBoard board, int row, int col, String prefix, SET<String> validWords, boolean[][] used) {
        char letter = board.getLetter(row, col);
        String neighbor;

        if (!used[row][col]) {

            if (letter == 'Q') {
                neighbor = prefix + letter + 'U';
            } else neighbor = prefix + letter;
            if (dictionary.isKeyWithPrefix(neighbor) ) {
                /*boolean[][] copyUsed = new boolean[used.length][used[0].length];
                for (int i = 0; i < used.length; i++) {
                    System.arraycopy(used[i], 0, copyUsed[i], 0, used[0].length);
                }*/
                used[row][col] = true;
                if (neighbor.length() >= 3) {
                    if (dictionary.contains(neighbor))
                        validWords.add(neighbor);
                }
                dfs(board, row, col, neighbor, validWords, used);
            }
        }

    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.contains(word)) {
            int length = word.length();
            switch (length) {
                case 3:
                    return 1;
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    if (length <= 2) return 0;
                    else if (length >= 8) return 11;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        long startTime = System.currentTimeMillis();
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        StdOut.println((System.currentTimeMillis() - startTime) / 1000);
    }
}
