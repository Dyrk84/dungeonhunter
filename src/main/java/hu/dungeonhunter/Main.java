package hu.dungeonhunter;
import hu.dungeonhunter.tools.Fight;
import java.util.InputMismatchException;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        actionMenu();
    }

    public static void actionMenu() {
        Fight fight = new Fight();
        while (fight.nextTurn()) {
            printMenu();
            int chosenNumber = chosenNumber();
            if (chosenNumber == 1) {
                fight.championAttack();
            } else yourChooseIsNotAppropriate();
        }
    }

    static int chosenNumber() {
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException hibafogo) {
                yourChooseIsNotAppropriate();
                printMenu();
            }
        } while (true);
    }

    private static void yourChooseIsNotAppropriate() {
        System.out.println("Your choose is not appropriate!");
    }

    private static void printMenu() {
        System.out.println("\nChoose one of the following actions:");
        System.out.println("1. Attack");
    }
}
