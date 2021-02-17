package hu.dungeonhunter;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import hu.dungeonhunter.tools.Dice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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
        Monsters monster = new Monsters();
        monster.setHp(0);
        monster.enemyVictory();
        assertThat(monster.isDefeat()).as("championWinTest").isTrue();
    }

    @Test
    public void championWinTestFail() {
        Monsters monster = new Monsters();
        monster.setHp(0);
        monster.enemyVictory();
        assertThat(monster.isDefeat()).as("championWinTest").isFalse();
    }

    @Test
    public void monsterWinTest() {
        Champion champion = new Champion();
        champion.setHp(0);
        champion.enemyVictory();
        assertThat(champion.isDefeat()).as("monsterWinTest").isTrue();
    }

    @Test
    public void monsterWinTestFail() {
        Champion champion = new Champion();
        champion.setHp(0);
        champion.enemyVictory();
        assertThat(champion.isDefeat()).as("monsterWinTestFail").isFalse();
    }
}