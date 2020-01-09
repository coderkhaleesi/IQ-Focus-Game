package comp1110.ass2;

import static comp1110.ass2.Color.*;
import static comp1110.ass2.Color.W;

//Author: Tanya Dixit

public class TileI extends Tile{
    /*
     * Color array tells what colors are in the tile
     * xOffsets are according to color and orientation.
     *
     * So for example, if we want to find where W is in TileI,
     * for orientation 0 it will be x+1
     *
     * yOffsets are just previous orientation's xOffsets
     */

    private Color[] colorArray = {B,B,W};
    private int[][] xOffsets = {{0,1,1},{1,1,0},{1,0,0},{0,0,1}};

    public TileI (TileType tileType, Orientation orientation, Location location) {
        this.tileType = tileType;
        this.orientation = orientation;
        this.location = location;
    }

    @Override
    public int[] getXOffsets() {
        return xOffsets[this.orientation.getValue()];
    }

    @Override
    public Color[] getColors() {
        return colorArray;
    }

    @Override
    public int[] getYOffsets() {
        int length = 4; //number of orientations
        return xOffsets[(this.orientation.getValue()+length - 1)%length];
    }
}
