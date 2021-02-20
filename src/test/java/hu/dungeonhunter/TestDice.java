package hu.dungeonhunter;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.monsters.Goblin;
import hu.dungeonhunter.tools.Dice;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void championWinTest() {
        Goblin monster = new Goblin(0);
        monster.isDefeat();
        assertThat(monster.isDefeat()).as("championWinTest").isTrue();
    }

    @Test
    public void monsterWinTest() {
        Champion champion = new Champion();
        champion.setHp(0);
        champion.enemyVictory();
        assertThat(champion.isDefeat()).as("monsterWinTest").isTrue();
    }

    @Test
    public void monsterCounterTestForTheCorrectValueAssignmentInFightMethod() {
        int monsterCounter;
        monsterCounter = Dice.rollDice(6, 2);
        assertThat(monsterCounter).as("Test Fight() monsterCounter is not between 2-12").isBetween(2, 12);
    }

}