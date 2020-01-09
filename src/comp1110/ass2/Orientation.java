package comp1110.ass2;

/*
 * Enum for representing orientation of tiles.
 * ZERO  - the original orientation
 * ONE   - get this after rotating ZERO 90 degrees clockwise
 * TWO   - get this after rotating ONE 90 degrees clockwise
 * THREE - get this after rotating TWO 90 degrees clockwise
 *
 * Author: Tanya Dixit
 */
public enum Orientation {
    ZERO, ONE, TWO, THREE;
    //* 0, 1, 2, 3 orientation of the tiles

    public int getValue() {
        if (this.equals(ONE))
            return 1;
        else if (this.equals(TWO))
            return 2;
        else if (this.equals(THREE))
            return 3;

        return 0;
    }
}
