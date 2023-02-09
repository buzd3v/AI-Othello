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
            int score = minimax(0, false);
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
     * @return the score of best move
     */
    private int minimax(int depth, boolean isMaximizing) {
        if (depth == 4 || gameGrid.getAllValidMoves().size() == 0) {
            return gameGrid.getNumberOfWhite() - gameGrid.getNumberOfBlack();
        }
        if (isMaximizing) {
            int maxVal = Integer.MIN_VALUE;
            List<Position> moves = gameGrid.getAllValidMoves();
            for (int i = 0; i < moves.size(); i++) {
                gameMap.play(moves.get(i));
                int val = minimax(depth + 1, false);
                gameMap.undo();
                maxVal = Math.max(maxVal, val);
                
            }
            return maxVal;
        } else {
            int minVal = Integer.MAX_VALUE;
            List<Position> moves = gameGrid.getAllValidMoves();
            for (int i = 0; i < moves.size(); i++) {
                gameMap.play(moves.get(i));
                int val = minimax(depth + 1, true);
                gameMap.undo();
                minVal = Math.min(minVal, val);
            }
            return minVal;
        }
    }
}
