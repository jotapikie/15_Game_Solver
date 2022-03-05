import java.util.*;

public class DFSSolver {

    /**
     * returns a list of boards if it was able to solve it, or else null
     *
     * @return
     */
    public List<Board> dfsSolver(Board b) throws CloneNotSupportedException {
        Comparator<Board> comparator = new Comparator<Board>() {
            @Override
            public int compare(Board a, Board b) {
                return a.getDepth() - b.getDepth();
            }
        };
        HashSet<Board> visited = new HashSet<>();
        PriorityQueue<Board> toVisit = new PriorityQueue<Board>(10000,comparator);
        HashMap<Board, Board> predecessor = new HashMap<Board, Board>();
        toVisit.add(b);
        predecessor.put(b, null);
        int cnt=0;
        while( toVisit.size() > 0) {
            Board candidate = toVisit.remove();
            visited.add(candidate);
            cnt++;
            if( cnt % 10000 == 0) {
                System.out.printf("Considered %,d positions. Queue = %,d\n", cnt, toVisit.size());
            }
            if( candidate.isSolved() ) {
                System.out.printf("Solution considered %d boards\n", cnt);
                LinkedList<Board> solution = new LinkedList<Board>();
                Board backtrace=candidate;
                while( backtrace != null ) {
                    solution.addFirst(backtrace);
                    backtrace = predecessor.get(backtrace);
                }
                return solution;
            }
            for(Board fp: candidate.allAdjacentPuzzles()) {
                if( !predecessor.containsKey(fp) ) {
                    predecessor.put(fp,candidate);
                    toVisit.add(fp);
                }
            }
        }
        return null;
    }


}
