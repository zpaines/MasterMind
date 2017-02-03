/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermindguesser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author zach
 */
public class MasterMindGuesser {

    /**
     * I
     */
    public static void main(String[] args) {
        int[] pegs = new int[]{(int) (Math.random() * 6), (int) (Math.random() * 6), (int) (Math.random() * 6), (int) (Math.random() * 6)};
        int[] guesses = new int[4];
        System.out.println("Pegs are " + Arrays.toString(pegs));
        List<int[]> possibilities = new ArrayList<>();  //This could be represented by an array of integers but oh well
        boolean[] prevGuesses = new boolean[6666];  //Some extra room but it doesn't matter
        for (int a = 0; a < 6; a++) {
            for (int b = 0; b < 6; b++) {
                for (int c = 0; c < 6; c++) {
                    for (int d = 0; d < 6; d++) {
                        possibilities.add(new int[]{a, b, c, d});
                    }
                }
            }
        }
        int[] curGuess = new int[]{0,1,2,3};
        prevGuesses[curGuess[0]*1000 + curGuess[1]*100 + curGuess[2]*10 + curGuess[3]] = true;

        while (true) {
            guesses = curGuess;
            int[] spots = check(pegs, curGuess);
            System.out.println("Current Guess is " + Arrays.toString(curGuess) + " " + spots[0] + " " + spots[1]);
            Iterator<int[]> iterator = possibilities.iterator();
            while (iterator.hasNext()) {
                if (! checkPossibility(iterator.next(), curGuess, spots[0], spots[1]))
                    iterator.remove();
            }
            if (possibilities.size() == 1) {
                System.out.println(Arrays.toString(possibilities.get(0)));
                break;
            }
            while (true) {
                curGuess = possibilities.get((int) (Math.random() * possibilities.size()));
                if (! prevGuesses[curGuess[0]*1000 + curGuess[1]*100 + curGuess[2]*10 + curGuess[3]])
                    break;
            }
        }
    }

    private static boolean checkPossibility(int[] next, int[] curGuess, int correctSpot, int wrongSpot) {
        int[] spots = check(next, curGuess);
        return ((spots[0]== correctSpot) && (spots[1] == wrongSpot));
    }

    private static int[] check(int[] key, int[] guess) {
        boolean[] used = new boolean[]{false, false, false, false};
        int sameSpots = 0;
        int diffSpots = 0;
        for (int i=0; i<4; i++) {
            if (Objects.equals(key[i], guess[i])) {
                sameSpots++;
                used[i] = true;
            }
        }
        for (int a=0; a<4; a++) {
            if (Objects.equals(key[a], guess[a]))
                continue;
            for (int b=0; b<4; b++) {
                if ((! used[b]) && (Objects.equals(key[a], guess[b]))) {
                    diffSpots++;
                    used[b] = true;
                    break;
                }
            }
        }
        return new int[]{sameSpots, diffSpots};
    }

}
