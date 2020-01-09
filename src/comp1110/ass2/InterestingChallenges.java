package comp1110.ass2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static comp1110.ass2.Color.*;

/*
 *  We use this class to pre-generate some interesting challenges and save them in a file.
 *  We have a button "Challenge me more!" in out user inreface which when pressed produces
 *  a challenge from this file in front of the user.
 *
 *  These challenges are interesting because of the way they are created and conceived.
 *  Before adding any challenge to the file, we make sure to check whether it has a solution
 *  and only if it does, it is then added to the file.
 *
 *  We need to run this only once, and this is not a part of the game. It is only used for
 *  generating the challenges once. In the individual methods, we mention how we generate
 *  each interesting challenge.
 *
 *  Author: Tanya Dixit
 * */

public class InterestingChallenges {

    //New game with default difficulty
    static FocusGame game = new FocusGame(0);

    //The new interesting challenges are permutations of colours in 9 ways
    static Color[] c = {R,B,G,W};

    //static data structure to store all the challenges that we generate from
    //all the functions
    static Map<String, String> interestingSol = new HashMap<>();

    /*
     * Challenge Type 1: All the nine tiles are same color. For e.g.
     * RRRRRRRRR or WWWWWWWWW.
     * This function has a side effect of adding the challenges and their solutions
     * in the interestingSol HashMap.
     *
     * @param: void
     * @returns: void
     */
    public void addChallengeType1() {

        for (int k = 0 ; k < c.length; k++) {
            String objective = "";
            for (int i = 0; i < 9; i++) {
                objective += c[k];
            }

            /*Before adding to interestingSol, check if solution exists. Only add if
             * solution exists
             */
            String x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }
        }
    }

    /*
     * Challenge Type 2: Three consecutive tiles are same color. For e.g.
     * RRRWWWBBB or WWWGGGRRR.
     * This function has a side effect of adding or appending the challenges and their solutions
     * in the interestingSol HashMap.
     *
     * @param: void
     * @returns: void
     */
    public void addChallengeType2() {


        for (int k = 0; k < c.length; k++) {
            String objective = "";

            int i = 1;
            //first three characters
            while (i < 4) {
                objective += c[k % c.length];
                i++;
            }

            //next three characters are another colour
            while (i < 7) {
                objective += c[(k + 1) % c.length];
                i++;
            }

            //next three characters for the objective
            while (i < 10) {
                objective += c[(k + 2) % c.length];
                i++;
            }

            String x = FocusGame.getSolution(objective);
            /*
             * Only add if solution exists.
             * Otherswise, don't add.
             */
            if (x != null) {
                interestingSol.put(objective, x);
            }
        }
    }

    /*
     * Challenge Type 3: Three consecutive tiles are same color vertically. For e.g.
     * RWB    GWR
     * RWB or GWR
     * RWB    GWR
     *
     * This function has a side effect of adding or appending the challenges and their solutions
     * in the interestingSol HashMap.
     *
     * @param: void
     * @returns: void
     */
    public void addChallengeType3() {

        /* This loop is to choose different start, end and middle strings
         * and then we permute. We could have used a recursive function to permute,
         * but we were too out of mental resources to write that, therefore I would agree
         * that the implementation of this function/method is inefficient to say the least.
         * And there is a lot of repeat code as well.
         */
        for (int k = 0; k < c.length; k++) {

            String objective = "";
            int i = 0;

            //start, end and middle colors
            Color start = c[k%c.length];
            Color middle = c[(k+1)%c.length];
            Color end = c[(k+2)%c.length];

            //different permutations of start, end, middle vertically
            while (i < 3) {
                objective += start;
                objective += middle;
                objective += end;
                i++;
            }

            /*
             * very important to only add when a solution exists.
             */
            String x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }

            objective = "";
            i=0;

            //again different permutations of start, end, middle
            while (i < 3) {
                objective += start;
                objective += end;
                objective += middle;
                i++;
            }


            x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }

            objective = "";
            i=0;

            //again different permutations of start, end, middle
            while (i < 3) {
                objective += middle;
                objective += start;
                objective += end;
                i++;
            }

            x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }

            objective = "";
            i=0;

            //again different permutations of start, end, middle
            while (i < 3) {
                objective += middle;
                objective += end;
                objective += start;
                i++;
            }

            // Only add when the solution exists
            x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }

            objective = "";
            i=0;

            //again different permutations of start, end, middle
            while (i < 3) {
                objective += end;
                objective += start;
                objective += middle;
                i++;
            }


            x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }

            objective = "";
            i=0;

            //again different permutations of start, end, middle
            while (i < 3) {
                objective += end;
                objective += middle;
                objective += start;
                i++;
            }

            x = FocusGame.getSolution(objective);
            if (x != null) {
                interestingSol.put(objective, x);
            }
        }
    }

    /*
     * Challenge Type 4: Diagonals have same colors. For e.g.
     * RWB
     * WRW
     * BWR
     *
     * This function has a side effect of adding or appending the challenges and their solutions
     * in the interestingSol HashMap.
     *
     * @param: void
     * @returns: void
     */
    public void addChallengeType4() {

        for (int k = 0; k < c.length; k++) {

            /* Color choice for the three diagonals.
             * Let's say diag1 is R, diag 2 is B and
             * diag 3 is W, then the objective will be
             * RBW
             * BRB
             * WBR
             */
            Color diag1 = c[k%c.length];
            Color diag2 = c[(k+1)%c.length];
            Color diag3 = c[(k+2)%c.length];

            int i = 1;
            String objective="";
            while (i < 10) {

                //choose which positions for which diagonal
                //in the objective string. The positions won't change.
                if (i==1 || i == 5 || i ==9)
                    objective+=diag1;

                if (i==2 || i==4 || i==6 || i==8)
                    objective+=diag2;

                if (i==3 || i==7)
                    objective+=diag3;

                    i++;
            }

            String x = FocusGame.getSolution(objective);
            //Only add if solutions is not null, i.e. it exists.
            if (x != null) {
                interestingSol.put(objective, x);
            }
        }
    }

    /*
     * This will be run only once. No need to run again and again.
     * Also, takes a lot of time to run.
     */
    public static void main(String[] args) {

        InterestingChallenges ic = new InterestingChallenges();

        /* Run all the challenges to add all the challenges and their
         * solutions in the static HashMap.
         */
        ic.addChallengeType1();
        ic.addChallengeType2();
        ic.addChallengeType3();
        ic.addChallengeType4();

        for (Map.Entry<String, String> entry : interestingSol.entrySet()) {
            System.out.println("Key:" + entry.getKey() + ", Value: "+entry.getValue());
        }
        try {

            /*
             * Write the HashMap to a file which will be then read in Board.java
             * if the user chooses to get more interesting challenges.
             */

            FileOutputStream file = new FileOutputStream(new File("interestingchallenges.ser"));
            ObjectOutput output = new ObjectOutputStream(file);
            output.writeObject(interestingSol);

            output.close();
            file.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

