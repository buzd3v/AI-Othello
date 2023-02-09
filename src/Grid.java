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

    public Grid(Grid another) {
        super(another.getPosition(),another.getWidth(),another.getHeight());
        this.cellState = another.cellState;
        this.height = another.height;
        this.width = another.width;
        this.position.setPosition(another.getPosition().x,another.getPosition().y);
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

     public boolean getHighlighted() {
         return this.highlighted;
     }

    
     public void paint(Graphics g) {
         if (highlighted) {
             
                 g.setColor(Color.yellow);
                 g.fillOval(position.x + GlobalVar.UNIT / 4, position.y + GlobalVar.UNIT / 4,
                         GlobalVar.UNIT / 2, GlobalVar.UNIT / 2);
             
             
         }
         if (cellState == 0)
             return;
         g.setColor(cellState == 1 ? Color.WHITE : Color.BLACK);
         g.fillOval(position.x, position.y, width, height);    
    }
    
}
