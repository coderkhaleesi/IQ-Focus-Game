package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.Solution.SOLUTIONS;
import static org.junit.Assert.assertTrue;

public class PiecePlacementWellFormedTest {
    // Original duration < 0.05sec * 10 = 0.5sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String in, boolean expected) {
        boolean out = FocusGame.isPiecePlacementWellFormed(in);
        assertTrue("Input was '" + in + "', expected " + expected + " but got " + out, out == expected);
    }

    @Test
    public void fourCharacters() {
        test(SOLUTIONS[100].placement.substring(0, 4), true);
        test(SOLUTIONS[101].placement.substring(0, 2), false);
        test(SOLUTIONS[102].placement.substring(0, 4), true);
        test(SOLUTIONS[100].placement.substring(0, 6), false);
    }

    @Test
    public void firstCharacterOK() {
        for (int i = 0; i < SOLUTIONS[100].placement.length() - 4; i += 4) {
            test(SOLUTIONS[100].placement.substring(i + 0, i + 4), true);
            test(SOLUTIONS[100].placement.substring(i + 1, i + 5), false);
        }
        for (char c = 'Z'; c < 'z'; c++) {
            test(c + "123", c >= 'a' && c <= 'j');
        }

    }

    @Test
    public void secondCharacterOK() {
        for (int i = 0; i < SOLUTIONS[100].placement.length() - 4; i += 4) {
            test(SOLUTIONS[100].placement.substring(i + 0, i + 4), true);
            test(SOLUTIONS[100].placement.substring(i + 1, i + 5), false);
        }
        for (char c = '0'; c <= '9'; c++) {
            test("a" + c + "23", c >= '0' && c <= '8');
        }
    }

    @Test
    public void thirdCharacterOK() {
        for (int i = 0; i < SOLUTIONS[100].placement.length() - 4; i += 4) {
            test(SOLUTIONS[100].placement.substring(i + 0, i + 4), true);
            test(SOLUTIONS[100].placement.substring(i + 1, i + 5), false);
        }
        for (char c = '0'; c <= '9'; c++) {
            test("a4" + c + "0", c >= '0' && c <= '4');
        }
    }

    @Test
    public void fourthCharacterOK() {
        for (char p = 'a'; p <= 'j'; p++) {
            for (char r = '0'; r <= '9'; r++)
                test(p + "40" + r, r >= '0' && r <= '3');
        }
    }
}
