package hu.dungeonhunter.tools;

import java.util.Random;

public class Dice {
    public static int rollDice(int numOfSides, int numOfDices) {
        Random random = new Random();
        int result = 0;
        for (int j = 1; j < numOfDices+1; j++) {
            int num = random.nextInt(numOfSides+1);

            System.out.println("num: " + num + ", j: " + j);
            result += num;
        }
        System.out.println(result);
        return result;
    }
}
