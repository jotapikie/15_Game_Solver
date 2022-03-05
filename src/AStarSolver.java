import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class AStarSolver {

    /**
     * returns a list of boards if it was able to solve it, or else null
     * @return
     */
    public List<Board> aStarSolve(Board b, int typeOfHeuristic) throws CloneNotSupportedException {
        HashMap<Board, Board> predecessor = new HashMap<Board, Board>();
        HashMap<Board,Integer> depth = new HashMap<Board,Integer>();
        final HashMap<Board,Integer> score = new HashMap<Board,Integer>();
        Comparator<Board> comparator = new Comparator<Board>() {
            @Override
            public int compare(Board a, Board b) {
                return score.get(a) - score.get(b);
            }
        };
        PriorityQueue<Board> toVisit = new PriorityQueue<Board>(10000,comparator);

        predecessor.put(b, null);
        depth.put(b,0);
        if (typeOfHeuristic==0){
            score.put(b, b.estimateErrorByManhattan());
        }else{
            score.put(b, b.estimateErrorByMisplaced());
        }
        toVisit.add(b);
        int cnt=0;
        while( toVisit.size() > 0) {
            Board candidate = toVisit.remove();
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
                if( !predecessor.containsKey(fp)) {
                    predecessor.put(fp,candidate);
                    depth.put(fp,depth.get(candidate)+1);
                    if (typeOfHeuristic==0){
                        int estimate= fp.estimateErrorByManhattan();
                        score.put(fp, depth.get(candidate)+1 + estimate);
                    }else{
                        int estimate= fp.estimateErrorByMisplaced();
                        score.put(fp, depth.get(candidate)+1 + estimate);
                    }

                    toVisit.add(fp);
                }
            }
        }
        return null;
    }





}
