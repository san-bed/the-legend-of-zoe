package Main;

/**
 * Generic class to represent a (x, y) position.
 * Used in the game to store the position of the actives and the blocks.
 */
public class Position {
    private int x, y;

    // Constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
