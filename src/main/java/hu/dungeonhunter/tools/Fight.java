package hu.dungeonhunter.tools;
import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Fight {
    Champion champion = new Champion();
    Monsters monster = new Monsters();

    public void monsterAttack() {
        System.out.print("Monster attack: ");
        champion.setHp(champion.getHp() - monster.monsterDamage());
        System.out.println("Champion have now " + champion.getHp() + " hit points");
        champion.enemyVictory();
        if (!champion.isLose())
            nextTurn();
    }

    public void championAttack() {
        System.out.print("Champion attack: ");
        monster.setHp(monster.getHp() - champion.championDamage());
        System.out.println("Monster have now " + monster.getHp() + " hit points");
        monster.enemyVictory();
        if (!monster.isLose())
            monsterAttack();
    }

    int chosenNumber() {
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

    private void yourChooseIsNotAppropriate() {
        System.out.println("Your choose is not appropriate!");
    }

    private void printMenu() {
        System.out.println("\nChoose one of the following actions:");
        System.out.println("1. Attack");
    }

    public boolean nextTurn() {
        if (champion.getHp() > 0 && monster.getHp() > 0)
            return true;
        else {
            return false;
        }
    }

    public void actionMenu() {
        while (nextTurn()) {
            printMenu();
            int chosenNumber = chosenNumber();
            if (chosenNumber == 1) {
                championAttack();
            } else yourChooseIsNotAppropriate();
        }
    }
}