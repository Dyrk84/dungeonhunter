package hu.dungeonhunter;

import hu.dungeonhunter.tools.Dice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Random;

public class TestDice {

    @Test
    public void testRollDice() {
        int result = Dice.rollDice(1, 1);
        assertThat(result).as("A kockadobás eredménye nem jó!").isEqualTo(1);
    }

    @Test
    public void testRollDiceRandomDice() {
        int result = Dice.rollDice(1, 10);
        assertThat(result).as("A kockadobás eredménye nem 1-10!").isEqualTo(10);
    }

    @Test
    public void testRollDiceRandomSide() {
        int result = Dice.rollDice(10,1);
        assertThat(result).as("A kockadobás eredménye nem 1-10!").isBetween(1, 10);
    }

}
