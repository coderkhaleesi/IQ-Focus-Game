package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Set;
import java.util.TreeSet;

import static comp1110.ass2.TestUtility.*;
import static org.junit.Assert.assertTrue;

public class ViablePlacementsTest {
    // Original duration < 0.05 sec * 10 = 0.5 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String start, String objective, int xLoc, int yLoc, Set<String> expected) {
        Set<String> outSet = FocusGame.getViablePiecePlacements(start, objective, xLoc, yLoc);
        if (expected == null) {
            if (outSet != null)
                assertTrue("Expected null for placement " + start +
                        ", and objective " + objective + ", at (" + xLoc + ", " + yLoc + ")," +
                        " but got " + outSet.toString(), outSet == null);
        } else {
            String expstr = expected.toString();
            assertTrue("Got null for input " + start + ", but expected " + expstr, outSet != null);
            TreeSet<String> out = new TreeSet<>();
            out.addAll(outSet);
            String outstr = out.toString();
            assertTrue("Incorrect viable placements for input " + start +
                    ", and objective " + objective + ", at (" + xLoc + ", " + yLoc + ")," +
                    ".  Expected " + expstr + ", but got " + outstr, expstr.equals(outstr));
        }
    }

    /*
     * Add the following tests:
     * 1 - remove one piece placement from solutions,
     * the returned set should only include the removed piece placement
     * 2 - should return empty set for cases (handcrafted), where no viable placement is possible
     *  2.1 - no vp at all
     *  2.2 - some vps but none viable considering the objective
     * 3 - handcrafted cases with few possible vps (including vps on X<xloc or Y<yLoc)
     *  3.1 - around the 3*3 window
     *  3.2 - around the corners
     * 4 - a few cases with more vps
     */

    @Test
    public void test_lastP() {
        for (int i = 0; i < VP_LAST.length; i++) {
            if (VP_LAST[i].expected.length() == 0) {
                test(VP_LAST[i].start, VP_LAST[i].objective, VP_LAST[i].xLoc, VP_LAST[i].yLoc, null);
            } else {
                Set<String> expected = new TreeSet<>();
                for (int j = 0; j < VP_LAST[i].expected.length(); j += 4) {
                    expected.add(VP_LAST[i].expected.substring(j, j + 4));
                }
                test(VP_LAST[i].start, VP_LAST[i].objective, VP_LAST[i].xLoc, VP_LAST[i].yLoc, expected);
            }
        }
    }

    @Test
    public void test_centreP() {
        for (int i = 0; i < VP_CENTRE.length; i++) {
            if (VP_CENTRE[i].expected.length() == 0) {
                test(VP_CENTRE[i].start, VP_CENTRE[i].objective, VP_CENTRE[i].xLoc, VP_CENTRE[i].yLoc, null);
            } else {
                Set<String> expected = new TreeSet<>();
                for (int j = 0; j < VP_CENTRE[i].expected.length(); j += 4) {
                    expected.add(VP_CENTRE[i].expected.substring(j, j + 4));
                }
                test(VP_CENTRE[i].start, VP_CENTRE[i].objective, VP_CENTRE[i].xLoc, VP_CENTRE[i].yLoc, expected);
            }
        }
    }

    @Test
    public void test_sidesP() {
        for (int i = 0; i < VP_SIDES.length; i++) {
            if (VP_SIDES[i].expected.length() == 0) {
                test(VP_SIDES[i].start, VP_SIDES[i].objective, VP_SIDES[i].xLoc, VP_SIDES[i].yLoc, null);
            } else {
                Set<String> expected = new TreeSet<>();
                for (int j = 0; j < VP_SIDES[i].expected.length(); j += 4) {
                    expected.add(VP_SIDES[i].expected.substring(j, j + 4));
                }
                test(VP_SIDES[i].start, VP_SIDES[i].objective, VP_SIDES[i].xLoc, VP_SIDES[i].yLoc, expected);
            }
        }
    }

    @Test
    public void test_real() {
        for (int i = 0; i < VP_REAL.length; i++) {
            if (VP_REAL[i].expected.length() == 0) {
                test(VP_REAL[i].start, VP_REAL[i].objective, VP_REAL[i].xLoc, VP_REAL[i].yLoc, null);
            } else {
                Set<String> expected = new TreeSet<>();
                for (int j = 0; j < VP_REAL[i].expected.length(); j += 4) {
                    expected.add(VP_REAL[i].expected.substring(j, j + 4));
                }
                test(VP_REAL[i].start, VP_REAL[i].objective, VP_REAL[i].xLoc, VP_REAL[i].yLoc, expected);
            }
        }
    }
}
