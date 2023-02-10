import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMenu extends JPanel  {

    private GameMap gameMap;
    private JButton reset;
    public int white = 2;
    public int black = 2;

    private JLabel whiteStatus;
    private JLabel blackStatus;
    private JLabel gameState;
    private JLabel timeRespond;
    public GameMenu() {
        super();
        this.setLayout(new GridLayout(5, 1));
        reset = new JButton("RESET");
        reset.setFont(new Font("MesloLGL Nerd Font", Font.BOLD, 50));
        reset.setPreferredSize(new Dimension(700, 200));
        reset.setFocusable(false);

        //label for white and black status
        whiteStatus = new JLabel("WHITE: " + white);
        blackStatus = new JLabel("BLACK: " + black);
        timeRespond = new JLabel();
        String x = "Black player Turn"; 
        gameState = new JLabel(x);

        whiteStatus.setFont(new Font("monokai", Font.BOLD, 40));
        blackStatus.setFont(new Font("monokai", Font.BOLD, 40));
        gameState.setFont(new Font("monokai", Font.ITALIC, 30));
        timeRespond.setFont(new Font("monokai", Font.BOLD, 25));
        whiteStatus.setPreferredSize(new Dimension(300, 200));
        blackStatus.setPreferredSize(new Dimension(300, 200));
        gameState.setPreferredSize(new Dimension(300, 200));
        
        add(reset);
        add(gameState);
        add(blackStatus);
        add(whiteStatus);
        add(timeRespond);
        this.setPreferredSize(new Dimension(300, GlobalVar.HEIGHT));
        this.setBackground(Color.lightGray);
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                gameMap.restart();
                gameMap.restart();
            }

        });
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
    public void updateStatus()
    {
        black = gameMap.gameGrid.getNumberOfBlack();
        white = gameMap.gameGrid.getNumberOfWhite();
        blackStatus.setText("BLACK: " + black);
        whiteStatus.setText("WHITE: " + white);
        timeRespond.setText("Time:  " + Float.toString(gameMap.timeToChose));
        System.out.println("Time Elapsed Minimax " + Float.toString(gameMap.timeToChose));
        System.out.println("Time Elapsed Minimax With AB " + Float.toString(gameMap.timeToChoseAB));

        System.out.println("___________________________________________________");
        String x = gameMap.gameStateStr;
        gameState.setText(x);
    }


}
