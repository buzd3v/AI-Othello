import java.util.Collections;

public class AI {
    GameGrid gameGrid;

    public AI(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    public Position choseMove() {
        Collections.shuffle(gameGrid.getAllValidMoves());
        return gameGrid.getAllValidMoves().get(0);
    }
}
