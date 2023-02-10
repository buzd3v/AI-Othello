import java.util.ArrayList;
import java.util.List;
public class AI {
    GameGrid gameGrid;

    public AI(GameGrid gameGrid,GameMap gMap) {
        this.gameGrid = gameGrid;
    }

    public void deepCopyGameGrid(GameGrid gr, GameGrid toCopy) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gr.grid[i][j].setCellState(toCopy.getGrid()[i][j].getCellState());
            }
        }
        
    }
    public void deepCopyPosition(List<Position> p1,List<Position> toCopy ){
        int size = toCopy.size();
        for (int i = 0; i < size; i++) {
            p1.add(new Position(toCopy.get(i)));
        }
    }

    public Position choseMoveABPrunning() {
        return minimaxWithAB(0, true, gameGrid, 1, Integer.MIN_VALUE, Integer.MAX_VALUE).getSecond();
    }
    public Position choseMove() {
        System.out.println("White's Posssible Moves: " + gameGrid.getAllValidMoves());
        
        return minimax(0, true, gameGrid, 1).getSecond();
    }
    
    /**
     * @param depth: the depth of minimax
     * @param isMaximizing : true if is maximizing, false if is opponent
     *
     * @return the score of best move
     */

     private Pair<Integer,Position> minimaxWithAB(int depth, boolean isMaximizing, GameGrid game, int player,int alpha,int beta) {
        List<GameGrid> list = new ArrayList<>();

        List<Position> p = new ArrayList<>();
        game.updateValidMoves(player);
        deepCopyPosition(p, game.getAllValidMoves());
        //System.out.println( player + "'s Posssible Moves: " + p);

        // System.out.println("white's valid move: " + p);
        // System.out.println("");
        for (int i = 0; i < p.size(); i++) {
            GameGrid tempp = new GameGrid(new Position(0, 0), GlobalVar.WIDTH, GlobalVar.HEIGHT);
            deepCopyGameGrid(tempp, game);
            tempp.playMove(p.get(i), player);
            list.add(tempp);
        }
        if (depth == 4) {
            //System.out.println(p);
            return new Pair<>(evaluate(game),null);
        }
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            Position bestPos = null;
            for (int i = 0; i < list.size(); i++) {
                //System.out.println(p);
                int val = minimaxWithAB(depth + 1, false, list.get(i), 2,alpha,beta).getFirst().intValue();
                if (val > best) {
                    best = val;
                    bestPos = p.get(i);
                }
                alpha = Math.max(alpha, best);
                if (alpha >= beta) {
                    break;
                }
            }
            return new Pair<>(best, bestPos);
        }
        else {
            int best = Integer.MAX_VALUE;
            Position bestPos = null;
            for (int i = 0; i < list.size(); i++) {
                int val = minimaxWithAB(depth + 1, true, list.get(i), 1,alpha,beta).getFirst().intValue();
                if (val < best) {
                    best = val;
                    //bestGr = list.get(i);
                    bestPos = p.get(i);
                    //System.out.println("BestMove: " + bestPos);
                    //bestMove = new Position(bestPos);
                }
                beta = Math.min(beta, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return new Pair<Integer,Position>(best, bestPos);
        }
    }
    private Pair<Integer,Position> minimax(int depth, boolean isMaximizing, GameGrid game, int player) {
        List<GameGrid> list = new ArrayList<>();

        List<Position> p = new ArrayList<>();
        game.updateValidMoves(player);
        deepCopyPosition(p, game.getAllValidMoves());
        //System.out.println( player + "'s Posssible Moves: " + p);

        // System.out.println("white's valid move: " + p);
        // System.out.println("");
        for (int i = 0; i < p.size(); i++) {
            GameGrid tempp = new GameGrid(new Position(0, 0), GlobalVar.WIDTH, GlobalVar.HEIGHT);
            deepCopyGameGrid(tempp, game);
            tempp.playMove(p.get(i), player);
            list.add(tempp);
        }
        if (depth == 4) {
            //System.out.println(p);
            return new Pair<>(evaluate(game),null);
        }
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            Position bestPos = null;
            for (int i = 0; i < list.size(); i++) {
                //System.out.println(p);
                int val = minimax(depth + 1, false, list.get(i), 2).getFirst().intValue();
                if (val > best) {
                    best = val;
                    bestPos = p.get(i);
                }
            }
            return new Pair<>(best, bestPos);
        }
        else {
            int best = Integer.MAX_VALUE;
            Position bestPos = null;
            for (int i = 0; i < list.size(); i++) {
                int val = minimax(depth + 1, true, list.get(i), 1).getFirst().intValue();
                if (val < best) {
                    best = val;
                    //bestGr = list.get(i);
                    bestPos = p.get(i);
                    //System.out.println("BestMove: " + bestPos);
                    //bestMove = new Position(bestPos);
                }
            }
            return new Pair<Integer,Position>(best, bestPos);
        }
    }

    // public int evaluate() {

    // }
    int evaluate(GameGrid gr) {
            return staticWeightingScheme(gr) 
                    + cellDiff(gr)
                    + calculateFrontierDiscScore(gr)
                    + checkCorner(gr);
        }
    int staticWeightingScheme(GameGrid gamerGrid) {
        int[][] weightingMatrix = {
                { 20, -3, 11, 8, 8, 11, -3, 20 },
                { -3, -7, -4, 1, 1, -4, -7, -3 },
                { 11, -4, 2, 2, 2, 2, -4, 11 },
                { 8, 1, 2, -3, -3, 2, 1, 8 },
                { 8, 1, 2, -3, -3, 2, 1, 8 },
                { 11, -4, 2, 2, 2, 2, -4, 11 },
                { -3, -7, -4, 1, 1, -4, -7, -3 },
                { 20, -3, 11, 8, 8, 11, -3, 20 }
        };
        int score = 0;
        Grid[][] temp = gameGrid.getGrid();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (temp[i][j].getCellState() == 1) {
                    score += weightingMatrix[i][j];
                }
                if (temp[i][j].getCellState() == 2) {
                    score -= weightingMatrix[i][j];
                }
            }
        }
        return score;
    }

    private int cellDiff(GameGrid gr) {
        int whitescore = 0;
        int blackscore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gr.getGrid()[i][j].getCellState() == 1) {
                    whitescore++;
                }
                if (gr.getGrid()[i][j].getCellState() == 2) {
                    blackscore++;
                }
            }
        }
        if (whitescore > blackscore) {
            return (whitescore / (whitescore + blackscore) * 100);
        }
        else{
            return -(blackscore/(whitescore+blackscore)*100);
        }
    }


    public int calculateFrontierDiscScore(GameGrid board) {
        int score = 0;
        int rows = board.getGrid().length;
        int cols = board.getGrid()[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board.getGrid()[row][col].getCellState() != 0) {
                    // Check if any of the 8 surrounding squares are empty
                    boolean hasEmptyNeighbor = (row > 0 && board.getGrid()[row - 1][col].getCellState() == 0) ||
                            (row < rows - 1 && board.getGrid()[row + 1][col].getCellState() == 0) ||
                            (col > 0 && board.getGrid()[row][col - 1].getCellState() == 0) ||
                            (col < cols - 1 && board.getGrid()[row][col + 1].getCellState() == 0) ||
                            (row > 0 && col > 0 && board.getGrid()[row - 1][col - 1].getCellState() == 0) ||
                            (row > 0 && col < cols - 1 && board.getGrid()[row - 1][col + 1].getCellState() == 0) ||
                            (row < rows - 1 && col > 0 && board.getGrid()[row + 1][col - 1].getCellState() == 0) ||
                            (row < rows - 1 && col < cols - 1 && board.getGrid()[row + 1][col + 1].getCellState() == 0);
                    if (hasEmptyNeighbor) {
                        if (board.getGrid()[row][col].getCellState() == 1) {
                            score++;
                        } else if (board.getGrid()[row][col].getCellState() == 2) {
                            score--;
                        }
                    }
                }
            }
        }

        return score;
    }
    private int checkCorner(GameGrid gr) {
        int score = 0;
        Grid[][] board = gr.getGrid();
        int row = board.length;
        int col = board[0].length;
        if (board[0][0].getCellState() == 1) {
            score += 20;
        } else if (board[0][0].getCellState() == 2) {
            score -= 20;
        }
        if (board[0][col - 1].getCellState() == 1) {
            score += 20;
        } else if (board[0][col - 1].getCellState() == 2) {
            score -= 20;
        }
        if (board[row - 1][0].getCellState() == 1) {
            score += 20;
        } else if (board[row - 1][0].getCellState() == 2) {
            score -= 20;
        }
        if (board[row - 1][col - 1].getCellState() == 1) {
            score += 20;
        } else if (board[row - 1][col - 1].getCellState() == 2) {
            score -= 20;
        }
        return score;
    }
}
