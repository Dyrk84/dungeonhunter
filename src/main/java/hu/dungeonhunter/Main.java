package hu.dungeonhunter;

import hu.dungeonhunter.tools.Fight;
import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        menu();

    }
    public static void menu(){
        Fight fight = new Fight();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chooose one of the following actions:");
        System.out.println("1. Attack");
        int chosenNumber = scanner.nextInt();
        if (chosenNumber == 1){
            fight.damagePlayer();
            fight.damageMonster();
        }

    }
}