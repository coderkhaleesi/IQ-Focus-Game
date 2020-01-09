package comp1110.ass2;
import org.junit.Test;

import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.TestUtility.*;
import static org.junit.Assert.assertTrue;

/*
 * This test tests the function FocusGame.meetsObjective. This test includes testing of different objectives, and if any of the tile placements violated or not.
 * This helped me identify any potential bugs with not only the logic of my function, but also the tiles, their xOffsets. yOffsets and colors.
 * 
 * Author: Tanya Dixit
 */

public class MeetsObjectiveTest {

    private void test(String objective, String placement, boolean expected) {
        boolean out = FocusGame.meetsObjective(objective, placement);
        assertTrue("Expected "+ expected+" for placement  "+placement+" and Objective "+ objective + " but got "+out, out == expected);
    }

    static final String[] VIOLATES_OBJECTIVE_1 = {
            "a300",
            "a301",
            "b320",

            "b400",
            "c412",
            "f330",

            "h502",
            "i210",
            "j420",

            "e400",
            "b312",
            "d320",

            "a513",
            "b130",
            "c502",
            "d002",
            "e020",
            "f401",
            "g721",
            "h101",
            "i713",
            "j332"
    };

    @Test
    public void objectiveTestOneToFive(){
        //For Objective 1
        test(SOLUTIONS[0].objective, VIOLATES_OBJECTIVE_1[0],true);
        test(SOLUTIONS[0].objective, VIOLATES_OBJECTIVE_1[1],false);
        test(SOLUTIONS[0].objective, VIOLATES_OBJECTIVE_1[2],false);

        //For Objective 2
        test(SOLUTIONS[1].objective, VIOLATES_OBJECTIVE_1[3],true);
        test(SOLUTIONS[1].objective, VIOLATES_OBJECTIVE_1[4],false);
        test(SOLUTIONS[1].objective, VIOLATES_OBJECTIVE_1[5],true);

        //For Objective 3
        test(SOLUTIONS[2].objective, VIOLATES_OBJECTIVE_1[6],true);
        test(SOLUTIONS[2].objective, VIOLATES_OBJECTIVE_1[7],true);
        test(SOLUTIONS[2].objective, VIOLATES_OBJECTIVE_1[8],false);

        //For Objective 4
        test(SOLUTIONS[3].objective, VIOLATES_OBJECTIVE_1[9],true); //This test helped me identify a bug in my code and fix it. Go tests!!!
        test(SOLUTIONS[3].objective, VIOLATES_OBJECTIVE_1[10],false);

        //For Objective 5
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[13],true);
        test(SOLUTIONS[3].objective, VIOLATES_OBJECTIVE_1[11],false);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[14],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[15],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[12],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[16],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[17],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[18],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[19],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[20],true);
        test(SOLUTIONS[4].objective, VIOLATES_OBJECTIVE_1[21],true);
    }

    static final String[] VIOLATES_OBJECTIVE_2 = {
                "d621",
                "f500",
                "e311",

                "i400",
                "j003",
                "e223",

                "b513",
                "c613",
                "d400",
                "e013",
                "f411"
    };

    @Test
    public void objectiveTestTwentyToThirty(){

        //Objective 21
        test(SOLUTIONS[20].objective, VIOLATES_OBJECTIVE_2[0],true);
        test(SOLUTIONS[20].objective, VIOLATES_OBJECTIVE_2[1],true);
        test(SOLUTIONS[20].objective, VIOLATES_OBJECTIVE_2[2],false);

        //Objective 25
        test(SOLUTIONS[24].objective, VIOLATES_OBJECTIVE_2[3],true);
        test(SOLUTIONS[24].objective, VIOLATES_OBJECTIVE_2[4],true);
        test(SOLUTIONS[24].objective, VIOLATES_OBJECTIVE_2[5],false);

        //Objective 29
        test(SOLUTIONS[28].objective, VIOLATES_OBJECTIVE_2[6],true);
        test(SOLUTIONS[28].objective, VIOLATES_OBJECTIVE_2[7],true);
        test(SOLUTIONS[28].objective, VIOLATES_OBJECTIVE_2[8],true);
        test(SOLUTIONS[28].objective, VIOLATES_OBJECTIVE_2[9],true);
        test(SOLUTIONS[28].objective, VIOLATES_OBJECTIVE_2[10],true);

    }

}