import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.JPanel;

public class GameMap extends JPanel implements MouseListener {
    private GameGrid gameGrid;
    private String gameStateStr;
    private AI ai;
    public enum GameState {
        WTurn, BTurn,BWin, WWin, Draw
    };

    GameState gameState;
    public GameMap(){
        setPreferredSize(new Dimension(GlobalVar.WIDTH, GlobalVar.HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        
        gameGrid = new GameGrid(new Position(0, 0), GlobalVar.WIDTH, GlobalVar.HEIGHT);
        ai = new AI(gameGrid);
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

    }
    
    private void play(Position p) {
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
        
        if (gameState == GameState.WTurn || gameState == GameState.BTurn) {
            Position p = gameGrid.convertMousePosition(new Position(arg0.getX(), arg0.getY()));
            System.out.println(p.toString());
            play(p);
            testForEndGame(true);
            while (gameState == GameState.WTurn ) {
                Position pai = ai.choseMove();
                play(pai);
                testForEndGame(true);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

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
