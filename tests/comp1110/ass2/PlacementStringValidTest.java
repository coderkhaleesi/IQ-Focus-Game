package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.TestUtility.*;
import static org.junit.Assert.assertTrue;

public class PlacementStringValidTest {
    // original duration < 0.15 sec -> 1 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void test(String in, String invalid, boolean expected) {
        boolean out = FocusGame.isPlacementStringValid(in);
        assertTrue("Input was '" + in + "', expected " + expected + " but got " + out + (invalid == "" ? "" : " (subsequence " + invalid + " is not valid)"), out == expected);
    }

    @Test
    public void offBoardA() {
        for (int i = 0; i < SOLUTIONS.length; i++) {
            int badpiece = i % 5;
            String good = SOLUTIONS[i].placement.substring((badpiece * 4), (badpiece + 1) * 4);
            test(OFF_BOARD_1[badpiece], "", false);
            test(good, "", true);
            test(OFF_BOARD_2[badpiece], "", false);
        }
    }

    @Test
    public void offBoardB() {
        for (int i = 0; i < SOLUTIONS.length; i++) {
            int badpiece = i % 5;
            String bad = SOLUTIONS[i].placement.substring(0, (badpiece * 4)) + OFF_BOARD_2[badpiece];
            test(SOLUTIONS[i].placement, "", true);
            test(bad, OFF_BOARD_2[badpiece], false);
        }
        for (int i = 0; i < SOLUTIONS.length; i++) {
            int badpiece = 5 + (i % 5);
            String bad = OFF_BOARD_1[badpiece-5] + SOLUTIONS[i].placement.substring((badpiece * 4), SOLUTIONS[badpiece].placement.length());
            test(SOLUTIONS[i].placement, "", true);
            test(bad, OFF_BOARD_1[badpiece-5], false);
        }
    }

    @Test
    public void overlapA() {
        for (int i = 0; i < SOLUTIONS.length; i++) {
            int targeta = i % 4;
            int targetb = (i + 1) % 4;
            if (targeta == 3) {
                targeta = 0;
                targetb = 3;
            }
            String good = SOLUTIONS[i].placement.substring((targeta * 4), (targeta + 1) * 4) + SOLUTIONS[i].placement.substring((targetb * 4), (targetb + 1) * 4);
            test(good, "", true);
            test(OVERLAP[i % 4], "", false);
        }
    }

    /*
     * rotates a piece in a solution
     */
    @Test
    public void overlapB() {
        for (int i = 0; i < SOLUTIONS.length; i++) {
            String badPiece = "";
            String badPlacement = "";
            for (int p = 0; p < 10; p++) {
                badPiece = (SOLUTIONS[i].placement.substring(p * 4, p * 4 + 3) + (char) (SOLUTIONS[i].placement.charAt(p * 4 + 3) + 1));
                if (p == 0) {
                    badPlacement = badPiece + SOLUTIONS[i].placement.substring(4);
                } else {
                    badPlacement = SOLUTIONS[i].placement.substring(0, p * 4) + badPiece + SOLUTIONS[i].placement.substring(p * 4 + 4);
                }
                test(badPlacement, badPiece, false);
            }
            test(SOLUTIONS[i].placement, "", true);
        }
    }

    /*
     * adds one to the X dimension of a piece in a solution
     */
    @Test
    public void overlapC() {
        for (int i = 0; i < SOLUTIONS.length; i++) {
            String badPiece = "";
            String badPlacement = "";
            for (int p = 0; p < 10; p++) {
                badPiece = (SOLUTIONS[i].placement.substring(p * 4, p * 4 + 1)
                        + (char) (SOLUTIONS[i].placement.charAt(p * 4 + 1) + 1)
                        + SOLUTIONS[i].placement.substring(p * 4 + 2, p * 4 + 4));
                if (p == 0) {
                    badPlacement = badPiece + SOLUTIONS[i].placement.substring(4);
                } else {
                    badPlacement = SOLUTIONS[i].placement.substring(0, p * 4) + badPiece + SOLUTIONS[i].placement.substring(p * 4 + 4);
                }
                test(badPlacement, badPiece, false);
            }
            test(SOLUTIONS[i].placement, "", true);
        }
    }
}
