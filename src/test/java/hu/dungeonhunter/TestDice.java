package hu.dungeonhunter;
import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import hu.dungeonhunter.tools.Dice;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestDice {

    @Test
    public void testRollDice() {
        int result = Dice.rollDice(1, 1);
        assertThat(result).as("A kockadobás eredménye nem 1!").isEqualTo(1);
    }

    @Test
    public void testRollDice10d1() {
        int result = Dice.rollDice(1, 10);
        assertThat(result).as("A kockadobás eredménye nem 10!").isEqualTo(10);
    }

    @Test
    public void testRollDice1d10() {
        int result = Dice.rollDice(10, 1);
        assertThat(result).as("A kockadobás eredménye nem 1-10!").isBetween(1, 10);
    }

    //next 4 test for ValidationInMainActionMenu
    @Test
    public void testAddInputValidationInMainActionMenuWithHigherNumber() {
        int chosenNumber = 4;
        assertThat(chosenNumber).as("Input in Action Menu is not applicable").isEqualTo(1);
    }

    @Test
    public void testAddInputValidationInMainActionMenuWithMinus() {
        int chosenNumber = -4;
        assertThat(chosenNumber).as("Input in Action Menu is not applicable").isEqualTo(1);
    }

    @Test
    public void testAddInputValidationInMainActionMenuWithString() {
        String chosenNumber = "wrong input";
        assertThat(chosenNumber).as("Input in Action Menu is not applicable").isEqualTo(1);
    }

    @Test
    public void testAddInputValidationInMainActionMenuWithChar() {
        char chosenNumber = '!';
        assertThat(chosenNumber).as("Input in Action Menu is not applicable").isEqualTo(1);
    }

    @Test
    //This solution definitely not good, but i don't know how to finish testing.
    public void testAddInputValidationInMainActionMenuWithNewReturnerMethod() {
        int chosenNumber = chosenNumber();
        if (chosenNumber != 1){System.out.println("Your choice is not appropriate!");}
        assertThat(chosenNumber).as("Input in Action Menu is not applicable").isEqualTo(1);
    }

    static int chosenNumber() {
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
//// nem tudom hogyan kell a következő 4 tesztben metódus végeredményére hivatkozni, de már nem akarom kitörölni
//    @Test
//    public void championPositiveVictoryTest() {
//        Champion champion = new Champion();
//        Monsters monster = new Monsters();
//        int championHP = 1;
//        int monsterHP = -1;
//        champion.setChampionHP(championHP);
//        monster.setMonsterHP(monsterHP);
//        if (champion.getChampionHP() > 0 && monster.getMonsterHP() < 1) {
//            System.out.println("You killed the monster! You win!");
//            assertThat().as("championPositiveVictoryTest").isEqualTo("You killed the monster! You win!");
//        }
//    }
//
//    @Test
//    public void championNegativeVictoryTest() {
//        Champion champion = new Champion();
//        Monsters monster = new Monsters();
//        int championHP = -1;
//        int monsterHP = 1;
//        champion.setChampionHP(championHP);
//        monster.setMonsterHP(monsterHP);
//        if (champion.getChampionHP() > 0 && monster.getMonsterHP() < 1) {
//            System.out.println("You killed the monster! You win!");
//            assertThat().as("championPositiveVictoryTest").isEqualTo("You killed the monster! You win!");
//        }
//    }
//
//    @Test
//    public void monsterPositiveVictoryTest() {
//        Champion champion = new Champion();
//        Monsters monster = new Monsters();
//        int championHP = 1;
//        int monsterHP = -1;
//        champion.setChampionHP(championHP);
//        monster.setMonsterHP(monsterHP);
//        if (champion.getChampionHP() > 0 && monster.getMonsterHP() < 1) {
//            System.out.println("The monster killed you! You lost!");
//            assertThat().as("monsterPositiveVictoryTest").isEqualTo("The monster killed you! You lost!");
//        }
//    }
//
//    @Test
//    public void monsterNegativeVictoryTest() {
//        Champion champion = new Champion();
//        Monsters monster = new Monsters();
//        int championHP = -1;
//        int monsterHP = 1;
//        champion.setChampionHP(championHP);
//        monster.setMonsterHP(monsterHP);
//        if (champion.getChampionHP() < 1 && monster.getMonsterHP() > 0) {
//            System.out.println("The monster killed you! You lost!");
//            assertThat().as("monsterNegativeVictoryTest").isEqualTo("The monster killed you! You lost!");
//        }
//    }

}