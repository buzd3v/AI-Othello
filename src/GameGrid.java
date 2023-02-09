import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameGrid extends Rectangle {
    /*
     * grid 
     */
    public Grid[][] grid;
    private GameMap gmap;
    public Grid[][] preGrid;
    public Stack<Grid[][]> stackOfPre;
    /*
     * list of available move
     */
    private List<Position> validMoves;

    /*
     * create a grid of gridcell 
     */

    public GameGrid(Position position, int width, int height, GameMap gmap) {

        super(position, width, height);
        this.gmap = gmap;
        stackOfPre = new Stack<>();
        grid = new Grid[GlobalVar.gridWidth][GlobalVar.gridHeight];
        preGrid = new Grid[GlobalVar.gridWidth][GlobalVar.gridHeight];
        int cellWidth = GlobalVar.UNIT;
        int cellHeight = GlobalVar.UNIT;

        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            for (int j = 0; j < GlobalVar.gridHeight; j++) {
                grid[i][j] = new Grid(new Position(position.x + cellWidth * i, position.y + cellHeight * j),
                        cellWidth, cellHeight);
                grid[i][j].setGameMap(gmap);
            }
        }
        grid[3][3].setCellState(1);
        grid[3][4].setCellState(2);
        grid[4][3].setCellState(2);
        grid[4][4].setCellState(1);
        /*
         * cccccccccc
         */
        validMoves = new ArrayList<>();
        updateValidMoves(2);

    }

    /*
     * reset all the cell
     */
    public void copy(Grid[][] grid, Grid[][] clone) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                clone[i][j] = new Grid(grid[i][j]);
            }
        }
    }
    public void reset() {
        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            for (int j = 0; j < GlobalVar.gridHeight; j++) {
                grid[i][j].reset();
            }
        }
    }
    /**
     * Gets the grid cell data.
     *
     * @return The array of all grid cells.
     */
    public Grid[][] getGrid() {
        return grid;
    }
    /*
     * get all the valid move
     */

    public List<Position> getAllValidMoves() {
        return validMoves;
    }
    /*
     * plays the move
     * swap all the valid cell
     * update the valid move
     * @param player : the ID of the player 1 or 2
     */

    public void playMove(Position position, int player) {
        Grid[][] temp = new Grid[8][8];
        copy(grid, temp);
        stackOfPre.push(temp);
        System.out.println("size of stack is: " +stackOfPre.size());
        grid[position.x][position.y].setCellState(player);
        List<Position> changeCellPos = getChangedPositionsForMove(position, player);
        for (Position s : changeCellPos) {
            grid[s.x][s.y].setCellState(player);
        }
        updateValidMoves(player==1?2:1);
    } 

    public void undoMove(int player) {
        copy(stackOfPre.pop(), grid);
        System.out.println("size of stack is: " +stackOfPre.size());
        updateValidMoves(player);
    }
    /*
     * convert mouse pos to grid pos
     */
    public Position convertMousePosition(Position mousePosition) {
        int gridX = (mousePosition.x - position.x) / GlobalVar.UNIT;
        int gridY = (mousePosition.y - position.y) / GlobalVar.UNIT;
        if (gridX >= GlobalVar.gridWidth || gridX < 0 || gridY >= GlobalVar.gridHeight || gridY < 0) {
            return new Position(-1, -1);
        }
        return new Position(gridX, gridY);
    }
    
    /*
     * check if the mouse pos in the valid moves list
     */
    public boolean isValidMove(Position position) {
        return getAllValidMoves().contains(position);
    }

    public boolean isValidMoveonMouse(Position mousePosition) {
        return getAllValidMoves().contains(convertMousePosition(mousePosition));
    }
    /*
     * check the winner is
     * @return 0 for no winner, 3 for draw, 1 and 2 for player 1 and 2
     */
    public int getWinner(boolean stillValidMove) {
        int[] counts = new int[3];
        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            for (int j = 0; j < GlobalVar.gridHeight; j++) {
                counts[grid[i][j].getCellState()]++;
            }
        }
        if (stillValidMove && counts[0] > 0)
            return 0;
        else if (counts[1] == counts[2])
            return 3;
        else
            return counts[1] > counts[2] ? 1 : 2;
    }

    public int getNumberOfBlack() {
        int count = 0;
        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            for (int j = 0; j < GlobalVar.gridHeight; j++) {
                if (grid[i][j].getCellState() == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getNumberOfWhite() {
        int count = 0;
        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            for (int j = 0; j < GlobalVar.gridHeight; j++) {
                if (grid[i][j].getCellState() == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    /*
     * draw grid lines and map and all the contents
     */
    
    public void paint(Graphics g) {
        paintMap(g);
        for(int x = 0; x < GlobalVar.gridWidth; x++) {
            for (int y = 0; y < GlobalVar.gridHeight; y++) {
                grid[x][y].paint(g);
            }
        }
    }
    /*
     * draw map
     */
    public void paintMap(Graphics g) {
        g.setColor(Color.black);

        //draw vertical lines
        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            g.drawLine(position.x + i * GlobalVar.UNIT, 0, position.x + i * GlobalVar.UNIT, height);
        }
        //draw horizontal line
        for (int i = 0; i < GlobalVar.gridWidth; i++) {
            g.drawLine(0, position.y + i * GlobalVar.UNIT, width, position.y + i * GlobalVar.UNIT);
        }
    }
    /*
     * updates the validMoves
     */
    public void updateValidMoves(int playerID) {
        //remove all highlighted cell
        for (Position valid : validMoves) {
            grid[valid.x][valid.y].setHightlight(false);
        }
        //clear all old validMove
        validMoves.clear();
        // find valid moves for the current player
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y].getCellState() == 0
                        && getChangedPositionsForMove(new Position(x, y), playerID).size() > 0) {
                    validMoves.add(new Position(x, y));
                }
            }
        }
        // Visually update all valid move positions to show with a highlight
        for (Position validMove : validMoves) {
            grid[validMove.x][validMove.y].setHightlight(true);
        }
    }
    /*
     * check for all changed cell pos based on playing move
     */
    public List<Position> getChangedPositionsForMove(Position position, int playerID) {
        List<Position> result = new ArrayList<>();
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.DOWN));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.LEFT));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.UP));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.RIGHT));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.TOPRIGHT));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.LASTRIGHT));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.TOPLEFT));
        result.addAll(getChangedPositionsForMoveInDirection(position, playerID, Position.LASTLEFT));

        return result;
    }
    private List<Position> getChangedPositionsForMoveInDirection(Position position, int playerID, Position direction) {
        List<Position> result = new ArrayList<>();
        Position movingPos = new Position(position);
        int otherPlayer = playerID == 1 ? 2 : 1;
        movingPos.add(direction);
       
        while(inBounds(movingPos) && grid[movingPos.x][movingPos.y].getCellState() == otherPlayer) {
            result.add(new Position(movingPos));
            movingPos.add(direction);
        }
        
        if(!inBounds(movingPos) || grid[movingPos.x][movingPos.y].getCellState() != playerID) {
            result.clear();
        }
        return result;
    }
     /*
     * Test a given grid position if it is valid.
     *
     * @param position Grid position to test if valid.
     * @return True if the grid position is on the grid.
     */
    private boolean inBounds(Position position) {
        return !(position.x < 0 || position.y < 0 || position.x >= grid.length || position.y >= grid[0].length);
    }
}
