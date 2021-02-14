package hu.dungeonhunter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final ArrayList<Integer> dice = new ArrayList<>();
    static int playerHP = 20;
    static int playerMaxDamage = 15;
    static int playerDamage = playerDamage();

    static int monsterHP = 20;
    static int monsterMaxDamage = 6;
    static int monsterDamage = monsterDamage();

    static int turnCounter = 0;
    static int thrownNumber;

    public static void main(String[] args) {
        startText();
        playerAction();
    }

    static void startText(){
        System.out.println("This is the Dungeon Hunter adventure game! Prepare yourself! \n Your Hit Points: "
                + playerHP + ". Your weapon can do 1-" + playerMaxDamage + " damage. \n Your enemy is a monster with "
                + monsterHP + " Hit Points, and his weapon do 1-" + monsterMaxDamage + " damage." );
    }

    private static int playerDamage() {
        dice(playerMaxDamage);
        playerDamage = thrownNumber;
        return Main.playerDamage;
    }

    private static int  monsterDamage() {
        dice(monsterMaxDamage);
        monsterDamage = thrownNumber;
        return Main.monsterDamage;
    }

    private static int dice(int incomingNumber) {
        for (int j = 0; j < 1; j++) {
            for (int i = 1; i < incomingNumber + 1; i++) {
                dice.add(i);
            }
            Collections.shuffle(dice);

        }return thrownNumber = dice.get(0);
    }

    static void fight(int monsterHP, int playerDamage, int playerHP, int monsterDamage) {
        playerDamage();
        monsterDamage();

        playerAttack(Main.monsterHP, playerDamage);
        System.out.println("Your damage was: "+ playerDamage);
        counterAttack(Main.playerHP, monsterDamage);
        System.out.println("The monster's counter attack hit you with "+ monsterDamage + " damage!");
        fightEnd(Main.playerHP, Main.monsterHP);
    }

    static void playerAttack(int monsterHP, int playerDamage) {
        Main.monsterHP -= playerDamage;
    }

    static void counterAttack(int playerHP, int monsterDamage) {
        Main.playerHP -= monsterDamage;
    }

    static void playerAction() {
        turnCounter++;
        System.out.println("\n" + turnCounter + ". turn beginning!");

        System.out.println("Choose one of the following actions: ");
        System.out.println("1. Attack");
        int choice = scanner.nextInt();
        if (choice == 1)

            fight(Main.monsterHP, Main.playerDamage, Main.playerHP, Main.monsterDamage);
    }

    static void fightEnd(int playerHP, int monsterHP){
        System.out.println("The monster HP is: " + Main.monsterHP);
        System.out.println("The player HP is: " + Main.playerHP);
        if (playerHP < 1 && monsterHP > 0)
            System.out.println("You are Dead! You Lose!");
        if (playerHP > 0 && monsterHP < 1)
            System.out.println("Monster is Dead! You Won!");
        if (playerHP < 1 && monsterHP <1)
            System.out.println("You are both dead!");
        if (playerHP > 10 && monsterHP < 1)
            System.out.println("Good Job, its was a easy fight for you!");
        if (playerHP < 1 && monsterHP > 10)
            System.out.println("The monster f**ked your loser corpse. That's it folks! ");
        if (playerHP > 0 && monsterHP > 0)
            playerAction();
    }
}
