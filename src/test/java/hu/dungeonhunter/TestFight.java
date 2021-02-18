package hu.dungeonhunter;

import hu.dungeonhunter.characters.Monsters;
import hu.dungeonhunter.tools.Fight;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFight {

    @Test
    public void monsterCounterAndKilledMonsterCounterTest() {
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

//TODO: add champion defeat test
}