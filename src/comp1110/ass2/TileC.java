package comp1110.ass2;

import static comp1110.ass2.Color.*;
import static comp1110.ass2.Color.W;

//Author: Tanya Dixit

public class TileC extends Tile {

    /*
     * Color array tells what colors are in the tile
     * xOffsets are according to color and orientation.
     *
     * So for example, if we want to find where W is in TileC,
     * for orientation 1 it will be x+0
     *
     * yOffsets are just previous orientation's xOffsets
     */
    private Color[] colorArray = {G,R,R,W,B};
    private int[][] xOffsets = {{2,0,1,2,3},{1,0,0,0,0},{1,3,2,1,0},{0,1,1,1,1}};

    public TileC (TileType tileType, Orientation orientation, Location location) {
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
