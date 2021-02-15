package hu.dungeonhunter.tools;

import java.util.Random;

public class Dice {
    public static int rollDice(int numOfSides, int numOfDices) {
        int result = 0;
        if (numOfDices <= 0 || numOfSides <=0)
            throw new RuntimeException("invalid dice input");

        for (int j = 0; j < numOfDices; j++) {
            int num = (int) (Math.random() * (numOfSides)) + 1;
            System.out.print(num);

            if (j != numOfDices - 1)
                System.out.print(" + ");

            result += num;
        }
        if (numOfDices > 1)
            System.out.print(" = " + result);
        System.out.println();
        return result;
    }
}
