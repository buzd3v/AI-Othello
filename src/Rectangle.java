
/**
 * define a rectangle with position
 */

public class Rectangle {
    /*
     * positon of the top left corner
     */
    protected Position position;
    /*
     * width anf height
     */
    protected int width;
    protected int height;
    /*
     * constructor
     */
    public Rectangle(Position position, int width, int height) {
        this.position = position;
        this.height = height;
        this.width = width;
    }
    /*
     * constructor with x,y define for position
     */
    public Rectangle(int x, int y, int width, int height) {
        this(new Position(x, y), width, height);
    }

    public Rectangle(Rectangle another) {
        this.position.x = another.position.x;
        this.position.y = another.position.y;
        this.width = another.width;
        this.height = another.height;
    }
    /*
     * get the height, width, position
     */
    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Position getPosition() {
        return position;
    }

    /*
     * get the centre Positon of rect
     */
    public Position getCentre() {
        return new Position(position.x + width / 2, position.y + height / 2);
    }

    /*
     * test the another position is inside rectangle
     */
    public boolean isPositionInside(Position another) {
        return another.x >= position.x && another.y >= position.y
                && another.x < position.x + width && another.y < position.y + height;
    }
    
    /**
     * Tests the Rectangle is intersecting with some otherRectangle.
     *
     */
    public boolean isIntersecting(Rectangle otherRectangle) {
        if(position.y + height < otherRectangle.position.y) return false;
        if(position.y > otherRectangle.position.y + otherRectangle.height) return false;
        if(position.x + width < otherRectangle.position.x) return false;
        if(position.x > otherRectangle.position.x + otherRectangle.width) return false;

        return true;
    }
}
