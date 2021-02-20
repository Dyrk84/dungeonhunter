package hu.dungeonhunter;

import hu.dungeonhunter.characters.champion.Champion;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import hu.dungeonhunter.tools.Fight;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.characters.monsters.MonsterFactory;
//import hu.dungeonhunter.characters.monsters.Goblin;
//import hu.dungeonhunter.characters.monsters.GoblinKing;

public class TestFight {
    MonsterFactory monsterFactory = new MonsterFactory();
    @Test
    public void monsterCounterAndKilledMonsterCounterTestAndVictory() {
        // test setup (Given)
        MonstersInterface lowHpmonster = monsterFactory.getMonster(CharacterTypes.GOBLIN, 1);
        Fight fight = new Fight(1);
        fight.setMonster(lowHpmonster);

        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(fight.getMonsterCounter()).as("monsterCounter test to decrease in value").isEqualTo(0);
        assertThat(fight.getKilledMonsterCounter()).as("killedMonsterCounter test to increase the value").isEqualTo(1);
        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpmonster.isDefeat()).as("Monster should have been defeated.").isTrue();
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
        MonstersInterface lowHpmonster = monsterFactory.getMonster(CharacterTypes.GOBLIN, 1);
        fight.setMonster(lowHpmonster);

        fight.runningAway();

        assertThat(fight.getMonsterCounter()).as("monsterCounter test to decrease in value").isEqualTo(0);
        assertThat(fight.nextTurn()).as("There should be no next turn, because no more monster.").isFalse();
        assertThat(highHpChampion.isDefeat()).as("Champion should have been not defeated.").isFalse();
    }

    @Test
    public void healingPotionCounterIfMonsterDieTest() {
        // test setup (Given)
        Fight fight = new Fight(1);
        Champion champion = new Champion();
        MonstersInterface lowHpGoblin = monsterFactory.getMonster(CharacterTypes.GOBLIN,1);
        fight.setChampion(champion);
        fight.setMonster(lowHpGoblin);

        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to increase the value").isEqualTo(2);
    }

    @Test
    public void healingPotionHealAfterUseTest() {
        // test setup (Given)
        Fight fight = new Fight();
        Champion champion = new Champion(10);
        fight.setChampion(champion);
        champion.setHealingPotionCounter(1);

        // test action (When)
        champion.drinkAHealingPotion();

        // assertion (Then)
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to decrease in value").isEqualTo(0);
        assertThat(champion.getHp()).as("healingPotion heal 2-8hp on champion").isBetween(12, 20);
    }

    @Test
    public void healingPotionCounterMaximumIfMonsterDieTest() {
        // test setup (Given)
        Fight fight = new Fight(1);
        MonstersInterface lowHpMonster = monsterFactory.getMonster(CharacterTypes.GOBLIN,1);
        fight.setMonster(lowHpMonster);
        Champion champion = new Champion();
        champion.setHealingPotionCounter(5);
        fight.setChampion(champion);
        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to the max value").isEqualTo(5);
    }

    @Test
    public void healingPotionMaxHealAfterUseTest() {
        // test setup (Given)
        Fight fight = new Fight();
        Champion champion = new Champion(39);
        champion.setHealingPotionCounter(1);
        fight.setChampion(champion);

        // test action (When)
        champion.drinkAHealingPotion();

        // assertion (Then)
        assertThat(champion.getHp()).as("healingPotion heal 2-8hp on champion, but 40 is the max").isEqualTo(40);
    }

    @Test
    public void healingPotionActionIfNoMorePotionTest() {
        // test setup (Given)
       // Fight fight = new Fight();
        Champion champion = new Champion(10);
        champion.setHealingPotionCounter(0);
      //  fight.setChampion(champion);

        // test action (When)
        champion.drinkAHealingPotion();

        // assertion (Then)
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to no change in value").isEqualTo(0);
        assertThat(champion.getHp()).as("there is no healing because there are no more drinks").isEqualTo(10);
    }

//DH-13
    @Test
    public void monsterIncomingOrWinRandomNumberCheck(){
        Fight fight = new Fight();
        fight.setMonsterCounter(5);

        assertThat(fight.getRandomEnemy()).as("randomEnemyInitializing").isBetween(1,5);
    }

    @Test
    public void monsterCallerRandomEnemyGoblinKingTest(){
        Fight fight = new Fight();
        MonstersInterface monster = fight.monsterCaller();
        fight.setRandomEnemy(1);
        fight.setMonster(monster);

        assertThat(monsterFactory.getMonster(CharacterTypes.GOBLIN_KING)).as("monsterCallerRandomEnemyTest - GoblinKing").isEqualTo("Goblin king");
    }

    @Test
    public void monsterCallerRandomEnemyGoblinTest(){
        Fight fight = new Fight();
        MonstersInterface monster = fight.monsterCaller();
        fight.setRandomEnemy(2);
        fight.setMonster(monster);

        assertThat(monster).as("monsterCallerRandomEnemyTest - Goblin").isEqualTo(monsterFactory.getMonster(CharacterTypes.GOBLIN));
    }

    @Test
    public void goblinKingAttackChampionDieTest() {
        Fight fight = new Fight();
        Champion lowHpChampion = new Champion(1);
        fight.setChampion(lowHpChampion);

        fight.goblinKingDamage();

        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpChampion.isDefeat()).as("Champion should have been defeated.").isTrue();
    }

    @Test
    public void goblinKingAttackChampionSurviveTest() {
        Fight fight = new Fight();
        Champion highHpChampion = new Champion(10);
        fight.setChampion(highHpChampion);

        fight.goblinKingDamage();

        assertThat(fight.nextTurn()).as("There should be next turn.").isTrue();
        assertThat(highHpChampion.isDefeat()).as("Champion should have been not defeated.").isFalse();
    }

    @Test
    public void runningFromGoblinKingTest(){
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
        fight.setMonster(monster);

        fight.runningAway();

        assertThat(fight.isRunningAwayFromGoblinKingTest()).as("You can not run from goblin king!").isTrue();
    }

    @Test
    public void runningFromGoblinTest(){
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        fight.setMonster(monster);

        fight.runningAway();

        assertThat(fight.isRunningAwayFromGoblinKingTest()).as("You can run from goblin ").isFalse();
    }

    @Test
    public void GoblinKingDeadWinTest(){
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING,0);
        fight.setMonster(monster);

        fight.championAttack();

        assertThat(fight.isGoblinKingIsDeadTest()).as("Goblin King is Dead").isTrue();
    }
}
