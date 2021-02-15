package hu.dungeonhunter;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;

import hu.dungeonhunter.tools.Fight;

import java.util.Scanner;

class Main {
    static Champion champion = new Champion();
    static Monsters monster = new Monsters();

    public static void main(String[] args) {
        actionMenu();
        nextTurn();
        gameOver();
        System.out.println("Champion have now " + champion.getChampionHP() + " hit points");

    }

    private static void gameOver() {

        if (champion.getChampionHP() > 0 && monster.getMonsterHP() < 1)
            System.out.println("You killed the monster! You win!");
        if (champion.getChampionHP() < 1 && monster.getMonsterHP() > 0)
            System.out.println("The monster killed you! You lost!");
        if (champion.getChampionHP() < 1 && monster.getMonsterHP() < 1)
            System.out.println("you are both dead");
        if (champion.getChampionHP() > 0 && monster.getMonsterHP() > 0)
            System.out.println("miért nem csökken a hp?");
    }

    private static void nextTurn() {
        if (champion.getChampionHP() > 0 && monster.getMonsterHP() > 0)
            actionMenu();
    }

    public static void actionMenu() {
        Fight fight = new Fight();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChooose one of the following actions:");
        System.out.println("1. Attack");
        int chosenNumber = scanner.nextInt();
        if (chosenNumber == 1) {
            fight.championAttack();
            fight.monsterAttack();
        }

    }
}