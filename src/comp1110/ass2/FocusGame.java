package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static comp1110.ass2.Color.EMPTY;
import static comp1110.ass2.Color.FORBIDDEN;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */


public class FocusGame {

    private static final int[] objectiveX = {3,4,5,3,4,5,3,4,5};
    private static final int[] objectiveY = {1,1,1,2,2,2,3,3,3};

    private static final int[] boardX = {0,1,2,3,4,5,6,7,8,0,1,2,6,7,8,0,1,2,6,7,8,0,1,2,6,7,8,1,2,3,4,5,6,7};
    private static final int[] boardY = {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3,4,4,4,4,4,4,4,};


    /*
     * This is an array representation of the board. We initialize with color enums EMPTY and FORBIDDEN
     * to signify that the place is EMPTY (no tile is occupying that (x,y) and FORBIDDEN (you can't place
     * anything at this place) according to the template in the original game.
     */
    private static Color[][] boardStates = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {FORBIDDEN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FORBIDDEN}
    };

    /* The objective represents the problem to be solved in this instance of the game. */
    private String objective;

 //Author: Tanya Dixit
    /*
     * This is a function to update the static variable boardStates mentioned above.
     *
     * @param piecePlacement: The String that makes up all the tiles that are placed
     *                        with their types, x,y and orientation.
     * @return: void (This function causes an effect, doesn't return anything.
     */

    /*
     * The two constructors of FocusGame
     */
    public FocusGame(int difficulty) {
        this.objective = Objective.newObjective(difficulty);
    }

    public FocusGame() {
    }

        public static void updateBoardStates(String piecePlacement) {
        /*-
         * Update the board state according to the tile placement. Also, check here if there is any overlap,
         * or objective is being violated or the tile is getting placed outside the game.
         */
        for (int i = 0; i < piecePlacement.length()/4; i++) {
            Tile tile;

            /*
             * Important note about the Tile Factory class: It returns the tile
             * according to the type automatically. That means we don't need to worry about
             * the type, and that logic is abstracted away. We only need to check the x and y
             * offsets now, and the colors at those points. The number of points changes or
             * affected by a tile placement can be gotten using length of xOffset array.
             */
            tile = TileFactory.getTile(piecePlacement.substring(4 * i, 4 * i + 4));

            int[] xOffsets = tile.getXOffsets();
            int[] yOffsets = tile.getYOffsets();
            Color[] colors = tile.getColors();
            int x = tile.location.getX();
            int y = tile.location.getY();

            for (int j = 0; j < xOffsets.length; j++) {
                /*
                 * Now, let's try to check overlaps. If the tile place is empty, we can update the
                 * colors according to the tile placement, and all of this information we get from our
                 * tile classes.
                 */
                if (boardStates[y+yOffsets[j]][x+xOffsets[j]] == EMPTY) {
                    boardStates[y + yOffsets[j]][x + xOffsets[j]] = colors[j];
                }
            }
        }
    }

    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    public static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 2: determine whether a piece placement is well-formed
        //Author: Mahesh Gundubogula (Review Block 1)
        if (piecePlacement.length() != 4)
            return false;

        //checking for allowed tileTypes according to the chars of the placement String
        if ((piecePlacement.charAt(0)-'a') < 0 || (piecePlacement.charAt(0) - 'a') > 9)
            return false;

        //checking the allowed column or x value allowed according to the length of each row of the board
        if (Character.getNumericValue(piecePlacement.charAt(1)) < 0 || Character.getNumericValue(piecePlacement.charAt(1)) > 8)
            return false;

        //checking the allowed row or y value allowed according to the length of each column of the board
        if(Character.getNumericValue(piecePlacement.charAt(2)) < 0 || Character.getNumericValue(piecePlacement.charAt(2)) > 4)
            return false;

        //checking the allowed orientations
        if(Character.getNumericValue(piecePlacement.charAt(3)) < 0 || Character.getNumericValue(piecePlacement.charAt(3)) > 3)
            return false;


        return true;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) {

        //Author: Mahesh Gundubogula (Review block 2)
        char[] shapeArray = new char[10];

        if (placement.length() == 0)
            return false;
        int length = placement.length();
        if (length%4 != 0 || length/4 < 0 || length/4 > 10)
            return false;

        for (int i = 0; i < length/4; i++) {
            if (!isPiecePlacementWellFormed(placement.substring(4 * i, 4 * i + 4)))
                return false;
        }

        for (int i = 0; i < length/4; i++) {
            shapeArray[placement.charAt(4 * i) - 'a']++;
        }

        for (int i = 0; i < shapeArray.length; i++)
            if (shapeArray[i] > 1)
                return false;

        return true;
    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     *
     * Author: Tanya Dixit
     */

    public static boolean isPlacementStringValid(String placement) {
        /*
         * Using a local board state matrix for now because we don't know yet if
         * this piece should actually be placed on the board. So, just to check, we
         * are using a local board state to verify if the placement overlaps or
         * goes off the board.
         */
        Color[][] boardStates = {
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {FORBIDDEN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FORBIDDEN}
        };

        /*
         * First we need to check if the placement string is well-formed meaning
         * it doesn't contain any repeat tile types and it follows the pattern
         * of tile type + x position + y position + orientation.
         */
        if (!isPlacementStringWellFormed(placement))
            return false;

        /*
         * Now we go into the heart of the function. This function's aim is to tell
         * us if any placement is going off board, or are the tiles in this placement
         * overlapping with each other or if it's going into the FORBIDDEN regions or
         * if it's going out of the board. We can check that by looping all of the tile
         * placements and checking for each tile if the above conditions are violated.
         * And since we have multiple tiles, that's why we need to save state using boardStates
         * array, so that we can check overlaps.
         */
        for (int i = 0; i < placement.length()/4; i++) {
            Tile tile;
            /*
             * Creating a tile for eah 4 characters in the whole placement. This
             * makes our code easier to read and smaller to write. Also, makes
             * it more efficient.
             */

            tile = TileFactory.getTile(placement.substring(4*i,4*i+4));

            int[] xOffsets = tile.getXOffsets();
            int[] yOffsets = tile.getYOffsets();
            Color[] colors = tile.getColors();
            int x = tile.location.getX();
            int y = tile.location.getY();

            for (int j = 0; j < xOffsets.length; j++) {

                /*First we are checking if the pieces go off-board or not. For this, either
                 *the tile's box is on FORBIDDEN or any index out of the board is accessed.
                 *In both cases, we return false. We use a clever try, catch clause because
                 * we know if we try to access any position that is out of bounds for the array,
                 * we go off the board as well, which will raise an IndexOutOfBoundsException.
                 */
                try {
                    if (boardStates[y+yOffsets[j]][x+xOffsets[j]] == FORBIDDEN){
                        return false;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {
                    return false; //if exception is raised, means the tile goes off the board, so return false
                }

                /*
                 * Now, let's try to check overlaps.
                 */
                if (boardStates[y+yOffsets[j]][x+xOffsets[j]] == EMPTY) {
                    boardStates[y + yOffsets[j]][x + xOffsets[j]] = colors[j];
                }
                else
                    return false;
            }
        }

        return true;
    }

    /*
     * This function checks if the given String placament is violating the objective or not. If it
     * is then that placement would not be a viable placement to consider for the next steps.
     * To check the same, see if the placement of any of the tiles in the placement is hurting the objective.
     *
     * The objective is a 9 char long String that identifies the color of the boxes in the positions
     * (3,1) to (3,3), (4,1) to (4,3) and (5,1) to (5,3). And that's what we check in this function.
     *
     * @param objective: This is the objective string, the challenge of the game.
     * @param placement: The placement string is the placement we are checking against the objective
     *
     * @return: boolean value telling whether the placement meets the objective or not. Returns true for meets, and
     *          false if it doesn't meet.
     *
     * Author: Tanya Dixit
     */
    static boolean meetsObjective(String objective, String placement) {

        Color[][] boardStates = {
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {FORBIDDEN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FORBIDDEN}
        };
        /*
         * The placement string is a 4 char long string. We need to check if the objective is violated or not
         * with such tile placement.
         */
        int k = 0;
        // place the objective first
        for (int i = 1; i < 4; i++) {
            for (int j = 3; j < 6; j++) {
                boardStates[i][j] = Color.valueOf(String.valueOf(objective.charAt(k)));
                k++;
            }
        }

        Tile tile;
        tile = TileFactory.getTile(placement);
        int[] xOffsets = tile.getXOffsets();
        int[] yOffsets = tile.getYOffsets();
        Color[] colors = tile.getColors();
        int x = tile.location.getX();
        int y = tile.location.getY();

        // now check if the tile placements are violating the placements
        for (int i = 0; i < xOffsets.length; i++) {
            if (boardStates[y+yOffsets[i]][x+xOffsets[i]] == EMPTY)
                continue;
            else if (boardStates[y+yOffsets[i]][x+xOffsets[i]]!= EMPTY && boardStates[y+yOffsets[i]][x+xOffsets[i]] != colors[i])
                return false;
        }

        return true;
    }

    /*
     * This function takes in a placement string of 4 characters, a row number and a col number.
     * It returns true or false based on whether the tile covers the (column, row) -> (x, y) as part of its placement.
     *
     * Using the tile x and y offsets and looping over the same, we can easily check the conditions as done below.
     *
     * @param    row:       The row number we need to check if the tile contains
     * @param    col:       The col number we need to check if the tile contains
     * @param    placement: The placement we need to check it on
     *
     * @returns    boolean: True if this placement is covering the (row,col) given. False if it's not.
     * Author: Tanya Dixit
     */

    static boolean containsRowCol (int row, int col, String placement) {

        boolean flag = false;
        Tile tile = TileFactory.getTile(placement);
        int[] xOffsets = tile.getXOffsets();
        int[] yOffsets = tile.getYOffsets();
        int x = tile.location.getX();
        int y = tile.location.getY();

        for (int i = 0; i < xOffsets.length; i++) {
            if ((x+xOffsets[i])==col && (y+yOffsets[i])==row)
                flag = true;
        }

        return flag;
    }

// For task 6, we need an updateBoardStates as well as a meetsObjective, then search with all possible string combinations.
    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     *
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     * which represents the color of the 3*3 central board area
     * squares indexed as follows:
     * [0] [1] [2]
     * [3] [4] [5]
     * [6] [7] [8]
     * each character may be any of
     * - 'R' = RED square
     * - 'B' = Blue square
     * - 'G' = Green square
     * - 'W' = White square
     * @param col The cell's column.
     * @param row The cell's row..3 *
     * @return A set of viable piece placements, or null if there are none.
     *
     * Author: Tanya Dixit
     */

    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {

        Set<String> returnSet = new HashSet<>();
        updateBoardStates(placement);

        //create a list of all possible characters (tile types) in a placement string
        List<Character> chars = new ArrayList<Character>();
        chars.add('a');chars.add('b');chars.add('c');chars.add('d');chars.add('e');chars.add('f');chars.add('g');
        chars.add('h');chars.add('i');chars.add('j');

        if (placement.length()!=0) {

            char b = placement.charAt(0);
            chars.remove((Character) b);

            //Remove the tile types already placed
            for (int i = 1; i < placement.length() / 4; i++) {
                char a = placement.charAt(4 * i);
                chars.remove((Character) a);

            }
        }
        /* Now we have a set which contains the unplaced tile types
         * start enumerating all the possibilities in terms of tile types, x, y placements and orientation
         */

        for (int i = 0; i < chars.size(); i++) {
            char a = chars.get(i);

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 5; y++) {
                    if (y == 4 && (x==0 || x==8))
                        continue;

                    for (int o = 0; o < 4; o++) {
                        String s = a+Integer.toString(x)+y+o;

                        /* check if it's a valid placement
                         * check if it contains the col and row given
                         * check if it violates objective
                         * If all the above are satisfied, add the String to the Set to be returned
                         */
                        if (isPlacementStringValid(s) && containsRowCol(row, col, s) && meetsObjective(challenge,s)) {
                            if (isPlacementStringValid(placement+s))
                                returnSet.add(s);
                        }
                    }
                }
            }
        }

        if(returnSet.size() == 0)
            return null;

        return returnSet;
    }

    /*
     * If returnRowColNumber returns null, then in the calling function, we should consider that we
     * have found a solution, because this means that no point in the board is empty.
     *
     * Author: Tanya Dixit
     */

    private static int[] returnRowColNumber(){

        int[] result = {0,0};
        for (int i = 0; i < objectiveX.length; i++) {
            if (boardStates[objectiveY[i]][objectiveX[i]] == EMPTY) {
                result[0] = objectiveX[i];
                result[1] = objectiveY[i];
                return result;
            }
        }

        for (int i = 0; i < boardX.length; i++) {
            if (boardStates[boardY[i]][boardX[i]] == EMPTY) {
                result[0] = boardX[i];
                result[1] = boardY[i];
                return result;
            }
        }

        return null;
    }

    /*
     * This is a recursive function that finds the solutions of a particular objective.
     *
     * @param placedNow: The String that has been placed till now
     * @param challenge: The objective string
     *
     * @return String: Solution string formed till now with the tiles placed
     *
     * Author: Tanya Dixit (Review Block 3)
     */

    public static String findSolutionsRecursively(String placedNow, String challenge) {

        int[] rowCol = {-1,-1};
        Color[][] boardStates = {
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {FORBIDDEN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FORBIDDEN}
        };

        for (int i = 0; i < placedNow.length()/4; i++) {
            Tile tile;
            tile = TileFactory.getTile(placedNow.substring(4 * i, 4 * i + 4));

            int[] xOffsets = tile.getXOffsets();
            int[] yOffsets = tile.getYOffsets();
            Color[] colors = tile.getColors();
            int x = tile.location.getX();
            int y = tile.location.getY();

            for (int j = 0; j < xOffsets.length; j++) {
                boardStates[y + yOffsets[j]][x + xOffsets[j]] = colors[j];
            }
        }

        //This gives row and col number that's not occupied.
        //We first search in the objective array
        for (int i = 0; i < objectiveX.length; i++) {
            if (boardStates[objectiveY[i]][objectiveX[i]] == EMPTY) {
                rowCol[0] = objectiveX[i];
                rowCol[1] = objectiveY[i];
            }
        }

        //This gives row and col number that's not occupied
        for (int i = 0; i < boardX.length; i++) {
            if (boardStates[boardY[i]][boardX[i]] == EMPTY) {
                rowCol[0] = boardX[i];
                rowCol[1] = boardY[i];

            }
        }

        /*
         * If all positions are occupied, we got our solution
         */
        if (rowCol[0] == -1 && rowCol[1] == -1)
            return placedNow;

        Set<String> currentViablePlacements = getViablePiecePlacements(placedNow, challenge, rowCol[0], rowCol[1]);

        if (currentViablePlacements == null) {
            return null;
        }

        /*
         * Loop over the viable placememts and check if more viablePlacements are there
         * eventually leading to solution
         */
        for (String s: currentViablePlacements) {

            String x = findSolutionsRecursively(placedNow + s, challenge);
            if (x == null) {
                //If no solutions with this string, don't add it, return null
                continue;
            } else {
                //return current string appended by x
                return s+x;
            }
        }

        return null;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     *
     * A given challenge can only solved with a single placement of pieces.
     *
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     * orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     *
     * Author: Tanya Dixit
     */

    public static String getSolution(String challenge) {
        String solutionString = "";

        solutionString = findSolutionsRecursively("",challenge);

        if (solutionString==null)
            return null;
        String solutionStringNew = solutionString.substring(0, 40);

        char[] ss = solutionStringNew.toCharArray();

        //This is logic for returning only canonical forms of tiles that are symmetric on rotation
        for (int i = 0; i < ss.length; i+=4) {
            if (ss[i] == 'f' || ss[i] == 'g') {
                if (ss[i+3] == '2')
                    ss[i+3] = '0';
                if (ss[i+3] == '3')
                    ss[i+3] = '1';
            }
        }

        String finalSolution = new String(ss);

        System.out.println(finalSolution);
        return finalSolution;
    }
}