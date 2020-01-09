package comp1110.ass2;

/*
 * This class is created for the FactoryPattern that we wanted to use.
 * Bascially, when we want to create a tile from a given placement, we just pass
 * the placement string to TileFactory believeing it will give us the right tile
 * type (because we have coded it like that).
 * This is a Design Pattern used in Computer Science called the Factory Pattern.
 *
 * Advantages - Very compact and easy to use code
 * Disadvantages - A little code repitition
 *
 * Author: Tanya Dixit
 */


public class TileFactory {

    /*
     * Return a tile object based on the placement passed to it.
     * This function looks at the placement string, decides which type
     * it is by looking at the first character, and creates and returns
     * a suitable tiletype object.
     *
     * @param placement: A string which has tiletype + x + y + orientation
     * @returns newTile: A Tile object based on the placement
     */
    public static Tile getTile(String placement) {

        TileType tileType = TileType.valueOf(Character.toString((placement.charAt(0) - 32)));
        Orientation orientation = placementToOrientation(placement);
        Location location = placementToLocation(placement);

        //return suitable tile object based on the TileType
        switch (tileType) {
            case A:
                Tile newTileA = new TileA(tileType, orientation, location);
                return newTileA;
            case B:
                Tile newTileB = new TileB(tileType, orientation, location);
                return newTileB;
            case C:
                Tile newTileC = new TileC(tileType, orientation, location);
                return newTileC;
            case D:
                Tile newTileD = new TileD(tileType, orientation, location);
                return newTileD;
            case E:
                Tile newTileE = new TileE(tileType, orientation, location);
                return newTileE;
            case F:
                Tile newTileF = new TileF(tileType, orientation, location);
                return newTileF;
            case G:
                Tile newTileG = new TileG(tileType, orientation, location);
                return newTileG;
            case H:
                Tile newTileH = new TileH(tileType, orientation, location);
                return newTileH;
            case I:
                Tile newTileI = new TileI(tileType, orientation, location);
                return newTileI;
            case J:
                Tile newTileJ = new TileJ(tileType, orientation, location);
                return newTileJ;
        }
        return null;
    }

    /*
     * A function to convert placement to orientation by looking at the
     * fourth character of the placement string.
     *
     * @param placement: A placement String tiletype + x + y + orientation
     * @returns Orientation : The suitable Orientation enum
     */
    public static Orientation placementToOrientation(String placement) {

        char x = placement.charAt(3);
        switch(x) {
            case('0'):
                return Orientation.ZERO;
            case ('1'):
                return Orientation.ONE;
            case ('2'):
                return Orientation.TWO;
            case ('3'):
                return Orientation.THREE;
        }
        return Orientation.ZERO;
    }

    public static Location placementToLocation(String placement) {

        int x = placement.charAt(1)-48; //ASCII encoding
        int y = placement.charAt(2)-48;
        return new Location(x, y);
    }
}
