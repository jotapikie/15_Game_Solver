import java.util.*;

public class IDFSSolver {
    /**
     * returns a list of boards if it was able to solve it, or else null
     *
     * @return
     */
    public List<Board> idfsSolver(Board b) throws CloneNotSupportedException {

        int range=100;
        Comparator<Board> comparator = Comparator.comparingInt(Board::getDepth);
        HashSet<Board> visited = new HashSet<>();
        HashMap<Board, Board> predecessor = new HashMap<Board, Board>();
        predecessor.put(b, null);
        int cnt=0;
        for (int limit=0; limit<range; limit++) {
            PriorityQueue<Board> toVisit = new PriorityQueue<Board>(10000,comparator);
            toVisit.add(b);
            while (toVisit.size() > 0) {
                Board candidate = toVisit.remove();
                visited.add(candidate);
                cnt++;
                if (cnt % 10000 == 0) {
                    System.out.printf("Considered %,d positions. Queue = %,d\n", cnt, toVisit.size());
                }
                if (candidate.isSolved()) {
                    System.out.printf("Solution considered %d boards\n", cnt);
                    LinkedList<Board> solution = new LinkedList<Board>();
                    Board backtrace = candidate;
                    while (backtrace != null) {
                        solution.addFirst(backtrace);
                        backtrace = predecessor.get(backtrace);
                    }
                    return solution;
                }
                for (Board fp : candidate.allAdjacentPuzzles()) {
                    if (!predecessor.containsKey(fp) && fp.getDepth() < limit) {
                        predecessor.put(fp, candidate);
                        toVisit.add(fp);
                    }
                }
            }
        }
        return null;
    }

}
