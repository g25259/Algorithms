import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;


/**
 * Created by g2525_000 on 2015/12/18.
 */
public class BoggleSolver {
    private HashSet<String> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new HashSet();
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.add(dictionary[i]);
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> validWords = new SET<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, "", validWords);
            }
        }
        return validWords;
    }

    private void dfs(BoggleBoard board, int row, int col, String prefix, SET<String> validWords) {
        if (row > 0) {
            check(board, row - 1, col, prefix, validWords);
            if (col > 0)
                check(board, row - 1, col - 1, prefix, validWords);
            if (col < board.cols() - 1)
                check(board, row - 1, col + 1, prefix, validWords);
        }
        if (row < board.rows() - 1) {
            check(board, row + 1, col, prefix, validWords);
            if (col > 0)
                check(board, row + 1, col - 1, prefix, validWords);
            if (col < board.cols() - 1)
                check(board, row + 1, col + 1, prefix, validWords);
        }
        if (col > 0) {
            check(board, row, col - 1, prefix, validWords);
        }
        if (col < board.cols() - 1) {
            check(board, row, col + 1, prefix, validWords);
        }
    }

    private void check(BoggleBoard board, int row, int col, String prefix, SET<String> validWords) {
        char letter = board.getLetter(row, col);
        String neighbor;
        if (!contain(prefix, letter)) {
            if (letter == 'Q') {
                neighbor = prefix + letter + 'U';
            } else neighbor = prefix + letter;

            if (neighbor.length() >= 3) {
                if (dictionary.contains(neighbor))
                    validWords.add(neighbor);
            }
            dfs(board, row, col, neighbor, validWords);
        }
    }

    private boolean contain(String prefix, char letter) {
        for (int i = 0; i < prefix.length(); i++) {
            if (prefix.charAt(i) == letter)
                return true;
        }
        return false;
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
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
