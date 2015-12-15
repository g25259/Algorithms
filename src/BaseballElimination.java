import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by g2525_000 on 2015/12/15.
 */
public class BaseballElimination {
    private String[] teams;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] against;
    private FlowNetwork flowNetwork;
    private int mostWin;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int numOfTeams = in.readInt();
        teams = new String[numOfTeams];
        wins = new int[numOfTeams];
        losses = new int[numOfTeams];
        remaining = new int[numOfTeams];
        against = new int[numOfTeams][numOfTeams];
        int count = 0;
        while (count < numOfTeams) {
            teams[count] = in.readString();
            wins[count] = in.readInt();
            if (wins[count] > mostWin)
                mostWin = wins[count];
            losses[count] = in.readInt();
            remaining[count] = in.readInt();
            for (int i = 0; i < numOfTeams; i++) {
                against[count][i] = in.readInt();
            }
            ++count;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.length;
    }

    // all teams
    public Iterable<String> teams() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < teams.length; i++) {
            list.add(teams[i]);
        }
        return list;
    }

    // number of wins for given team
    public int wins(String team) {
        int idx = team(team);
        if (team(team) == -1) throw new java.lang.IllegalArgumentException();
        return wins[idx];
    }

    // number of losses for given team
    public int losses(String team) {
        int idx = team(team);
        if (idx == -1) throw new java.lang.IllegalArgumentException();
        return losses[idx];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        int idx = team(team);
        if (idx == -1) throw new java.lang.IllegalArgumentException();
        return remaining[idx];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int idx1 = team(team1);
        int idx2 = team(team2);
        if ((idx1 == -1) || (idx2 == -1)) throw new java.lang.IllegalArgumentException();
        return against[idx1][idx2];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        int idx = team(team);
        if (idx == -1) throw new java.lang.IllegalArgumentException();
        if (wins(team) + remaining(team) < mostWin)
            return true;
        if (FordFulkerson(team) != null) return true;
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        int idx = team(team);
        if (idx == -1) throw new java.lang.IllegalArgumentException();
        if (wins(team) + remaining(team) < mostWin) {
            List r = new ArrayList<>();
            r.add(teams[0]);
            return r;
        }
        return FordFulkerson(team);
    }

    private int team(String team) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].equals(team)) return i;
        }
        return -1;
    }

    private FlowNetwork eliminationNet(String team) {
        int idx = team(team);
        int numOfGameVertices = numOfGameVertices();
        FlowNetwork fn = new FlowNetwork(numOfGameVertices + teams.length - 1 + 2);
        boolean jumpI = false;
        int count = 1;
        for (int i = 0; i < teams.length; i++) {
            boolean jumpJ = false;
            if (i == idx) {
                jumpI = true;
                continue;
            }
            for (int j = i + 1; j < teams.length; j++) {
                if (j == idx) {
                    jumpJ = true;
                    continue;
                }
                if (i > idx)
                    jumpJ = true;
                if (!jumpI) {
                    if (!jumpJ) {
                        fn.addEdge(new FlowEdge(0, count, against[i][j]));
                        fn.addEdge(new FlowEdge(count,
                                1 + numOfGameVertices + i, Double.POSITIVE_INFINITY));
                        fn.addEdge(new FlowEdge(count,
                                1 + numOfGameVertices + j, Double.POSITIVE_INFINITY));
                    } else {
                        fn.addEdge(new FlowEdge(0, count, against[i][j]));
                        fn.addEdge(new FlowEdge(count,
                                1 + numOfGameVertices + i, Double.POSITIVE_INFINITY));
                        fn.addEdge(new FlowEdge(count,
                                1 + numOfGameVertices + j - 1, Double.POSITIVE_INFINITY));
                    }
                } else {

                    fn.addEdge(new FlowEdge(0, count, against[i][j]));
                    fn.addEdge(new FlowEdge(count,
                            1 + numOfGameVertices + i - 1, Double.POSITIVE_INFINITY));
                    fn.addEdge(new FlowEdge(count,
                            1 + numOfGameVertices + j - 1, Double.POSITIVE_INFINITY));
                }
                ++count;
            }
        }
        count = 0;
        for (int i = 0; i < teams.length; i++) {
            int capacity = wins(team) + remaining(team) - wins[i];
            if (capacity < 0) capacity =0;
            if (i < idx) {
                fn.addEdge(new FlowEdge(1 + numOfGameVertices + i,
                        fn.V() - 1, capacity));
            } else if (i > idx) fn.addEdge(new FlowEdge(numOfGameVertices + i,
                    fn.V() - 1, capacity));

        }
        return fn;

    }

    private Iterable<String> FordFulkerson(String team) {
        FlowNetwork fn = eliminationNet(team);
        int fullValue = fullValue(fn);
        FordFulkerson fordFulkerson = new FordFulkerson(fn, 0, fn.V() - 1);
        if (fordFulkerson.value() < fullValue) {
            List<String> R = new ArrayList<>();
            int idx = team(team);
            for (int i = 0; i < teams.length; i++) {
                if (i < idx) {
                    if (fordFulkerson.inCut(1 + numOfGameVertices() + i)) {
                        R.add(teams[i]);
                    }
                } else if (i > idx){
                    if (fordFulkerson.inCut(1 + numOfGameVertices() + i - 1)) {
                        R.add(teams[i]);
                    }
            }
            }
            return R;
        }
        return null;

    }

    private int fullValue(FlowNetwork fn) {
        int value = 0;
        for (FlowEdge e : fn.adj(0)) {
            value += e.capacity();
        }
        return value;
    }
    private int numOfGameVertices() {
        return (teams.length - 2) * (teams.length - 1) / 2;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
