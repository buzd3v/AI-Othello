import java.awt.Color;
import java.awt.Graphics;

public class Grid extends Rectangle {
    /*
     * the state of grid 
     * 0 = empty , 1= white, 2 = black
     */
    private int cellState;
    /*
     * highlighted if a valid move
     */
    private boolean highlighted;

    public Grid(Position position, int width, int height) {
        super(position, width, height);
        reset();
    }
    /*
     * reset to empty
     */
    public void reset() {
        cellState = 0;
        highlighted = false;
    }
    /*
     * change the state
     */
    public void setCellState(int newstate) {
        this.cellState = newstate;
    }
    /*
     * get the cell state
     */

    public int getCellState() {
        return cellState;
    }
     /*
      * set hightlight to true or false
      */
     public void setHightlight(boolean t) {
         this.highlighted = t;
     }
    
     public void paint(Graphics g) {
         if (highlighted) {
             g.setColor(Color.ORANGE);
             g.fillRect(position.x, position.y, width, height);
         }
         if (cellState == 0)
             return;
         g.setColor(cellState == 1 ? Color.WHITE : Color.BLACK);
         g.fillOval(position.x, position.y, width, height);    
    }

}
