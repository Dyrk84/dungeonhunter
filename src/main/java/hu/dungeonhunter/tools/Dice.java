package hu.dungeonhunter.tools;

import java.util.Random;

public class Dice {
    public static int rollDice(int numOfSides, int numOfDices) {
        Random random = new Random();
        int result = 0;
        for (int j = 0; j < numOfDices; j++) {
            int num = random.nextInt(numOfSides)+1;
            System.out.print(num);

            if (j != numOfDices-1)
                System.out.print(" + ");

            result += num;
        }
        System.out.println(" = " + result);
        return result;
    }
}
