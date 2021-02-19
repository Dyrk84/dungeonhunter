package hu.dungeonhunter.tools;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void actionMenu() {
        Fight fight = new Fight();
        while (fight.nextTurn()) {
            int chosenNumber = chosenNumber();
            switch (chosenNumber) {
                case 1:
                    fight.championAttack();
                    break;
                case 2:
                    fight.runningAway();
                    break;
                default:
                    yourChooseIsNotAppropriate();
            }
        }
    }

    private int chosenNumber() {
        do {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException hibafogo) {
                yourChooseIsNotAppropriate();
            }
        } while (true);
    }

    private void yourChooseIsNotAppropriate() {
        System.out.println("Your choose is not appropriate!");
    }


    private void printMenu() {
        System.out.println("\nChoose one of the following actions:");
        System.out.println("1. Attack");
        System.out.println("2. Running away!");
    }

}
