package comp1110.ass2;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
 * This test is for testing the function FocusGame.containsRowCol. To test thoroughly, we have added tests
 * for every Tile Type - A,B,C,D,E,F,G,H,I,J. This test implicitly helps us test all the x offsets and y offsets
 * of each tile. Hence, it's an important test.
 *
 * For each placement, we have a (x,y) that is contained in the placement, and one which is not.
 * This is the basis for structuring PLACEMENTS, rowNumbers and colNumbers arrays.
 *
 * Author: Tanya Dixit
 */

public class ContainsRowColTest {

    private void test(int row, int col, String placement, boolean expected) {
        boolean out = FocusGame.containsRowCol(row, col, placement);
        assertTrue("Expected "+ expected+" for row:  "+row+", col: "+ col+" and placement: "+placement+ " but got "+out, out == expected);
    }

    static final String[] PLACEMENTS = {
            //For TileType A
            "a022",
            "a400",

            //For TileType B
            "b222",
            "b703",

            //For TileType C
            "c103",
            "c330",

            //For TileType D
            "d620",
            "d621",

            //For TileType E
            "e220",
            "e713",

            //For TileType F
            "f340",
            "f723",

            //For TileType G
            "g010",
            "g013",

            //For TileType H
            "h300",
            "h602",

            //For TileType I
            "i030",
            "i733",

            //For TileType J
            "j500",
            "j011"
    };

    static final int[][] rowNumbers = {
            //a - {true, false}
            {2,3},
            {0,1},

            //b - {true, false}
            {3,4},
            {2,1},

            //c - {true, false}
            {0,4},
            {4,2},

            //d - {true, false}
            {3,7},
            {2,3},

            //e - {true, false}
            {3,4},
            {1,0},

            //f - {true, false}
            {4,3},
            {2,1},

            //g - {true, false}
            {1,1},
            {3,1},

            //h - {true, false}
            {1,0},
            {0,2},

            //i - {true, false}
            {3,2},
            {4,3},

            //j - {true, false}
            {1,1},
            {1,2}
    };

    static final int[][] colNumbers = {

            //a - {true, false}
            {1, 3},
            {5, 4},

            //b - {true, false}
            {2, 2},
            {7, 8},

            //c - {true, false}
            {2, 2},
            {6, 7},

            //d - {true, false}
            {8, 1},
            {7, 6},

            //e - {true, false}
            {2, 3},
            {7, 5},

            //f - {true, false}
            {5, 4},
            {7, 8},

            //g - {true, false}
            {0, 2},
            {0, 0},

            //h - {true, false}
            {3,1},
            {8,4},

            //i - {true, false}
            {0,1},
            {7,5},

            //j - {true, false}
            {5,6},
            {0,0}
    };

    //For Tile Type A
    @Test
    public void rowColTestA(){
        test(rowNumbers[0][0], colNumbers[0][0], PLACEMENTS[0],true);
        test(rowNumbers[0][1], colNumbers[0][1], PLACEMENTS[0], false);
        test(rowNumbers[1][0], colNumbers[1][0], PLACEMENTS[1],true);
        test(rowNumbers[1][1], colNumbers[1][1], PLACEMENTS[1],false);
    }

    //For Tile Type B
    @Test
    public void rowColTestB(){
        test(rowNumbers[2][0], colNumbers[2][0], PLACEMENTS[2],true);
        test(rowNumbers[2][1], colNumbers[2][1], PLACEMENTS[2],false);
        test(rowNumbers[3][0], colNumbers[3][0], PLACEMENTS[3],true);
        test(rowNumbers[3][1], colNumbers[3][1], PLACEMENTS[3],false);
    }

    //For Tile Type C
    @Test
    public void rowColTestC(){
        test(rowNumbers[4][0], colNumbers[4][0], PLACEMENTS[4],true);
        test(rowNumbers[4][1], colNumbers[4][1], PLACEMENTS[4],false);
        test(rowNumbers[5][0], colNumbers[5][0], PLACEMENTS[5],true);
        test(rowNumbers[5][1], colNumbers[5][1], PLACEMENTS[5], false);
    }

    @Test
    public void rowColTestD(){
        test(rowNumbers[6][0], colNumbers[6][0], PLACEMENTS[6], true);
        test(rowNumbers[6][1], colNumbers[6][1], PLACEMENTS[6],false);
        test(rowNumbers[7][0], colNumbers[7][0], PLACEMENTS[7],true);
        test(rowNumbers[7][1], colNumbers[7][1], PLACEMENTS[7], false);
    }

    @Test
    public void rowColTestE() {
        test(rowNumbers[8][0], colNumbers[8][0], PLACEMENTS[8], true);
        test(rowNumbers[8][1], colNumbers[8][1], PLACEMENTS[8], false);
        test(rowNumbers[9][0], colNumbers[9][0], PLACEMENTS[9], true);
        test(rowNumbers[9][1], colNumbers[9][1], PLACEMENTS[9], false);
    }

    @Test
    public void rowColTestF() {
        test(rowNumbers[10][0], colNumbers[10][0], PLACEMENTS[10], true);
        test(rowNumbers[10][1], colNumbers[10][1], PLACEMENTS[10], false);
        test(rowNumbers[11][0], colNumbers[11][0], PLACEMENTS[11], true);
        test(rowNumbers[11][1], colNumbers[11][1], PLACEMENTS[11], false);
    }

    @Test
    public void rowColTestG() {
        test(rowNumbers[12][0], colNumbers[12][0], PLACEMENTS[12], true);
        test(rowNumbers[12][1], colNumbers[12][1], PLACEMENTS[12], false);
        test(rowNumbers[13][0], colNumbers[13][0], PLACEMENTS[13], true);
        test(rowNumbers[13][1], colNumbers[13][1], PLACEMENTS[13], false);
    }

    @Test
    public void rowColTestH() {
        test(rowNumbers[14][0], colNumbers[14][0], PLACEMENTS[14], true);
        test(rowNumbers[14][1], colNumbers[14][1], PLACEMENTS[14], false);
        test(rowNumbers[15][0], colNumbers[15][0], PLACEMENTS[15], true);
        test(rowNumbers[15][1], colNumbers[15][1], PLACEMENTS[15], false);
    }

    @Test
    public void rowColTestI() {
        test(rowNumbers[16][0], colNumbers[16][0], PLACEMENTS[16], true);
        test(rowNumbers[16][1], colNumbers[16][1], PLACEMENTS[16], false);
        test(rowNumbers[17][0], colNumbers[17][0], PLACEMENTS[17], true);
        test(rowNumbers[17][1], colNumbers[17][1], PLACEMENTS[17], false);
    }

    @Test
    public void rowColTestJ() {
        test(rowNumbers[18][0], colNumbers[18][0], PLACEMENTS[18], true);
        test(rowNumbers[18][1], colNumbers[18][1], PLACEMENTS[18], false);
        test(rowNumbers[19][0], colNumbers[19][0], PLACEMENTS[19], true);
        test(rowNumbers[19][1], colNumbers[19][1], PLACEMENTS[19], false);
    }
}
