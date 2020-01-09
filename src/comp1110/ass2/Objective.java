package comp1110.ass2;

import java.util.Random;

/**
 * This class will encode all the game's objectives. It has
 * 9 characters in each objective.
 *
 * Author: Tanya Dixit
 */

public class Objective {

    /* Based on the difficulty, return an objective String
     * from the list of Objectives. This list is taken from Solution
     * class.
     *
     * @param - difficulty: The value gotten from the slider in the user interface
     * @return - objective: The objective string based on the difficulty level
     *                   0-24 is starter
     *                   25-48 is junior
     *                   49-72 is expert
     *                   73-96 is master
     *                   97-120 is wizard (most difficult level)
     */
    public static String newObjective(int difficulty) {

        assert difficulty >= 0 && difficulty <= 4;

        /* Generate random number between 0 and 23 for selecting
         * random objectives within a difficulty level.
         */
        Random rand = new Random();
        int objectiveNo = 0;
        int randomNo = rand.nextInt(23-0);

        switch (difficulty) {
            case (0):
                objectiveNo = randomNo + 0;
                break;
            case (1):
                objectiveNo = randomNo + 24;
                break;
            case(2):
                objectiveNo = randomNo + 48;
                break;
            case(3):
                objectiveNo = randomNo + 72;
                break;
            case(4):
                objectiveNo = randomNo + 96;
        }

        return Solution.SOLUTIONS[objectiveNo].objective;
    }
}