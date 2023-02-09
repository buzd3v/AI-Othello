import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JPanel;

public class GameMap extends JPanel implements MouseListener {
    public GameGrid gameGrid;
    public String gameStateStr;
    private AI ai;
    private GameMenu gameMenu;

    public GameState gameState;
    public GameMap(GameMenu gameMenu){
        setPreferredSize(new Dimension(GlobalVar.WIDTH, GlobalVar.HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        
        this.gameMenu = gameMenu;

        gameGrid = new GameGrid(new Position(0, 0), GlobalVar.WIDTH, GlobalVar.HEIGHT,this);
        ai = new AI(gameGrid,this);
        setGameState(GameState.BTurn);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameGrid.paint(g);
    }

    

    public void restart() {
        gameGrid.reset();
        setGameState(GameState.BTurn);
        gameGrid.grid[3][3].setCellState(1);
        gameGrid.grid[3][4].setCellState(2);
        gameGrid.grid[4][3].setCellState(2);
        gameGrid.grid[4][4].setCellState(1);
        gameGrid.updateValidMoves(2);
        repaint();

    }
    
    public void play(Position p) {
        // if (!gameGrid.isValidMove(p)) {
        //     return;
        // } else 
        if (gameState == GameState.WTurn) {
            gameGrid.playMove(p, 1);
            setGameState(GameState.BTurn);
        } else if (gameState == GameState.BTurn) {
            gameGrid.playMove(p, 2);
            setGameState(GameState.WTurn);
        }
    }

    public void undo() {
        if (gameState == GameState.WTurn) {
            gameGrid.undoMove(2);
            setGameState(GameState.BTurn);
        } else if (gameState == GameState.BTurn) {
            gameGrid.undoMove(1);
            setGameState(GameState.WTurn);
        }
        gameMenu.updateStatus();
    }
    private void setGameState(GameState newState) {
        gameState = newState;
        switch (gameState) {
            case WTurn:
                // If there are moves for the White player
                if(gameGrid.getAllValidMoves().size() > 0) {
                    gameStateStr = "White Player Turn";
                } else {
                    // No moves for the white player. Check the black player
                    gameGrid.updateValidMoves(2);
                    if(gameGrid.getAllValidMoves().size() > 0) {
                        // The black player has moves, swap back to them
                        setGameState(GameState.BTurn);
                    } else {
                        // No moves for either player found, end the game.
                        testForEndGame(false);
                    }
                }
                break;
            case BTurn:
                // If there are moves for the Black player
                if(gameGrid.getAllValidMoves().size() > 0) {
                    gameStateStr = "Black Player Turn";
                } else {
                    // No moves for the black player. Check the white player
                    gameGrid.updateValidMoves(1);
                    if(gameGrid.getAllValidMoves().size() > 0) {
                        // The white player has moves, swap back to them
                        setGameState(GameState.WTurn);
                    } else {
                        // No moves for either player found, end the game.
                        testForEndGame(false);
                    }
                }
                 break;
            case WWin: gameStateStr = "White Player Wins"; break;
            case BWin: gameStateStr = "Black Player Wins"; break;
            case Draw: gameStateStr = "Draw! "; break;
        }
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public int getCurrentPlayer() {
        if (gameState == GameState.WTurn) {
            return 1;
        }
        else {
            return 2;
        }
    }
    
    private void testForEndGame(boolean stillValidMoves) {
        int gameResult = gameGrid.getWinner(stillValidMoves);
        if(gameResult == 2) {
            setGameState(GameState.BWin);
        } else if(gameResult == 1) {
            setGameState(GameState.WWin);
        } else if(gameResult == 3) {
            setGameState(GameState.Draw);
        }
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (arg0.getButton() == MouseEvent.BUTTON1) {
            if (gameState == GameState.WTurn || gameState == GameState.BTurn) {
                Position p = gameGrid.convertMousePosition(new Position(arg0.getX(), arg0.getY()));
                System.out.println(p.toString());
                play(p);
                testForEndGame(true);
                gameMenu.updateStatus();

                while (gameState == GameState.WTurn) {
                    Position pai = ai.choseMove();
                    System.out.println(pai);
                    play(pai);
                    testForEndGame(true);
                    gameMenu.updateStatus();

                }
                repaint();
            }
            
        }
        if (arg0.getButton() == MouseEvent.BUTTON3) {
            undo();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(gameGrid.getGrid()[i][j].getCellState());
                    System.out.print(" ");
                }
                System.out.println();
            }
            repaint();
        }
        
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        // undo();
        // for (int i = 0; i < 8; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         System.out.print(gameGrid.getGrid()[i][j].getCellState());
        //         System.out.print(" ");
        //     }
        //     System.out.println();
        // }
        // repaint();

    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
}
