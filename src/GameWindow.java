import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame implements KeyListener{

    private GameMenu gameMenu = new GameMenu();
    private GameMap gameMap = new GameMap(gameMenu);
    public GameWindow() {
        super();
    }

    public GameWindow(String title) {
        
        super();
        gameMenu.setGameMap(gameMap);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.add(gameMap,BorderLayout.LINE_START);
        this.add(gameMenu, BorderLayout.LINE_END);

        this.pack();
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameMap.restart();
            gameMap.repaint();
        }
        
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
