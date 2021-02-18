package hu.dungeonhunter.tools;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void actionMenu() {
        Fight fight = new Fight();
        while (fight.nextTurn()) {
            int chosenNumber = chosenNumber();
            if (chosenNumber == 1) {
                fight.championAttack();
            } else yourChooseIsNotAppropriate();
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
    }

}
