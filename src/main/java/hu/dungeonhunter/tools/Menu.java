package hu.dungeonhunter.tools;

import hu.dungeonhunter.utils.TextSeparator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    Fight fight = new Fight();

    public void actionMenu() {

        while (fight.nextTurn()) {
            int chosenNumber = chosenNumber();
            switch (chosenNumber) {
                case 1:
                    fight.battle();
                    break;
                case 2:
                    fight.runningAway();
                    break;
                case 3:
                    fight.getChampion().drinkAHealingPotion();
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
        TextSeparator.format("Akcio menu");
        System.out.println("Choose one of the following actions:");
        System.out.println("1. Attack");
        System.out.println("2. Running away!");
        System.out.println("3. Drink a healing potion! You have " + fight.getChampion().getHealingPotionCounter() + " healing potion.");
        TextSeparator.format("");
        System.out.print("Type your action number: ");
    }
}
