package hu.dungeonhunter.tools;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    Fight fight = new Fight();

    public void actionMenu() {

        while (fight.nextTurn()) {
            int chosenNumber = chosenNumber();
            switch (chosenNumber) {
                case 1:
                    fight.championAttack();
                    break;
                case 2:
                    fight.runningAway();
                    break;
                case 3:
                    fight.drinkAHealingPotion();
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
        System.out.println("3. Drink a healing potion! You have " + fight.getHealingPotionCounter() + " healing potion.");
    }

}
