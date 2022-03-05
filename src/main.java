import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class main {

    static final String BFS_OPTION= "--bfs";
    static final String DFS_OPTION= "--dfs";
    static final String IDFS_OPTION= "--idfs";
    static final String BF_OPTION ="--bf";
    static final String ASTAR_OPTION="--astar";
    static final String SMA_OPTION="--sma";
    static final int MANHANTAN_HEURISTIC=0;
    static final int ERROR_HEURISTIC=1;
    static final int[][] solvedPuzzle = new int[4][4];

    public static void main(String[] args) throws CloneNotSupportedException {
        int rows;
        int columns;
        int in;
        int heuristic=0;

        Scanner sc= new Scanner(System.in);
        System.out.println("\nPlease Input puzzle configuration:\n");
        System.out.println("\nColumns:\n");
        columns=sc.nextInt();
        System.out.println("\nRows:\n");
        rows=sc.nextInt();
        ArrayList<Integer> results= new ArrayList<>();
        int[][] inputTiles = new int[columns][rows];
        int numberOfTiles= rows*columns;
        for(int i=0; i<columns; i++) {
            for(int j=0; j<rows; j++) {
                System.out.println("Insert content");
                in=sc.nextInt();
                if (results.isEmpty()&&(in<numberOfTiles-1&&in>0)){
                    results.add(in);
                    inputTiles[i][j]=in;
                }else{
                    while ((in>numberOfTiles-1||in<0)||results.contains(in)){
                        System.out.println("Values must be unique and between 0 and "+ (numberOfTiles-1));
                        in= sc.nextInt();
                    }
                }
                results.add(in);
                inputTiles[i][j]=in;
            }
        }

        int cnt=1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                solvedPuzzle[i][j] = cnt;
                cnt++;
            }
        }

        solvedPuzzle[rows - 1][columns - 1] = 0;
        Board solvedBoard= new Board(columns,rows,solvedPuzzle);
        solvedBoard.show();
        Board mainBoard= new Board(columns,rows,inputTiles);
        mainBoard.show();

        if (args[0].equals(DFS_OPTION)||args[0].equals(BFS_OPTION)||args[0].equals(IDFS_OPTION)){
            if (args.length!=1){
                String param=args[1].substring(1,args[1].length()-1);
                String[] order=param.split(",");
                for (String s : order) {
                    switch (s) {
                        case "U":
                            for (Board.TilePos p : mainBoard.allValidMoves()) {
                                if (mainBoard.getBlank().y < p.y) {
                                    mainBoard.move(p);
                                }
                            }
                        case "D":
                            for (Board.TilePos p : mainBoard.allValidMoves()) {
                                if (mainBoard.getBlank().y > p.y) {
                                    mainBoard.move(p);
                                }
                            }
                        case "R":
                            for (Board.TilePos p: mainBoard.allValidMoves()){
                                if(mainBoard.getBlank().x<p.x){
                                    mainBoard.move(p);
                                }
                            }
                        case "L":
                            for (Board.TilePos p: mainBoard.allValidMoves()){
                                if(mainBoard.getBlank().x>p.x){
                                    mainBoard.move(p);
                                }
                            }
                    }
                }
            }

        }

        if (mainBoard.isSolvable()){
            System.out.println("Matrix is solvable");
        }else{
            System.out.println("Non Solvable Matrix, Sorry");
            return;
        }

        if (args[0].equals(BF_OPTION)||args[0].equals(ASTAR_OPTION)||args[0].equals(SMA_OPTION)){
            if (!Objects.equals(args[1], "0") && !Objects.equals(args[1], "1")){
                System.out.println("Invalid output for heuristic");
                return;
            }else{
                if (args[1].equals("0")){
                    heuristic=MANHANTAN_HEURISTIC;
                }else{
                    heuristic=ERROR_HEURISTIC;
                }
            }
        }
        List<Board> solution;

        switch (args[0]){

            case (BFS_OPTION):
                mainBoard.show();
                System.out.println("Solving with BFS");
                BFSSolver bfs = new BFSSolver();
                solution = bfs.bfsSolver(mainBoard);
                showSolution(solution);
                break;


            case (DFS_OPTION):
                mainBoard.show();
                System.out.println("Solving with DFS");
                DFSSolver dfs = new DFSSolver();
                solution = dfs.dfsSolver(mainBoard);
                showSolution(solution);
                break;

            case (ASTAR_OPTION):
                mainBoard.show();
                System.out.println("Solving with A*");
                AStarSolver a = new AStarSolver();
                solution = a.aStarSolve(mainBoard, heuristic);
                showSolution(solution);
                break;

            case (BF_OPTION):
                mainBoard.show();
                System.out.println("Solving with Best First Search");
                BestFSSolver bf = new BestFSSolver();
                solution = bf.bestFSSolver(mainBoard);
                showSolution(solution);
                break;

            case (IDFS_OPTION):
                mainBoard.show();
                System.out.println("Solving with Iterative deepenening DFS");
                IDFSSolver idfsSolver = new IDFSSolver();
                solution = idfsSolver.idfsSolver(mainBoard);
                showSolution(solution);
                break;
        }


    }

    private static void showSolution(List<Board> solution) {
        if (solution != null ) {
            System.out.printf("Success!  Solution with %d moves:\n", solution.size());
            for( Board sp: solution) {
                sp.show();
            }
        } else {
            System.out.println("Did not solve. :(");
        }
   }


}


