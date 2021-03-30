package hu.dungeonhunter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.CHARACTER;

import hu.dungeonhunter.characters.monsters.Goblin;
import hu.dungeonhunter.characters.monsters.GoblinKing;
import org.assertj.core.api.Assertions;
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
    int startingHealingPotionCounter = fight.getChampion().getHealingPotionCounter();

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

    @Test
    public void fightInitiationCalculationChampionTest() {
        Goblin monster = new Goblin();
        monster.setHp(1);
        monster.setDefense(1);
        monster.setInitiation(20);
        fight.setMonster(monster);
        champion.setInitiation(100);
        champion.setHp(1);
        champion.setDefense(1);
        fight.setChampion(champion);
        fight.setCharactersInBattle(Arrays.asList(monster, champion)); //az Arrays.asList azt jelenti, hogy létrehoz egy tömb osztályt listaként.

        fight.initiationCalc();

        assertThat(fight.getCharactersInBattle().get(0)).isEqualTo(champion);
    }

    @Test
    public void fightInitiationCalculationMonsterTest() {
        Goblin monster = new Goblin();
        monster.setHp(1);
        monster.setDefense(1);
        monster.setInitiation(100);
        fight.setMonster(monster);
        champion.setHp(1);
        champion.setDefense(1);
        champion.setInitiation(10);
        fight.setChampion(champion);
        fight.setCharactersInBattle(Arrays.asList(monster, champion));

        fight.initiationCalc();

        assertThat(fight.getCharactersInBattle().get(0)).isEqualTo(monster);
    }

    @Test
    public void getCharacterChampionTest() {
        Assertions.assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> monsterFactory.getCharacter(CharacterTypes.CHAMPION));
    }

    @Test
    public void monsterAccuracyIfAccuracyRollIs1Test() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        fight.setRandomCriticalMissEvent(5);

        fight.attack(monster, 1);

        softly.assertThat(fight.getRandomCriticalMissEvent()).isLessThan(5);
    }

    @Test
    public void championAccuracyIfAccuracyRollIs1Test() {
        Goblin monster = new Goblin();
        fight.setMonster(monster);
        fight.setChampion(champion);
        fight.setRandomCriticalMissEvent(5);

        fight.attack(champion, 1);

        softly.assertThat(fight.getRandomCriticalMissEvent()).isLessThan(5);
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
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() -
                fight.getMonster().getMaxDamage() * fight.getMonster().getNumOfDices(),
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
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() - (fight.getChampion().getMaxDamage() * fight.getChampion().getNumOfDices()) * 10,
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
        int[] championHpAfterDamage = {fight.getChampion().getHp() - (fight.getMonster().getMaxDamage() * fight.getMonster().getNumOfDices()) * 10,
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
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() - fight.getChampion().getMaxDamage() * fight.getChampion().getNumOfDices(),
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
        int[] championHpAfterDamage = {fight.getChampion().getHp() -
                fight.getMonster().getMaxDamage() * fight.getMonster().getNumOfDices(),
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
        int[] monsterHpAfterDamage = {fight.getMonster().getHp() -
                (fight.getChampion().getMaxDamage() * fight.getChampion().getNumOfDices()) * 2,
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
        int[] championHpAfterDamage = {fight.getChampion().getHp() -
                (fight.getMonster().getMaxDamage() * fight.getMonster().getNumOfDices()) * 2,
                fight.getChampion().getHp() - (fight.getMonster().getNumOfDices()) * 2};

        fight.attack(monster, 91);

        softly.assertThat(fight.getChampion().getHp()).isBetween(championHpAfterDamage[0], championHpAfterDamage[1]);
        ifAssertThatIsChampionDefeated();
        softly.assertAll();
    }

    @Test
    public void monsterDefeatedWithDefeatedGoblinTest() {
        Goblin monster = new Goblin();
        monster.setHp(0);
        fight.setMonster(monster);

        fight.monsterDefeated();

        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(startingKilledMonsterCounter + 1);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter - 1);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(startingHealingPotionCounter + 1);
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void monsterDefeatedWithDefeatedGoblinAndHealingPotionOnMaxTest() {
        defeatedGoblin();
        champion.setHealingPotionCounter(5);
        fight.setChampion(champion);

        fight.monsterDefeated();

        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(startingKilledMonsterCounter + 1);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter - 1);
        softly.assertThat(champion.getHealingPotionCounter()).isEqualTo(5);
        softly.assertThat(fight.nextTurn()).isTrue();
        softly.assertAll();
    }

    @Test
    public void monsterDefeatedWithDefeatedGoblinKingTest() {
        GoblinKing monster = new GoblinKing();
        monster.setHp(0);
        fight.setMonster(monster);

        fight.monsterDefeated();

        softly.assertThat(fight.getMonsterFactory().getKilledMonsterCounter()).isEqualTo(startingKilledMonsterCounter);
        softly.assertThat(fight.getMonsterCounter()).isEqualTo(startingMonsterCounter);
        softly.assertThat(fight.getChampion().getHealingPotionCounter()).isEqualTo(startingHealingPotionCounter);
        ifAssertThatIsMonsterDefeated();
        softly.assertThat(fight.nextTurn()).isFalse();
        softly.assertAll();
    }
}