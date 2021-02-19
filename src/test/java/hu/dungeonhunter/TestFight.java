package hu.dungeonhunter;

import hu.dungeonhunter.characters.Champion;
import hu.dungeonhunter.characters.Monsters;
import hu.dungeonhunter.tools.Fight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFight {

    @Test
    public void monsterCounterAndKilledMonsterCounterTestAndVictory() {
        // test setup (Given)
        Fight fight = new Fight(1);
        Monsters lowHpMonster = new Monsters(1);
        fight.setMonster(lowHpMonster);

        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(fight.getMonsterCounter()).as("monsterCounter test to decrease in value").isEqualTo(0);
        assertThat(fight.getKilledMonsterCounter()).as("killedMonsterCounter test to increase the value").isEqualTo(1);
        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpMonster.isDefeat()).as("Monster should have been defeated.").isTrue();
    }

    @Test
    public void championDefeatTest() {
        Fight fight = new Fight();
        Champion lowHpChampion = new Champion(1);
        fight.setChampion(lowHpChampion);

        fight.monsterAttack();

        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpChampion.isDefeat()).as("Champion should have been defeated.").isTrue();
    }

    @Test
    public void runningAwayAndTheChampionWillDieTest() {
        Fight fight = new Fight();
        Champion lowHpChampion = new Champion(1);
        fight.setChampion(lowHpChampion);

        fight.runningAway();

        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpChampion.isDefeat()).as("Champion should have been defeated.").isTrue();
    }

    @Test
    public void runningAwaySuccessfulEscapeTest() {
        Fight fight = new Fight(1);
        Champion highHpChampion = new Champion(10);
        fight.setChampion(highHpChampion);

        fight.runningAway();

        assertThat(fight.getMonsterCounter()).as("monsterCounter test to decrease in value").isEqualTo(0);
        assertThat(fight.nextTurn()).as("There should be no next turn, because no more monster.").isFalse();
        assertThat(highHpChampion.isDefeat()).as("Champion should have been not defeated.").isFalse();
    }

    @Test
    public void healingPotionCounterIfMonsterDieTest() {
        // test setup (Given)
        Fight fight = new Fight(1);
        Monsters lowHpMonster = new Monsters(1);
        fight.setMonster(lowHpMonster);
        fight.setHealingPotionCounter(1);
        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(fight.getHealingPotionCounter()).as("healingPotionCounter test to increase the value").isEqualTo(2);
    }

    @Test
    public void healingPotionHealAfterUseTest() {
        // test setup (Given)
        Fight fight = new Fight();
        Champion champion = new Champion(10);
        fight.setChampion(champion);
        fight.setHealingPotionCounter(1);

        // test action (When)
        fight.drinkAHealingPotion();

        // assertion (Then)
        assertThat(fight.getHealingPotionCounter()).as("healingPotionCounter test to decrease in value").isEqualTo(0);
        assertThat(champion.getHp()).as("healingPotion heal 2-8hp on champion").isBetween(12, 20);
    }

    @Test
    public void healingPotionCounterMaximumIfMonsterDieTest() {
        // test setup (Given)
        Fight fight = new Fight(1);
        Monsters lowHpMonster = new Monsters(1);
        fight.setMonster(lowHpMonster);
        fight.setHealingPotionCounter(5);
        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(fight.getHealingPotionCounter()).as("healingPotionCounter test to the max value").isEqualTo(5);
    }

    @Test
    public void healingPotionMaxHealAfterUseTest() {
        // test setup (Given)
        Fight fight = new Fight();
        Champion champion = new Champion(39);
        fight.setChampion(champion);
        fight.setHealingPotionCounter(1);

        // test action (When)
        fight.drinkAHealingPotion();

        // assertion (Then)
        assertThat(champion.getHp()).as("healingPotion heal 2-8hp on champion, but 40 is the max").isEqualTo(40);
    }

    @Test
    public void healingPotionActionIfNoMorePotionTest() {
        // test setup (Given)
        Fight fight = new Fight();
        Champion champion = new Champion(10);
        fight.setChampion(champion);
        fight.setHealingPotionCounter(0);

        // test action (When)
        fight.drinkAHealingPotion();

        // assertion (Then)
        assertThat(fight.getHealingPotionCounter()).as("healingPotionCounter test to no change in value").isEqualTo(0);
        assertThat(champion.getHp()).as("there is no healing because there are no more drinks").isEqualTo(10);
    }
}
