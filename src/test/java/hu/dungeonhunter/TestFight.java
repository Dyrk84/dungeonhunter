package hu.dungeonhunter;

import static org.assertj.core.api.Assertions.assertThat;

import hu.dungeonhunter.characters.monsters.Goblin;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import hu.dungeonhunter.characters.champion.Champion;
import hu.dungeonhunter.tools.Fight;
import hu.dungeonhunter.model.CharacterTypes;
import hu.dungeonhunter.characters.Character;
import hu.dungeonhunter.characters.MonsterFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class TestFight {

    private SoftAssertions softly = new SoftAssertions(); // akkor van értelme amikor több assert van egy metódusban,
    // az összes hibásat kiadja, nem csak az elsőt. softly.assertAll(); //ezzel gyűjti be a hibás asserteket, ha ez nincs, akkor nem dobja fel a hibákat!!!

    private MonsterFactory monsterFactory = new MonsterFactory();
    private Fight fight = new Fight();
    private Champion champion = new Champion();

    //segítő változók
    int startingMonsterCounter = fight.getMonsterCounter();
    int startingKilledMonsterCounter = monsterFactory.getKilledMonsterCounter();

    //segítő meghívások
    private void defeatedGoblin() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
        monster.setHp(0);
        fight.setMonster(monster);
    }

    private void defeatedGoblinKing() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN_KING);
        monster.setHp(0);
        fight.setMonster(monster);
    }

    private void defeatedChampion() {
        Champion champion = new Champion();
        champion.setHp(0);
        fight.setChampion(champion);
    }

    private void lowHpGoblin() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
        monster.setHp(1);
        fight.setMonster(monster);
    }

    private void lowHpGoblinKing() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN_KING);
        monster.setHp(1);
        fight.setMonster(monster);
    }

    private void lowHpChampion() {
        Champion champion = new Champion();
        champion.setHp(1);
        fight.setChampion(champion);
    }

    private void highHpGoblin() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
        monster.setHp(1000);
        fight.setMonster(monster);
    }

    private void highHpChampion() {
        Champion lowHpChampion = new Champion();
        champion.setHp(1000);
        fight.setChampion(lowHpChampion);
    }

    //segítő assertek
    private void ifAssertThatIsChampionStillAlive() {
        softly.assertThat(fight.getChampion().getHp()).as("Champion should be still alive").isGreaterThan(0);
        softly.assertThat(fight.nextTurn()).as("There should be next turn.").isTrue();
    }

    private void ifAssertThatIsChampionDefeated() {
        softly.assertThat(fight.getChampion().getHp()).as("Champion defeated").isLessThan(1);
        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
    }

    private void ifAssertThatIsMonsterStillAlive() {
        softly.assertThat(fight.getMonster().getHp()).as("Goblin or Goblin king should be still alive after damage").isGreaterThan(0);
    }

    private void ifAssertThatIsMonsterDefeated() {
        softly.assertThat(fight.getMonster().getHp()).as("Goblin or Goblin King defeated").isLessThan(1);
    }

    @Test
    public void goblinKingDamageTest() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN_KING);
        Champion champion = new Champion();
        champion.setHp(1);
        fight.setChampion(champion);
        fight.setMonster(monster);

        fight.goblinKingDamage();

        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void runningAwayFromGoblinKingTest() {
        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN_KING);
        fight.setMonster(monster);
        lowHpChampion();

        fight.runningAway();

        ifAssertThatIsChampionStillAlive();
        softly.assertAll();
    }

    @Test
    public void runningAwayWithGoblinHitChampStillAliveTest() {
        highHpChampion();
        lowHpGoblin();

        fight.runningAway();

        ifAssertThatIsChampionStillAlive();
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter - 1);
        softly.assertAll();
    }

    @Test
    public void runningAwayWithGoblinHitChampDefeatedTest() {
        lowHpChampion();
        lowHpGoblin();

        fight.runningAway();

        ifAssertThatIsChampionDefeated();
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter);
        softly.assertAll();
    }

    @Test
    public void monsterIncomingRandomEnemyTest() {
        fight.setMonsterCounter(10);

        fight.monsterIncoming();

        assertThat(fight.getRandomEnemy()).isBetween(1, 10);
    }

    @Test
    public void monsterCallerGoblinKingTest() {
        fight.setRandomEnemy(1);
        highHpGoblin();

        fight.monsterCaller();

        assertThat(fight.getMonster().getNumOfDices()).as("goblinKing have 2 numOfDices").isEqualTo(2);
    }

    @Test
    public void monsterCallerGoblinTest() {
        fight.setRandomEnemy(10);
        highHpGoblin();

        fight.monsterCaller();

        assertThat(fight.getMonster().getNumOfDices()).as("goblin have 1 numOfDices").isEqualTo(1);
    }
//
//    @Test
//    public void fightInitiationCalculationChampionTest() {
//        Goblin monster = new Goblin();
//        monster.setInitiation(20);
//        fight.setMonster(monster);
//        champion.setInitiation(100);
//        fight.setChampion(champion);
//        fight.setCharactersInBattle(Arrays.asList(monster, champion)); //az Arrays.asList azt jelenti, hogy létrehoz egy tömb osztályt listaként.
//
//        fight.initiationCalc();
//
//        assertThat(fight.getInitRolls().get(0)).isBetween(101,110); //TODO hogy tudnék hivatkozni a value-ra?
//        assertThat(fight.getCharactersInBattle().get(0)).isEqualTo("champion"); //TODO Actual   :hu.dungeonhunter.characters.champion.Champion@6121c9d6
//    }
//
//    @Test
//    public void fightInitiationCalculationMonsterTest() {
//        Goblin monster = new Goblin();
//        monster.setInitiation(20);
//        fight.setMonster(monster);
//        champion.setInitiation(100);
//        fight.setChampion(champion);
//        fight.setCharactersInBattle(Arrays.asList(monster, champion));
//
//        fight.initiationCalc();
//
//        assertThat(fight.getMonsterFinalInitiation()).isBetween(11, 20);
//    }
////
////    @Test
////    public void initiationCalculationWithSameFinalInitiationWithInfinityLoopExceptionTest() throws RuntimeException {
////        Character monster = characterFactory.getCharacter(CharacterTypes.GOBLIN);
////        monster.setHp(1);
////        monster.setAccuracy(1000);
////        monster.setInitiation(1000);
////        fight.setMonster(monster);
////        champion.setHp(1);
////        fight.setChampion(champion);
////        fight.setLoopCounter(99);
////        fight.setMonsterFinalInitiation(1000);
////        fight.setChampionFinalInitiation(1000);
////
////        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> fight.battle());
////
////        ifAssertThatIsChampionDefeated();
////        assertThat(fight.getLoopCounter()).isEqualTo(100);
////    }
//
//    @Test
//    public void monsterAccuracyCalculationIfMonsterIsAliveTest() {
//        Character monster = monsterFactory.getCharacter(CharacterTypes.GOBLIN);
//        monster.setHp(1);
//        monster.setAccuracy(100);
//        fight.setMonster(monster);
//
//        fight.monsterAccuracyCalculation();
//
//        assertThat(fight.getFinalAccuracy()).isBetween(101, 200);
//        assertThat(fight.getMonster().getAccuracyRoll()).isBetween(1, 100);
//    }

    @Test
    public void monsterAccuracyCalculationIfMonsterIsDefeated() {
        Goblin monster = new Goblin();
        monster.setHp(0);
        fight.setCharactersInBattle(Arrays.asList(monster, champion)); //az Arrays.asList azt jelenti, hogy létrehoz egy tömb osztályt listaként.
        fight.setMonster(monster);
        int championStartHp = fight.getChampion().getHp();

        fight.attack(monster, 50);

        assertThat(fight.getChampion().getHp()).isEqualTo(championStartHp);
    }

    //    @Test
//    public void championAccuracyCalculationIfChampionIsAliveTest() {
//        Character monster = characterFactory.getCharacter(CharacterTypes.GOBLIN);
//        fight.setMonster(monster);
//        champion.setHp(1);
//        champion.setAccuracy(100);
//        fight.setChampion(champion);
//
//        fight.championAccuracyCalculation();
//
//        assertThat(fight.getFinalAccuracy()).isBetween(101, 200);
//        assertThat(fight.getChampion().getAccuracyRoll()).isBetween(1, 100);
//    }
//
//    @Test
//    public void championAccuracyCalculationIfChampionIsDefeated() {
//        champion.setHp(0);
//        champion.setAccuracy(100);
//        fight.setChampion(champion);
//
//        fight.championAccuracyCalculation();
//
//        assertThat(fight.getFinalAccuracy()).isEqualTo(0);
//        assertThat(fight.getChampion().getAccuracyRoll()).isEqualTo(0);
//    }
//
    @Test
    public void monsterAccuracyIfAccuracyRollIs1Test() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        fight.setRandomCriticalMissEvent(5);
        fight.attack(monster, 1);

        softly.assertThat(fight.getRandomCriticalMissEvent()).isLessThan(5);

        softly.assertAll();
    }

    @Test
    public void championAccuracyIfAccuracyRollIs1Test() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        fight.setChampion(champion);
        fight.setRandomCriticalMissEvent(5);

        fight.attack(champion, 1);

        softly.assertThat(fight.getRandomCriticalMissEvent()).isLessThan(5);
        softly.assertAll();
    }

    @Test
    public void championCriticalMissEvent1Test() {
        champion.setHp(1);
        fight.setChampion(champion);
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        fight.setRandomCriticalMissEvent(1);
        int[] championHpAfterDamage = {fight.getChampion().getHp() - fight.getChampion().getMaxDamage(),
                fight.getChampion().getHp() - fight.getChampion().getNumOfDices()};

        fight.criticalMissEvent(champion);

        softly.assertThat(fight.getChampion().getHp()).isBetween(championHpAfterDamage[0], championHpAfterDamage[1]);
        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterCriticalMissEvent1Test() {
        Goblin monster = new Goblin();
        monster.setHp(1);
        fight.setMonster(monster);
        fight.setRandomCriticalMissEvent(1);
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() - fight.getMonster().getMaxDamage(),
                fight.getMonster().getHp() - fight.getMonster().getNumOfDices()};

        fight.criticalMissEvent(monster);

        softly.assertThat(fight.getMonster().getHp()).isBetween(monsterHpAfterDamage[0], monsterHpAfterDamage[1]);
        ifAssertThatIsMonsterDefeated();
        softly.assertAll();
    }

    @Test
    public void championCriticalMissEvent2Test() {
        champion.setMaxDamage(6);
        fight.setChampion(champion);
        fight.setRandomCriticalMissEvent(2);

        fight.criticalMissEvent(champion);

        assertThat(fight.getChampion().getMaxDamage()).isEqualTo(5);
    }

    @Test
    public void monsterCriticalMissEvent2Test() {
        Goblin monster = new Goblin();
        monster.setMaxDamage(6);
        fight.setMonster(monster);
        fight.setRandomCriticalMissEvent(2);

        fight.criticalMissEvent(monster);

        assertThat(fight.getMonster().getMaxDamage()).isEqualTo(5);
    }

    @Test
    public void championAccuracyIfAccuracyRollIs100Test() {
        Goblin monster = new Goblin();
        monster.setHp(10);
        monster.setDefense(1000);
        fight.setMonster(monster);
        champion.setHp(10);
        fight.setChampion(champion);
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() - (fight.getChampion().getMaxDamage()) * 10,
                fight.getMonster().getHp() - (fight.getChampion().getNumOfDices()) * 10};

        fight.attack(champion,100);

        softly.assertThat(fight.getMonster().getHp()).isBetween(monsterHpAfterDamage[0], monsterHpAfterDamage[1]);
        ifAssertThatIsMonsterDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyIfAccuracyRollIs100Test() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        champion.setHp(10);
        champion.setDefense(1000);
        fight.setChampion(champion);
        int[] championHpAfterDamage = {fight.getChampion().getHp() - (fight.getMonster().getMaxDamage()) * 10,
                fight.getChampion().getHp() - (fight.getMonster().getNumOfDices()) * 10};

        fight.attack(monster,100);

        softly.assertThat(fight.getChampion().getHp()).isBetween(championHpAfterDamage[0], championHpAfterDamage[1]);
        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void championAccuracyIfHitTest() {
        Goblin monster = new Goblin();
        monster.setHp(1);
        monster.setDefense(50);
        fight.setMonster(monster);
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() - fight.getChampion().getMaxDamage(),
                fight.getMonster().getHp() - fight.getChampion().getNumOfDices()};

        fight.attack(champion,31);

        softly.assertThat(fight.getMonster().getHp()).isBetween(monsterHpAfterDamage[0], monsterHpAfterDamage[1]);
        ifAssertThatIsMonsterDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyIfHitTest() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        Champion champion = new Champion();
        champion.setHp(1);
        champion.setDefense(50);
        fight.setChampion(champion);
        int[] championHpAfterDamage = {fight.getChampion().getHp() - fight.getMonster().getMaxDamage(),
                fight.getChampion().getHp() - fight.getMonster().getNumOfDices()};

        fight.attack(monster,41);

        softly.assertThat(fight.getChampion().getHp()).isBetween(championHpAfterDamage[0], championHpAfterDamage[1]);
        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void championAccuracyIfMissHitTest() {
        Goblin monster = new Goblin();
        monster.setHp(1);
        monster.setDefense(50);
        fight.setMonster(monster);

        fight.attack(champion,30);

        softly.assertThat(fight.getMonster().getHp()).isEqualTo(1);
        ifAssertThatIsMonsterStillAlive();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyIfMissHitTest() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        Champion champion = new Champion();
        champion.setHp(1);
        champion.setDefense(50);
        fight.setChampion(champion);

        fight.attack(monster,40);

        softly.assertThat(fight.getChampion().getHp()).isEqualTo(1);
        ifAssertThatIsChampionStillAlive();
        softly.assertAll();
    }

    @Test
    public void championAccuracyIfCriticalHitTest() {
        Goblin monster = new Goblin();
        monster.setHp(2);
        monster.setDefense(50);
        fight.setMonster(monster);
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() - (fight.getChampion().getMaxDamage()) * 2,
                fight.getMonster().getHp() - (fight.getChampion().getNumOfDices()) * 2};

        fight.attack(champion,81);

        softly.assertThat(fight.getMonster().getHp()).isBetween(monsterHpAfterDamage[0], monsterHpAfterDamage[1]);
        ifAssertThatIsMonsterDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterAccuracyIfCriticalHitTest() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        champion.setHp(2);
        champion.setDefense(50);
        fight.setChampion(champion);
        int[] championHpAfterDamage = {fight.getChampion().getHp() - (fight.getMonster().getMaxDamage()) * 2,
                fight.getChampion().getHp() - (fight.getMonster().getNumOfDices()) * 2};

        fight.attack(monster, 91);

        softly.assertThat(fight.getChampion().getHp()).isBetween(championHpAfterDamage[0], championHpAfterDamage[1]);
        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }
//
//    @Test
//    public void championAttackAliveChampionTest() {
//        lowHpChampion();
//        lowHpGoblin();
//
//        fight.dealDamage();
//
//        ifAssertThatIsMonsterDefeated();
//        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
//        softly.assertAll();
//    }
//
//    @Test
//    public void monsterAttackAliveChampionTest() {
//        lowHpGoblin();
//        lowHpChampion();
//
//        fight.monsterAttack();
//
//        ifAssertThatIsChampionDefeated();
//        softly.assertAll();
//    }
//
//    @Test
//    public void championAttackDefeatedChampionTest() {
//        defeatedChampion();
//        lowHpGoblin();
//
//        fight.dealDamage();
//
//        ifAssertThatIsMonsterStillAlive();
//        softly.assertAll();
//    }
//
//
//    @Test
//    public void monsterAttackDefeatedMonsterTest() {
//        defeatedGoblin();
//        lowHpChampion();
//
//        fight.monsterAttack();
//
//        softly.assertThat(fight.getChampion().getHp()).as("Champion still alive").isGreaterThan(0);
//        softly.assertThat(fight.nextTurn()).as("There should be no next turn.").isFalse();
//        softly.assertAll();
//    }
//
//    @Test
//    public void monsterDefeatedWithDefeatedGoblinTest() {
//        defeatedGoblin();
//
//        fight.monsterDefeated();
//
//        softly.assertThat(fight.getCharacterFactory().getKilledMonsterCounter()).isEqualTo(startingKilledMonsterCounter + 1);
//        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter - 1);
//        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(startingHealingPotionCounter + 1);
//        ifAssertThatIsChampionStillAlive(); //this is the new goblin
//        softly.assertThat(fight.nextTurn()).isTrue();
//        softly.assertAll();
//    }
//
//    @Test
//    public void monsterDefeatedWithDefeatedGoblinAndHealingPotionOnMaxTest() {
//        defeatedGoblin();
//        champion.setHealingPotionCounter(5);
//        int toTestHealingPotionCounter = champion.getHealingPotionCounter();
//
//        fight.monsterDefeated();
//
//        softly.assertThat(fight.getCharacterFactory().getKilledMonsterCounter()).isEqualTo(startingKilledMonsterCounter + 1);
//        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter - 1);
//        softly.assertThat(champion.getHealingPotionCounter()).isEqualTo(toTestHealingPotionCounter);
//        ifAssertThatIsChampionStillAlive(); //this is the new goblin
//        softly.assertThat(fight.nextTurn()).isTrue();
//        softly.assertAll();
//    }
//
//    @Test
//    public void monsterDefeatedWithDefeatedGoblinKingTest() {
//        defeatedGoblinKing();
//
//        fight.monsterDefeated();
//
//        softly.assertThat(fight.getCharacterFactory().getKilledMonsterCounter()).isEqualTo(startingKilledMonsterCounter);
//        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter);
//        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(startingHealingPotionCounter);
//        ifAssertThatIsMonsterDefeated(); //dont call new monster
//        softly.assertThat(fight.nextTurn()).isFalse();
//        softly.assertAll();
//    }
}