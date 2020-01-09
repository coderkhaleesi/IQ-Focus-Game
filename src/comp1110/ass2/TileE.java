package comp1110.ass2;

import static comp1110.ass2.Color.*;
import static comp1110.ass2.Color.B;

//Author: Tanya Dixit

public class TileE extends Tile {
    /*
     * Color array tells what colors are in the tile
     * xOffsets are according to color and orientation.
     *
     * So for example, if we want to find where B is in TileE,
     * for orientation 0 it will be x+0, x+1 and x+2 for
     * different y
     *
     * yOffsets are just previous orientation's xOffsets
     */
    private Color[] colorArray = {B,B,B,R,R};
    private int[][] xOffsets = {{0,1,2,0,1},{1,1,1,0,0},{2,1,0,2,1},{0,0,0,1,1}};

    public TileE (TileType tileType, Orientation orientation, Location location) {
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
        int length = 4;//number of orientations
        return xOffsets[(this.orientation.getValue()+length - 1)%length];
    }
}
