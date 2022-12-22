import java.awt.Window;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

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
        this.setVisible(true);
    }
    
}
