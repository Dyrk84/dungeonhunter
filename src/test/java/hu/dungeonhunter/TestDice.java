package hu.dungeonhunter;

import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.characters.monsters.MonsterFactory;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.tools.Dice;
import hu.dungeonhunter.tools.Fight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDice {

    private MonsterFactory monsterFactory = new MonsterFactory();
    private Fight fight = new Fight();

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
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        monster.setHp(0);
        fight.setMonster(monster);

        monster.enemyVictory();
        assertThat(monster.enemyVictory()).as("championWinTest").isTrue();
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