package hu.dungeonhunter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.tools.Fight;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.characters.monsters.MonstersInterface;
import hu.dungeonhunter.characters.monsters.MonsterFactory;

public class TestFight {
    MonsterFactory monsterFactory = new MonsterFactory();

    @Test
    public void championAttackTest() {
        // test setup (Given)
        Fight fight = new Fight();
        MonstersInterface lowHpmonster = monsterFactory.getMonster(CharacterTypes.GOBLIN, 1);
        fight.setMonster(lowHpmonster);

        // test action (When)
        fight.championAttack();

        // assertion (Then)
        assertThat(lowHpmonster.isDefeat()).as("Monster should have been defeated.").isTrue();
    }

    @Test
    public void monserAttackTest() {
        Fight fight = new Fight();
        Champion lowHpChampion = new Champion(1);
        fight.setChampion(lowHpChampion);

        fight.monsterAttack();

        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpChampion.getHp()).as("Champion should have been defeated.").isLessThan(1);
    }

    @Test
    public void runningAwayAndTheChampionWillDieIfEnemyIsNotTheGoblinKingTest() {
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        fight.setMonster(monster);
        Champion lowHpChampion = new Champion(1);
        fight.setChampion(lowHpChampion);

        fight.runningAway();

        assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
        assertThat(lowHpChampion.isDefeat()).as("Champion should have been defeated.").isTrue();
    }

    @Test
    public void runningAwaySuccessfulEscapeTest() {
        Fight fight = new Fight();
        Champion highHpChampion = new Champion(40);
        fight.setChampion(highHpChampion);
        MonstersInterface lowHpmonster = monsterFactory.getMonster(CharacterTypes.GOBLIN, 1);
        fight.setMonster(lowHpmonster);
        fight.setMonsterCounter(1);
        int monsterCounterToTest = fight.getMonsterCounter();

        fight.runningAway();

        assertThat(fight.getMonsterCounter()).as("monsterCounter test to decrease in value").isEqualTo(monsterCounterToTest - 1);
        assertThat(highHpChampion.isDefeat()).as("Champion should have been not defeated.").isFalse();
    }

    @Test
    public void healingPotionCounterMax5IfMonsterDefeatedTest() {
        // test setup (Given)
        Fight fight = new Fight();
        Champion champion = new Champion();
        fight.setChampion(champion);
        champion.setHealingPotionCounter(5);

        // test action (When)
        fight.monsterDefeated();

        // assertion (Then)
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to increase the value").isEqualTo(5);
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
    public void monsterIncomingOrWinRandomNumberCheck() {
        Fight fight = new Fight();

        fight.monsterIncomingOrWin();

        assertThat(fight.getRandomEnemy()).as("randomEnemyInitializing").isBetween(1, 12);
    }

    @Test
    public void monsterCallerRandomEnemyGoblinKingTest() {
        Fight fight = new Fight();
        fight.setRandomEnemy(1);
        MonstersInterface monster = fight.monsterCaller();
        fight.setMonster(monster);

        assertThat(fight.getMonster().getType()).as("monsterCallerRandomEnemyTest - GoblinKing").isEqualTo(CharacterTypes.GOBLIN_KING);
    }

    @Test
    public void monsterCallerRandomEnemyGoblinTest() {
        Fight fight = new Fight();
        fight.setRandomEnemy(2);
        MonstersInterface monster = fight.monsterCaller();
        fight.setMonster(monster);

        assertThat(fight.getMonster().getType()).as("monsterCallerRandomEnemyTest - Goblin").isEqualTo(CharacterTypes.GOBLIN);
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
    public void YouCanNotRunFromGoblinKingTest() {
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
        fight.setMonster(monster);
        fight.setMonsterCounter(3);
        Champion champion = new Champion();
        champion.setHp(10);
        fight.setChampion(champion);

        fight.runningAway();

        assertThat(fight.getMonsterCounter()).as("You can not run from goblin king!").isEqualTo(3);
        assertThat(champion.getHp()).as("You can not run from goblin king!").isEqualTo(10);
    }

    @Test
    public void runningFromGoblinTest() {
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        fight.setMonster(monster);
        fight.setMonsterCounter(3);
        Champion champion = new Champion();
        champion.setHp(10);
        fight.setChampion(champion);

        fight.runningAway();

        assertThat(fight.getMonsterCounter()).as("You can not run from goblin king!").isEqualTo(2);
        assertThat(champion.getHp()).as("You can not run from goblin king!").isLessThan(10);
    }

    @Test
    public void GoblinKingIsDefeatedTest() {
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING, 0);
        fight.setMonster(monster);

        fight.championAttack();

        assertThat(monster.isDefeat()).as("Goblin King is Dead").isTrue();
    }

    @Test
    public void goblinAttackInitiationCalculationTest() {
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        fight.setMonster(monster);

        fight.getMonster().monsterAttackInitiationCalculation();

        assertThat(fight.getMonster().getFinalMonsterInitiation()).as("This volume will between Goblin initiative + 1d10")
                .isBetween(fight.getMonster().getInitiative() + 1,
                        fight.getMonster().getInitiative() + 10);
    }

    @Test
    public void goblinKingAttackInitiationCalculationTest() {
        Fight fight = new Fight();
        MonstersInterface monster = monsterFactory.getMonster(CharacterTypes.GOBLIN_KING);
        fight.setMonster(monster);

        fight.getMonster().monsterAttackInitiationCalculation();

        assertThat(fight.getMonster().getFinalMonsterInitiation()).as("This volume will between Goblin king initiative + 1d10")
                .isBetween(fight.getMonster().getInitiative() + 1,
                        fight.getMonster().getInitiative() + 10);
    }

    @Test
    public void championAttackInitiationCalculationTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        fight.setChampion(champion);

        champion.championAttackInitiationCalculation();

        assertThat(champion.getFinalChampionInitiation()).as("This volume will between Champion initiative + 1d10")
                .isBetween(champion.getInitiative() + 1, champion.getInitiative() + 10);
    }

    @Test
    public void attackInitiatingIfChampionHaveHigherInitiativeTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setInitiative(50);
        champion.setHp(1);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setHp(1);
        fight.setMonster(goblin);

        fight.attackInitiating();

        assertThat(goblin.isDefeat()).as("Low hp goblin should have been defeated, without attack").isTrue();
    }

    @Test
    public void attackInitiatingIfMonsterHaveHigherInitiativeTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setHp(1);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setInitiative(50);
        goblin.setHp(1);
        fight.setMonster(goblin);

        fight.attackInitiating();

        assertThat(goblin.isDefeat()).as("Low hp champion should have been defeated, without attack").isFalse();
    }

    @Test
    public void champAttackFirstGoblinDefeatedTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setInitiative(50);
        champion.setHp(1);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setHp(1);
        fight.setMonster(goblin);
        int killedMonsterCounterAfterTesting = monsterFactory.getKilledMonsterCounter() + 1;
        int monsterCounterAfterTesting = fight.getMonsterCounter() - 1;
        champion.setHealingPotionCounter(2);

        fight.attackInitiating();

        assertThat(goblin.isDefeat()).as("Low hp goblin should have been defeated, without attack").isTrue();
        assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).as("The killedMonsterCounter value will increase by one")
                .isEqualTo(killedMonsterCounterAfterTesting);
        assertThat(fight.getMonsterCounter()).as("The monsterCounter value will decrease by one").isEqualTo(monsterCounterAfterTesting);
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to increasing").isEqualTo(3);
    }

    @Test
    public void reworkedHealingPotionCounterMax5Test() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setInitiative(50);
        champion.setHp(1);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setHp(1);
        fight.setMonster(goblin);
        champion.setHealingPotionCounter(5);

        fight.attackInitiating();

        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to the max counter").isEqualTo(5);
    }

    @Test
    public void championAttackingFirstButTheGoblinDoNotDieAndHitBackTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setInitiative(50);
        champion.setHp(1);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setHp(50);
        fight.setMonster(goblin);
        int killedMonsterCounterAfterTesting = monsterFactory.getKilledMonsterCounter();
        int monsterCounterAfterTesting = fight.getMonsterCounter();
        champion.setHealingPotionCounter(2);

        fight.attackInitiating();

        assertThat(champion.getHp()).as("High hp goblin will take a champ damage, but don't die and hit back to" +
                " the low hp champion, who will die.").isLessThan(1);
        assertThat(goblin.isDefeat()).as("High hp goblin will take a champ damage, but don't die!").isFalse();
        assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).as("The killedMonsterCounter will not change")
                .isEqualTo(killedMonsterCounterAfterTesting);
        assertThat(fight.getMonsterCounter()).as("The monsterCounter value will not change").isEqualTo(monsterCounterAfterTesting);
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter value will not change").isEqualTo(2);
    }

    @Test
    public void goblinAttackFirstAndChampionDefeatedTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setInitiative(1);
        champion.setHp(1);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setInitiative(50);
        goblin.setHp(1);
        fight.setMonster(goblin);
        int killedMonsterCounterAfterTesting = monsterFactory.getKilledMonsterCounter();
        int monsterCounterAfterTesting = fight.getMonsterCounter();
        champion.setHealingPotionCounter(2);

        fight.attackInitiating();

        assertThat(champion.getHp()).as("High hp goblin will take a champ damage, but don't die and hit back to" +
                " the low hp champion, who will die.").isLessThan(1);
        assertThat(goblin.isDefeat()).as("High hp goblin will take a champ damage, but don't die!").isFalse();
        assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).as("The killedMonsterCounter will not change")
                .isEqualTo(killedMonsterCounterAfterTesting);
        assertThat(fight.getMonsterCounter()).as("The monsterCounter value will not change").isEqualTo(monsterCounterAfterTesting);
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter value will not change").isEqualTo(2);
    }

    @Test
    public void goblinAttackFirstAndChampionNotDefeatedTest() {
        Fight fight = new Fight();
        Champion champion = new Champion();
        champion.setInitiative(1);
        champion.setHp(50);
        fight.setChampion(champion);
        MonstersInterface goblin = monsterFactory.getMonster(CharacterTypes.GOBLIN);
        goblin.setInitiative(50);
        goblin.setHp(1);
        fight.setMonster(goblin);
        int killedMonsterCounterAfterTesting = monsterFactory.getKilledMonsterCounter() + 1;
        int monsterCounterAfterTesting = fight.getMonsterCounter() - 1;
        champion.setHealingPotionCounter(2);

        fight.attackInitiating();

        assertThat(goblin.isDefeat()).as("Low hp goblin should have been defeated, without attack").isTrue();
        assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).as("The killedMonsterCounter value will increase by one")
                .isEqualTo(killedMonsterCounterAfterTesting);
        assertThat(fight.getMonsterCounter()).as("The monsterCounter value will decrease by one").isEqualTo(monsterCounterAfterTesting);
        assertThat(champion.getHealingPotionCounter()).as("healingPotionCounter test to increasing").isEqualTo(3);
    }
    //TODO egyező kezdeményezőértékre írni egy tesztet
}