package hu.dungeonhunter;

import hu.dungeonhunter.tools.Fight;

import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        actionMenu();
    }

    public static void actionMenu() {
        Fight fight = new Fight();
        fight.start();
        while (fight.nextTurn()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChooose one of the following actions:");
            System.out.println("1. Attack");
            int chosenNumber = scanner.nextInt();
            if (chosenNumber == 1) {
                fight.championAttack();
                fight.monsterAttack();
                fight.gameOver();
            }
        }
    }
}