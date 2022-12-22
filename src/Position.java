/*
 * define position x,y
 */
public class Position {
    public static final Position DOWN = new Position(0, 1);
    public static final Position UP = new Position(0, -1);
    public static final Position LEFT = new Position(-1, 0);
    public static final Position RIGHT = new Position(1, 0);
    public static final Position TOPRIGHT = new Position(-1, -1);
    public static final Position LASTRIGHT = new Position(-1, 1);
    public static final Position TOPLEFT = new Position(1, -1);
    public static final Position LASTLEFT = new Position(1, 1);

    public static final Position ZERO = new Position(0, 0);



    /*
     * x and y coord
     */
    public int x;
    public int y;
    /*
     * constructor
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position cpy) {
        this.x = cpy.x;
        this.y = cpy.y;
    }
    
    /*
     * set position to the specified x anf y COORD
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /*
     * add position
     */
    public void add(Position p) {
        this.x += p.x;
        this.y += p.y;
    }

    /*
     * calculate the distance to another Position
     */
    public double distanceTo(Position p) {
        return Math.sqrt(Math.pow((x - p.x), 2) + Math.pow(y - p.y, 2));
    }
    
    /*
     * multiply both component
     */
    public void multiply(int k) {
        x *= k;
        y *= k;
    }
    /*
     * subtract position
     */

    public void subtract(Position p) {
        this.x -= p.x;
        this.y -= p.y;
    }
    /*
     * 
     * get the string version of positon
     * ex : (x, y)
     */

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
