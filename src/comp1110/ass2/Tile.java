package comp1110.ass2;


/*
 * This is the abstract class Tile that all the other tiles extend.
 *
 * Author: Tanya Dixit (Review Block 1)
 */

public abstract class Tile {

    /*
     * A tile has a tileType (see TileType enum)
     *            an orientation (see Orientation enum)
     *            a location (see Location class)
     */

    TileType tileType;
    Orientation orientation;
    Location location;

    public Location getLocation() {
        return location;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public TileType getTileType() {
        return tileType;
    }

    public abstract int[] getXOffsets();
    public abstract int[] getYOffsets();
    public abstract Color[] getColors();

}