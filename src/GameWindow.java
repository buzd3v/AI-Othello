import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame implements KeyListener{

    private GameMap gMap = new GameMap();
    public GameWindow() {
        super();
    }

    public GameWindow(String title) {
        
        super();
        
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        this.add(gMap);

        this.pack();
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gMap.restart();
            gMap.repaint();
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
