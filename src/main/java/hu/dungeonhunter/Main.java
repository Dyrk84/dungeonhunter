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
        fight.start();
        while (fight.nextTurn()) {
            System.out.println("\nChoose one of the following actions:");
            System.out.println("1. Attack");
            int chosenNumber = chosenNumber();
            if (chosenNumber == 1) {
                fight.championAttack();
            }else System.out.println("Your choice is not appropriate!");
        }
    }

    static int chosenNumber(){
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException hibafogo) {
                System.out.println("Your choose is not appropriate!");
                System.out.println("\nChoose one of the following actions:");
                System.out.println("1. Attack");
            }
        } while (true);
    }
}