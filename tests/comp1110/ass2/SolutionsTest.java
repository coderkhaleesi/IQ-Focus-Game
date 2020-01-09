package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import static comp1110.ass2.Solution.SOLUTIONS;
import static org.junit.Assert.assertTrue;

public class SolutionsTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(120000);


    private void test(String objective, Set<Set<String>> expected) {
        String out = FocusGame.getSolution(objective);
        assertTrue("No solutions returned for objective " + objective + ", expected one of " + expected, out != null);
        TreeSet<String> outSet = new TreeSet<>();
        for (int i = 0; i < out.length(); i += 4) {
            outSet.add(out.substring(i, i + 4));
        }
        String outstr = outSet.toString();
        boolean found = false;
        for (Set<String> exp : expected) {
            String expstr = exp.toString();
            if (outstr.equals(expstr)) {
                found = true;
            }
        }
        assertTrue("For objective " + objective + ", was expecting one of " + expected + ", but got " + outstr, found);
    }

    @Test
    public void test_starter() {
        for (int i = 0; i < (SOLUTIONS.length / 5); i++) {
            Set<Set<String>> exp = new LinkedHashSet<>();
            for (String sol : SOLUTIONS[i].placements) {
                TreeSet<String> outSet = new TreeSet<>();
                for (int j = 0; j < sol.length(); j += 4) {
                    outSet.add(sol.substring(j, j + 4));
                }
                exp.add(outSet);
            }
            test(SOLUTIONS[i].objective, exp);
        }
    }

    @Test
    public void test_junior() {
        for (int i = (SOLUTIONS.length / 5); i < (2 * (SOLUTIONS.length / 5)); i++) {
            Set<Set<String>> exp = new LinkedHashSet<>();
            for (String sol : SOLUTIONS[i].placements) {
                TreeSet<String> outSet = new TreeSet<>();
                for (int j = 0; j < sol.length(); j += 4) {
                    outSet.add(sol.substring(j, j + 4));
                }
                exp.add(outSet);
            }
            test(SOLUTIONS[i].objective, exp);
        }
    }

    @Test
    public void test_expert() {
        for (int i = (2 * (SOLUTIONS.length / 5)); i < (3 * (SOLUTIONS.length / 5)); i++) {
            Set<Set<String>> exp = new LinkedHashSet<>();
            for (String sol : SOLUTIONS[i].placements) {
                TreeSet<String> outSet = new TreeSet<>();
                for (int j = 0; j < sol.length(); j += 4) {
                    outSet.add(sol.substring(j, j + 4));
                }
                exp.add(outSet);
            }
            test(SOLUTIONS[i].objective, exp);
        }
    }

    @Test
    public void test_master() {
        for (int i = (3 * (SOLUTIONS.length / 5)); i < (4 * (SOLUTIONS.length / 5)); i++) {
            Set<Set<String>> exp = new LinkedHashSet<>();
            for (String sol : SOLUTIONS[i].placements) {
                TreeSet<String> outSet = new TreeSet<>();
                for (int j = 0; j < sol.length(); j += 4) {
                    outSet.add(sol.substring(j, j + 4));
                }
                exp.add(outSet);
            }
            test(SOLUTIONS[i].objective, exp);
        }
    }

    @Test
    public void test_wizard() {
        for (int i = (4 * (SOLUTIONS.length / 5)); i < SOLUTIONS.length; i++) {
            Set<Set<String>> exp = new LinkedHashSet<>();
            for (String sol : SOLUTIONS[i].placements) {
                TreeSet<String> outSet = new TreeSet<>();
                for (int j = 0; j < sol.length(); j += 4) {
                    outSet.add(sol.substring(j, j + 4));
                }
                exp.add(outSet);
            }
            test(SOLUTIONS[i].objective, exp);
        }
    }
}
