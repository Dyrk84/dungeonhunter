package hu.dungeonhunter.tools;
import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Fight{
    Champion champion = new Champion();
    Monsters monster = monsterCaller();
    private int monsterCounter;
    private int killedMonsterCounter = 0;

    public void afterMain(){
        monsterCounter = Dice.rollDice(6,2);
        System.out.println(monsterCounter + " monsters is in the Dungeon!");
        start();
    }

    public void start(){
        if (monsterCounter != 0) {
            System.out.println("Still left in the cave " + monsterCounter + " !");
            monsterCaller();
            actionMenu();
        }
        else
        System.out.println("The Dungeon is clear! You killed " + killedMonsterCounter + "monster (not counting the " +
                "many mothers and children), you win!");
    }

    public Monsters monsterCaller() {
        if (monsterCounter != 0) {
            System.out.println("A monster step out from darkness!");
            monster = new Monsters();
        }return monster;
    }

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
        if (!monster.isLose()){
            monsterAttack();
        }
        else{
            killedMonsterCounter++;
            monsterCounter--;
            start();
        }
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
        if (champion.getHp() > 0 && monster.getHp() > 0) {
            return true;
        }
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