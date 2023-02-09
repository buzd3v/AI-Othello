import java.util.ArrayList;
import java.util.List;

public class AI {
    GameGrid gameGrid;
    GameMap gameMap;
    Grid[][] gameBoard = new Grid[GlobalVar.gridWidth][GlobalVar.gridHeight];
    public AI(GameGrid gameGrid,GameMap gMap) {
        this.gameGrid = gameGrid;
        gameMap = gMap;
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
    public Position choseMove() {
        Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        List<Position> moves = new ArrayList<>();
        
        GameGrid tempGameGrid = new GameGrid(new Position(0, 0), GlobalVar.WIDTH, GlobalVar.HEIGHT);
        deepCopyGameGrid(tempGameGrid, gameGrid);
        tempGameGrid.updateValidMoves(1);
        
        deepCopyPosition(moves, tempGameGrid.getAllValidMoves());

        System.out.println("White's Valid moves: " + tempGameGrid.getAllValidMoves());
        
        for (int i = 0; i < moves.size(); i++) {
            tempGameGrid.playMove(moves.get(i), 1);
            tempGameGrid.updateValidMoves(2);
            int score = minimax(0, false, tempGameGrid);
            
            if (score > bestScore) {
                bestScore = score;
                bestMove = moves.get(i);
            }
        }
        System.out.println("White's move: " + bestMove);
        System.out.println("Black's valid move: " + gameGrid.getAllValidMoves());

        return bestMove;
    }
    
    /**
     * @param depth: the depth of minimax
     * @param isMaximizing : true if is maximizing, false if is opponent
     *
     * @return the score of best move
     */
    private int minimax(int depth, boolean isMaximizing, GameGrid gameGrid) {
        GameGrid temp = new GameGrid(new Position(0, 0), GlobalVar.WIDTH, GlobalVar.HEIGHT);
        deepCopyGameGrid(temp, gameGrid);
        int player;
        if(isMaximizing) player = 1;
        else player = 2; 
        temp.updateValidMoves(player);
        if (depth == 4 ) {
            return staticWeightingScheme();
        }
        if (isMaximizing) {
            int maxVal = Integer.MIN_VALUE;
            
            List<Position> moves = new ArrayList<>();
            deepCopyPosition(moves, temp.getAllValidMoves());
            // System.out.println("max " + moves);
            for (int i = 0; i < moves.size(); i++) {
                temp.playMove(moves.get(i),1);
                temp.updateValidMoves(2);

                int val = minimax(depth + 1, false,gameGrid);
                
                maxVal = Math.max(maxVal, val);
            }
            return maxVal;
        } else {
            int minVal = Integer.MAX_VALUE;
            
            List<Position> moves = new ArrayList<>();
            deepCopyPosition(moves, temp.getAllValidMoves());
            // System.out.println("min " + moves);
            for (int i = 0; i < moves.size(); i++) {
                temp.playMove(moves.get(i), 2);
                temp.updateValidMoves(1);
                int val = minimax(depth + 1, true,gameGrid);
                
                minVal = Math.min(minVal, val);
            }
            return minVal;
        }
    }

    // public int evaluate() {

    // }

    int staticWeightingScheme() {
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

    private int cornerBlock() {
        int score = 0;
        Grid[][] temp = gameGrid.getGrid();
        for (int i = 0; i < 8; i++) {
            if (temp[0][i].getCellState() == 1) {
            }
        }
        return 0;
    }
}
