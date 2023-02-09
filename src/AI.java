import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AI {
    GameGrid gameGrid;
    GameMap gameMap;
    Grid[][] gameBoard = new Grid[GlobalVar.gridWidth][GlobalVar.gridHeight];
    public AI(GameGrid gameGrid,GameMap gMap) {
        this.gameGrid = gameGrid;
        gameMap = gMap;
    }

    public Position choseMove() {
        Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        List<Position> moves = gameGrid.getAllValidMoves();
        for (int i = 0; i < moves.size(); i++) {
            gameMap.play(moves.get(i));
            int score = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            gameMap.undo();
            if (score > bestScore) {
                bestScore = score;
                bestMove = moves.get(i);
            }
        }
        return bestMove;
    }
    
    /**
     * @param depth: the depth of minimax
     * @param isMaximizing : true if maximize the value, false if minimize the opponent value
     * @param alpha maximum score
     * @param beta minimum score of opponent
     * 
     * return the score of best move
     */
    private int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 4) {
            return gameGrid.getNumberOfWhite() - gameGrid.getNumberOfBlack();
        }
        if (isMaximizing) {
            int maxVal = Integer.MIN_VALUE;
            List<Position> moves = gameGrid.getAllValidMoves();
            for (int i = 0; i < moves.size(); i++) {
                gameMap.play(moves.get(i));
                int val = minimax(depth + 1, true, alpha, beta);
                gameMap.undo();
                maxVal = Math.max(maxVal, val);
                beta = Math.max(alpha, maxVal);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxVal;
        } else {
            int minVal = Integer.MAX_VALUE;
            List<Position> moves = gameGrid.getAllValidMoves();
            for (int i = 0; i < moves.size(); i++) {
                gameMap.play(moves.get(i));
                int val = minimax(depth + 1, true, alpha, beta);
                gameMap.undo();
                minVal = Math.min(minVal, val);
                beta = Math.min(beta, minVal);
                if (beta <= alpha) {
                    break;
                }
            }
            return minVal;
        }

    }

    
    
}
