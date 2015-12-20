import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

/**
 * Created by g2525_000 on 2015/12/18.
 */
public class BoggleSolver {
    private class TrieST {
        private static final int R = 26;
        private Node root;


        public class Node {
            protected int value = -1;
            private Node[] next = new Node[R];
            private int prefix = 1;
        }

        public void put(String key, int val) {
            root = put(root, key, val, 0);
        }

        private Node put(Node x, String key, int val, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                x.value = val;
                return x;
            }
            char c = (char) (key.charAt(d) - 'A');
            x.next[c] = put(x.next[c], key, val, d + 1);
            return x;
        }

        public int get(String key) {
            Node x = get(root, key, 0);
            if (x == null) return -1;
            return x.value;
        }

        public Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = (char) (key.charAt(d) - 'A');
            return get(x.next[c], key, d + 1);
        }

        public boolean contains(String key) {
            return get(key) != -1;
        }


    }

    private TrieST dictionary;
    private boolean[][] used;
    private StringBuilder prefix;
    private HashSet<String> validWords;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TrieST();
        prefix = new StringBuilder();
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.put(dictionary[i], 0);
        }


    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        validWords = new HashSet<>();
        used = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, dictionary.root);
            }
        }
        return validWords;
    }

    private void dfs(BoggleBoard board, int row, int col, TrieST.Node x) {
        if (row > 0) {
            if (!used[row - 1][col]) {
                check(board, row - 1, col, x);
                // used[row - 1][col] = false;
            }
            if (col > 0) {
                if (!used[row - 1][col - 1]) {
                    check(board, row - 1, col - 1, x);
                    // used[row - 1][col - 1] = false;
                }
            }
            if (col < board.cols() - 1) {
                if (!used[row - 1][col + 1]) {
                    check(board, row - 1, col + 1, x);
                    //  used[row - 1][col + 1] = false;
                }
            }
        }
        if (row < board.rows() - 1) {
            if (!used[row + 1][col]) {
                check(board, row + 1, col, x);
                // used[row + 1][col] = false;
            }
            if (col > 0) {
                if (!used[row + 1][col - 1]) {
                    check(board, row + 1, col - 1, x);
                    // used[row + 1][col - 1] = false;
                }
            }
            if (col < board.cols() - 1) {
                if (!used[row + 1][col + 1]) {
                    check(board, row + 1, col + 1, x);
                    // used[row + 1][col + 1] = false;
                }
            }
        }
        if (col > 0) {
            if (!used[row][col - 1]) {
                check(board, row, col - 1, x);
                // used[row][col - 1] = false;
            }
        }
        if (col < board.cols() - 1) {
            if (!used[row][col + 1]) {
                check(board, row, col + 1, x);
                // used[row][col + 1] = false;
            }
        }
    }

    private void check(BoggleBoard board, int row, int col, TrieST.Node x) {
        char letter = board.getLetter(row, col);
        if (letter == 'Q') {
            x = x.next['Q' - 'A'];
            if (x != null) {
                x = x.next['U' - 'A'];
            } else {
                return;
            }
        } else {
            x = x.next[letter - 'A'];
        }
        if (x != null && x.prefix == 1) {
            used[row][col] = true;
            if (letter != 'Q'){
                prefix.append(letter);
                if (prefix.length() >= 3) {
                    if (x.value != -1)
                        validWords.add(prefix.toString());
                }
                dfs(board, row, col, x);
                used[row][col] = false;
                prefix.deleteCharAt(prefix.length() - 1);
            }

            else {
                prefix.append(letter);
                prefix.append('U');
                if (prefix.length() >= 3) {
                    if (x.value != -1)
                        validWords.add(prefix.toString());
                }
                dfs(board, row, col, x);
                used[row][col] = false;
                prefix.delete(prefix.length() - 2, prefix.length());
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
        StdOut.println((System.currentTimeMillis() - startTime));
    }
}
