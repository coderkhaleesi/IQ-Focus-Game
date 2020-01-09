package comp1110.ass2;

/*
 * This class encapsulates the location information of each tile.
 * x is the x position or the column information of the tile.
 * y is the y position or the row information of the tile.
 *
 * Author: Tanya Dixit
 */
public class Location {

    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
